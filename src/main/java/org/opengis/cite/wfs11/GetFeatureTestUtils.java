package org.opengis.cite.wfs11;

import static org.opengis.cite.wfs11.NamespaceBindingUtils.GML_NAMESPACE;
import static org.opengis.cite.wfs11.NamespaceBindingUtils.WFS_NAMESPACE;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSTypeDefinition;
import org.opengis.cite.iso19136.util.NamespaceBindings;
import org.opengis.cite.iso19136.util.XMLSchemaModelUtils;
import org.opengis.cite.wfs11.NamespaceBindingUtils.NamespaceBindingBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.SAXException;

/**
 * 
 */
public class GetFeatureTestUtils {

	/**
	 * Iterates about the feature types specified in the capabilities, for each
	 * feature type the schema document is requested (DescribeFeatureType). The
	 * features are requested and compared with the schema document. If a
	 * feature contains a value for a simple property (string, integer, double),
	 * this information is returned. The returned node looks like (if all
	 * informations could be found):
	 * 
	 * <pre>
	 * 
	 * <FeatureData>
	 *   <FeatureType>
	 *     <localName>AggregateGeoFeature</localName>
	 *     <namespace>http://cite.opengeospatial.org/gmlsf</namespace>
	 *   </FeatureType>
	 *   <Property>
	 *     <localName>doubleProperty</localName>
	 *     <namespace>http://cite.opengeospatial.org/gmlsf</namespace>
	 *   </Property>
	 *   <value>2012.78</value>
	 * </FeatureData>
	 * 
	 * </pre>
	 * 
	 * @param wfsCapabilities
	 *            the capabilities of the wfs, never <code>null</code>
	 * @return a tupel containing the value of a property specified by the
	 *         feature type, the property and feature type in xml format
	 *         (described above), may be <code>null</code> if no such tupel
	 *         could be found
	 * @throws Exception
	 *             if an error occured
	 */
	public static Node findFeatureTypeAndPropertyName(Node wfsCapabilities)
			throws Exception {
		try {
			Node wfsCapabilitiesReloaded = reloadNode(wfsCapabilities);

			WFSClient wfsClient = new WFSClient(wfsCapabilitiesReloaded);
			List<QName> featureTypeNames = parseFeatureTypeNames(wfsCapabilitiesReloaded);
			XSModel model = loadFeatureTypeModel(wfsClient);
			FeatureData featureData = acquireFeatureData(wfsClient,
					featureTypeNames, model);
			if (featureData != null)
				return asXml(featureData).getDocumentElement();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static XSModel loadFeatureTypeModel(WFSClient wfsClient)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IOException, XPathExpressionException {
		InputStream featureType = wfsClient.getFeatureType();

		DOMImplementationRegistry registry = DOMImplementationRegistry
				.newInstance();

		XSImplementation impl = (XSImplementation) registry
				.getDOMImplementation("XS-Loader");

		XSLoader schemaLoader = impl.createXSLoader(null);

		DOMInputImpl lsInput = new DOMInputImpl();
		lsInput.setByteStream(featureType);

		return schemaLoader.load(lsInput);
	}

	private static List<QName> parseFeatureTypeNames(Node wfsCapabilities)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException {

		System.out.println("LN: " + wfsCapabilities.getLocalName());

		List<QName> featureInfo = new ArrayList<QName>();
		XPath xpath = XPathFactory.newInstance().newXPath();
		NamespaceBindings nsBindings = new NamespaceBindings();
		nsBindings.addNamespaceBinding(WFS_NAMESPACE, "wfs");
		xpath.setNamespaceContext(nsBindings);
		NodeList nodes = (NodeList) xpath.evaluate(
				"//wfs:FeatureTypeList/wfs:FeatureType/wfs:Name",
				wfsCapabilities, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			featureInfo.add(buildQName(node));
		}
		return featureInfo;

	}

	private static FeatureData acquireFeatureData(WFSClient wfsClient,
			List<QName> featureTypes, XSModel model) throws Exception {
		for (QName featureType : featureTypes) {
			Document rspEntity = wfsClient.getFeatureByType(featureType);
			String typeName = featureType.getLocalPart();
			String typeNamespace = featureType.getNamespaceURI();
			NodeList features = rspEntity.getElementsByTagNameNS(typeNamespace,
					typeName);
			boolean hasFeatures = features.getLength() > 0;
			if (hasFeatures) {
				XSTypeDefinition xsdDoubleType = model.getTypeDefinition(
						"double", XMLConstants.W3C_XML_SCHEMA_NS_URI);
				XSTypeDefinition xsdDecimalType = model.getTypeDefinition(
						"decimal", XMLConstants.W3C_XML_SCHEMA_NS_URI);
				XSTypeDefinition xsdIntegerType = model.getTypeDefinition(
						"integer", XMLConstants.W3C_XML_SCHEMA_NS_URI);
				XSTypeDefinition xsdStringType = model.getTypeDefinition(
						"string", XMLConstants.W3C_XML_SCHEMA_NS_URI);

				List<XSElementDeclaration> featureProperties = getFeaturePropertiesByType(
						model, featureType, xsdDecimalType, xsdDoubleType,
						xsdIntegerType, xsdStringType);
				for (XSElementDeclaration featureProperty : featureProperties) {
					String propName = featureProperty.getName();
					String propNamespace = featureProperty.getNamespace();
					if (!GML_NAMESPACE.equals(propNamespace)) {
						Object property = extractProperty(rspEntity, typeName,
								typeNamespace, propName, propNamespace);
						if (property instanceof NodeList) {
							NodeList nodes = (NodeList) property;
							for (int i = 0; i < nodes.getLength(); i++) {
								Node node = nodes.item(i);
								String textContent = node.getTextContent();
								if (textContent != null
										&& !textContent.isEmpty()) {
									QName propertyQName = new QName(
											propNamespace, propName);
									return new FeatureData(featureType,
											propertyQName, textContent);
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	private static Object extractProperty(Document rspEntity, String typeName,
			String typeNamespace, String propName, String propNamespace)
			throws XPathExpressionException {
		NamespaceBindings nsBindings = new NamespaceBindingBuilder()
				.add("n1", typeNamespace).add("n2", propNamespace)
				.add("wfs", WFS_NAMESPACE).add("gml", GML_NAMESPACE).build();

		StringBuilder xPathBuilder = new StringBuilder();
		xPathBuilder.append("//wfs:FeatureCollection/gml:featureMember/");
		xPathBuilder.append(nsBindings.getPrefix(typeNamespace)).append(":")
				.append(typeName).append("/");
		xPathBuilder.append(nsBindings.getPrefix(propNamespace)).append(":")
				.append(propName);
		xPathBuilder.append(" | ");
		xPathBuilder.append("//wfs:FeatureCollection/gml:featureMembers/");
		xPathBuilder.append(nsBindings.getPrefix(typeNamespace)).append(":")
				.append(typeName).append("/");
		xPathBuilder.append(nsBindings.getPrefix(propNamespace)).append(":")
				.append(propName);

		String xPath = xPathBuilder.toString();

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(nsBindings);
		return xpath.evaluate(xPath, rspEntity, XPathConstants.NODESET);
	}

	private static List<XSElementDeclaration> getFeaturePropertiesByType(
			XSModel model, QName featureTypeName, XSTypeDefinition... typeDefs) {
		XSElementDeclaration elemDecl = model.getElementDeclaration(
				featureTypeName.getLocalPart(),
				featureTypeName.getNamespaceURI());
		XSComplexTypeDefinition featureTypeDef = (XSComplexTypeDefinition) elemDecl
				.getTypeDefinition();
		List<XSElementDeclaration> featureProps = XMLSchemaModelUtils
				.getAllElementsInParticle(featureTypeDef.getParticle());
		List<XSElementDeclaration> props = new ArrayList<XSElementDeclaration>();
		// set bit mask to indicate acceptable derivation mechanisms
		short extendOrRestrict = XSConstants.DERIVATION_EXTENSION
				| XSConstants.DERIVATION_RESTRICTION;
		for (XSElementDeclaration featureProp : featureProps) {
			XSTypeDefinition propType = featureProp.getTypeDefinition();
			for (XSTypeDefinition typeDef : typeDefs) {
				switch (propType.getTypeCategory()) {
				case XSTypeDefinition.SIMPLE_TYPE:
					if ((typeDef.getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE)
							&& propType.derivedFromType(typeDef,
									extendOrRestrict)) {
						props.add(featureProp);
					}
					break;
				case XSTypeDefinition.COMPLEX_TYPE:
					if (typeDef.getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE) {
						// check type of child element(s)
						XSComplexTypeDefinition complexPropType = (XSComplexTypeDefinition) propType;
						List<XSElementDeclaration> propValues = XMLSchemaModelUtils
								.getAllElementsInParticle(complexPropType
										.getParticle());
						for (XSElementDeclaration propValue : propValues) {
							if (propValue.getTypeDefinition().derivedFromType(
									typeDef, extendOrRestrict)) {
								props.add(featureProp);
							}
						}
					} else {
						// complex type may derive from simple type
						if (propType.derivedFromType(typeDef, extendOrRestrict)) {
							props.add(featureProp);
						}
					}
					break;
				}
			}
		}
		return props;
	}

	private static Node reloadNode(Node wfsCapabilities) throws Exception,
			SAXException, IOException, ParserConfigurationException {
		// this is required cause of java.lang.RuntimeException: Knoten konnte
		// nicht in Handle aufgelöst werden
		// at
		// com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault.getDTMHandleFromNode(DTMManagerDefault.java:579)
		// at
		// com.sun.org.apache.xpath.internal.XPathContext.getDTMHandleFromNode(XPathContext.java:188)
		// at com.sun.org.apache.xpath.internal.XPath.execute(XPath.java:305)
		// at
		// com.sun.org.apache.xpath.internal.jaxp.XPathImpl.eval(XPathImpl.java:205)
		// at
		// com.sun.org.apache.xpath.internal.jaxp.XPathImpl.evaluate(XPathImpl.java:270)
		// at
		// org.opengis.cite.wfs11.GetFeatureTestUtils.parseFeatureTypeNames(GetFeatureTestUtils.java:133)
		// at
		// org.opengis.cite.wfs11.GetFeatureTestUtils.findFeatureTypeAndPropertyName(GetFeatureTestUtils.java:48)
		String capabilitiesAsString = asString(wfsCapabilities);
		return asNode(capabilitiesAsString);
	}

	private static Document asXml(FeatureData featureData)
			throws ParserConfigurationException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("FeatureData");
		doc.appendChild(rootElement);

		Element featureTypeElement = doc.createElement("FeatureType");
		rootElement.appendChild(featureTypeElement);

		Element featureTypeLocalNameElement = doc.createElement("localName");
		featureTypeLocalNameElement.setTextContent(featureData.getFeatureType()
				.getLocalPart());
		featureTypeElement.appendChild(featureTypeLocalNameElement);

		Element featureTypeNamespaceElement = doc.createElement("namespace");
		featureTypeNamespaceElement.setTextContent(featureData.getFeatureType()
				.getNamespaceURI());
		featureTypeElement.appendChild(featureTypeNamespaceElement);

		Element propertyElement = doc.createElement("Property");
		rootElement.appendChild(propertyElement);

		Element propertyLocalNameElement = doc.createElement("localName");
		propertyLocalNameElement.setTextContent(featureData.getPropName()
				.getLocalPart());
		propertyElement.appendChild(propertyLocalNameElement);

		Element propertyNamespaceElement = doc.createElement("namespace");
		propertyNamespaceElement.setTextContent(featureData.getPropName()
				.getNamespaceURI());
		propertyElement.appendChild(propertyNamespaceElement);

		Element valueElement = doc.createElement("value");
		valueElement.setTextContent(featureData.getData());
		rootElement.appendChild(valueElement);

		return doc;
	}

	private static Node asNode(String asString) throws SAXException,
			IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(asString.getBytes());
		try {
			Document wfsCapabilities = builder.parse(is);
			return wfsCapabilities.getDocumentElement();
		} finally {
			is.close();
		}
	}

	private static String asString(Node node) throws Exception {
		StringWriter writer = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.transform(new DOMSource(node), new StreamResult(writer));
		return writer.toString();
	}

	private static QName buildQName(Node node) {
		String localPart;
		String nsName;
		String name = node.getTextContent();
		int indexOfColon = name.indexOf(':');
		if (indexOfColon > 0) {
			localPart = name.substring(indexOfColon + 1);
			nsName = node.lookupNamespaceURI(name.substring(0, indexOfColon));
		} else {
			localPart = name;
			// return default namespace URI if any
			nsName = node.lookupNamespaceURI(null);
		}
		return new QName(nsName, localPart);
	}

}