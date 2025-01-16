package org.opengis.cite.wfs11;

import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.GML_NAMESPACE;
import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.WFS_NAMESPACE;
import static org.opengis.cite.wfs11.util.XmlUtils.buildQName;
import static org.opengis.cite.wfs11.util.XmlUtils.reloadNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSTypeDefinition;
import org.opengis.cite.iso19136.util.NamespaceBindings;
import org.opengis.cite.wfs11.domain.FeatureData;
import org.opengis.cite.wfs11.util.NamespaceBindingUtils.NamespaceBindingBuilder;
import org.opengis.cite.wfs11.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.SAXException;

/**
 * Extension functions for ctl scripts to retrieve informations which can not be retrieved
 * by xslt/ctl.
 */
public class GetFeatureTestExtension {

	/**
	 * Iterates about the feature types specified in the capabilities, for each feature
	 * type the schema document is requested (DescribeFeatureType). The features are
	 * requested and compared with the schema document. If a feature contains a value for
	 * a simple property (string, integer, double), this information is returned. The
	 * returned node looks like (if all informations could be found):
	 *
	 * <pre>
	 *
	 * &lt;FeatureData&gt;
	 *   &lt;FeatureType&gt;
	 *     &lt;localName&gt;AggregateGeoFeature&lt;/localName&gt;
	 *     &lt;namespace&gt;http://cite.opengeospatial.org/gmlsf&lt;/namespace&gt;
	 *   &lt;/FeatureType&gt;
	 *   &lt;Property&gt;
	 *     &lt;localName&gt;doubleProperty&lt;/localName&gt;
	 *     &lt;namespace&gt;http://cite.opengeospatial.org/gmlsf&lt;/namespace&gt;
	 *   &lt;/Property&gt;
	 *   &lt;value&gt;2012.78&lt;/value&gt;
	 * &lt;/FeatureData&gt;
	 *
	 * </pre>
	 * @param wfsCapabilities the capabilities of the wfs, never <code>null</code>
	 * @return a tupel containing the value of a property specified by the feature type,
	 * the property and feature type in xml format (described above), may be
	 * <code>null</code> if no such tupel could be found
	 * @throws java.lang.Exception if an error occurred
	 */
	public static Node findFeatureTypeAndPropertyName(Node wfsCapabilities) throws Exception {
		try {
			Node wfsCapabilitiesReloaded = reloadNode(wfsCapabilities);

			WFSClient wfsClient = new WFSClient(wfsCapabilitiesReloaded);
			List<QName> featureTypeNames = parseFeatureTypeNames(wfsCapabilitiesReloaded);
			XSModel model = loadFeatureTypeModel(wfsClient);
			FeatureData featureData = acquireFeatureData(wfsClient, featureTypeNames, model);
			if (featureData != null)
				return asXml(featureData).getDocumentElement();
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static XSModel loadFeatureTypeModel(WFSClient wfsClient) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, XPathExpressionException {
		InputStream featureType = wfsClient.getFeatureType();

		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

		XSImplementation impl = (XSImplementation) registry.getDOMImplementation("XS-Loader");

		XSLoader schemaLoader = impl.createXSLoader(null);

		DOMInputImpl lsInput = new DOMInputImpl();
		lsInput.setByteStream(featureType);

		return schemaLoader.load(lsInput);
	}

	private static List<QName> parseFeatureTypeNames(Node wfsCapabilities)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		List<QName> featureInfo = new ArrayList<QName>();
		XPath xpath = XPathFactory.newInstance().newXPath();
		NamespaceBindings nsBindings = new NamespaceBindings();
		nsBindings.addNamespaceBinding(WFS_NAMESPACE, "wfs");
		xpath.setNamespaceContext(nsBindings);
		NodeList nodes = (NodeList) xpath.evaluate("//wfs:FeatureTypeList/wfs:FeatureType/wfs:Name", wfsCapabilities,
				XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			featureInfo.add(buildQName(node));
		}
		return featureInfo;

	}

	private static FeatureData acquireFeatureData(WFSClient wfsClient, List<QName> featureTypes, XSModel model)
			throws Exception {
		for (QName featureType : featureTypes) {
			Document rspEntity = wfsClient.getFeatureByType(featureType);
			String typeName = featureType.getLocalPart();
			String typeNamespace = featureType.getNamespaceURI();
			NodeList features = rspEntity.getElementsByTagNameNS(typeNamespace, typeName);
			boolean hasFeatures = features.getLength() > 0;
			if (hasFeatures) {
				List<XSElementDeclaration> featureProperties = findSimpleProperties(model, featureType);
				for (XSElementDeclaration featureProperty : featureProperties) {
					FeatureData featureDataForProperty = aquireFeatureData(featureType, rspEntity, typeName,
							typeNamespace, featureProperty);
					if (featureDataForProperty != null)
						return featureDataForProperty;
				}
			}
		}
		return null;
	}

	private static FeatureData aquireFeatureData(QName featureType, Document rspEntity, String typeName,
			String typeNamespace, XSElementDeclaration featureProperty) throws XPathExpressionException {
		String propName = featureProperty.getName();
		String propNamespace = featureProperty.getNamespace();
		if (!GML_NAMESPACE.equals(propNamespace)) {
			Object property = extractProperty(rspEntity, typeName, typeNamespace, propName, propNamespace);
			if (property instanceof NodeList) {
				NodeList nodes = (NodeList) property;
				for (int i = 0; i < nodes.getLength(); i++) {
					Node node = nodes.item(i);
					String textContent = node.getTextContent();
					if (textContent != null && !textContent.isEmpty()) {
						QName propertyQName = new QName(propNamespace, propName);
						return new FeatureData(featureType, propertyQName, textContent);
					}
				}
			}
		}
		return null;
	}

	private static List<XSElementDeclaration> findSimpleProperties(XSModel model, QName featureType) {
		XSTypeDefinition xsdDoubleType = model.getTypeDefinition("double", XMLConstants.W3C_XML_SCHEMA_NS_URI);
		XSTypeDefinition xsdDecimalType = model.getTypeDefinition("decimal", XMLConstants.W3C_XML_SCHEMA_NS_URI);
		XSTypeDefinition xsdIntegerType = model.getTypeDefinition("integer", XMLConstants.W3C_XML_SCHEMA_NS_URI);
		XSTypeDefinition xsdStringType = model.getTypeDefinition("string", XMLConstants.W3C_XML_SCHEMA_NS_URI);

		return XmlUtils.getFeaturePropertiesByType(model, featureType, xsdDecimalType, xsdDoubleType, xsdIntegerType,
				xsdStringType);
	}

	private static Object extractProperty(Document rspEntity, String typeName, String typeNamespace, String propName,
			String propNamespace) throws XPathExpressionException {
		NamespaceBindings nsBindings = new NamespaceBindingBuilder().add("n1", typeNamespace)
			.add("n2", propNamespace)
			.add("wfs", WFS_NAMESPACE)
			.add("gml", GML_NAMESPACE)
			.build();

		StringBuilder xPathBuilder = new StringBuilder();
		xPathBuilder.append("//wfs:FeatureCollection/gml:featureMember/");
		xPathBuilder.append(nsBindings.getPrefix(typeNamespace)).append(":").append(typeName).append("/");
		xPathBuilder.append(nsBindings.getPrefix(propNamespace)).append(":").append(propName);
		xPathBuilder.append(" | ");
		xPathBuilder.append("//wfs:FeatureCollection/gml:featureMembers/");
		xPathBuilder.append(nsBindings.getPrefix(typeNamespace)).append(":").append(typeName).append("/");
		xPathBuilder.append(nsBindings.getPrefix(propNamespace)).append(":").append(propName);

		String xPath = xPathBuilder.toString();

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(nsBindings);
		return xpath.evaluate(xPath, rspEntity, XPathConstants.NODESET);
	}

	private static Document asXml(FeatureData featureData) throws ParserConfigurationException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("FeatureData");
		doc.appendChild(rootElement);

		Element featureTypeElement = doc.createElement("FeatureType");
		rootElement.appendChild(featureTypeElement);

		Element featureTypeLocalNameElement = doc.createElement("localName");
		featureTypeLocalNameElement.setTextContent(featureData.getFeatureType().getLocalPart());
		featureTypeElement.appendChild(featureTypeLocalNameElement);

		Element featureTypeNamespaceElement = doc.createElement("namespace");
		featureTypeNamespaceElement.setTextContent(featureData.getFeatureType().getNamespaceURI());
		featureTypeElement.appendChild(featureTypeNamespaceElement);

		Element propertyElement = doc.createElement("Property");
		rootElement.appendChild(propertyElement);

		Element propertyLocalNameElement = doc.createElement("localName");
		propertyLocalNameElement.setTextContent(featureData.getPropName().getLocalPart());
		propertyElement.appendChild(propertyLocalNameElement);

		Element propertyNamespaceElement = doc.createElement("namespace");
		propertyNamespaceElement.setTextContent(featureData.getPropName().getNamespaceURI());
		propertyElement.appendChild(propertyNamespaceElement);

		Element valueElement = doc.createElement("value");
		valueElement.setTextContent(featureData.getData());
		rootElement.appendChild(valueElement);

		return doc;
	}

}
