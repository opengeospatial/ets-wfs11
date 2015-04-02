package org.opengis.cite.wfs11;

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
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSTypeDefinition;
import org.opengis.cite.iso19136.util.NamespaceBindings;
import org.opengis.cite.iso19136.util.XMLSchemaModelUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.xml.sax.SAXException;

/**
 *
 */
public class GetFeatureTestUtils {

	private static final String WFS_NAMESPACE = "http://www.opengis.net/wfs";

	public static final String GML = "http://www.opengis.net/gml/3.2";

	public static String findFeatureTypeAndPropertyName(
			String wfsCapabilitiesUrl) throws Exception {
		try {
			Document wfsCapabilities = asDocument(wfsCapabilitiesUrl);
			WFSClient wfsClient = new WFSClient(wfsCapabilities);
			List<QName> featureTypeNames = parseFeatureTypeNames(wfsCapabilities);
			XSModel model = loadFeatureTypeModel(wfsClient);
			FeatureData featureData = acquireFeatureData(wfsClient,
					featureTypeNames, model);
			return asString(featureData);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static String asString(FeatureData featureData) {
		StringBuilder sb = new StringBuilder();
		QName featureType = featureData.getFeatureType();
		sb.append(
				featureType.getNamespaceURI() != null ? featureType
						.getNamespaceURI() : "").append('|');
		sb.append(featureType.getLocalPart()).append('|');
		QName propertyName = featureData.getPropName();
		sb.append(
				propertyName.getNamespaceURI() != null ? propertyName
						.getNamespaceURI() : "").append('|');
		sb.append(propertyName.getLocalPart()).append('|');
		sb.append(featureData.getData());
		return sb.toString();
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

	private static List<QName> parseFeatureTypeNames(Document wfsCapabilities)
			throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException {

		List<QName> featureInfo = new ArrayList<QName>();
		XPath xpath = XPathFactory.newInstance().newXPath();
		NamespaceBindings nsBindings = new NamespaceBindings();
		nsBindings.addNamespaceBinding(WFS_NAMESPACE, "wfs");
		xpath.setNamespaceContext(nsBindings);
		Object result = xpath.evaluate(
				"//wfs:FeatureTypeList/wfs:FeatureType/wfs:Name",
				wfsCapabilities, XPathConstants.NODESET);
		if (result instanceof NodeList) {
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				featureInfo.add(buildQName(node));
			}
		}
		return featureInfo;

	}

	private static Document asDocument(String wfsCapabilitiesUrl)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(wfsCapabilitiesUrl);
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

					if (!"http://www.opengis.net/gml".equals(propNamespace)) {

						NamespaceBindings nsBindings = new NamespaceBindings();
						nsBindings.addNamespaceBinding(typeNamespace, "n1");
						nsBindings.addNamespaceBinding(propNamespace, "n2");
						nsBindings.addNamespaceBinding(WFS_NAMESPACE, "wfs");
						nsBindings.addNamespaceBinding(
								"http://www.opengis.net/gml", "gml");

						StringBuilder xPathBuilder = new StringBuilder();
						xPathBuilder
								.append("//wfs:FeatureCollection/gml:featureMember/");
						xPathBuilder
								.append(nsBindings.getPrefix(typeNamespace))
								.append(":").append(typeName).append("/");
						xPathBuilder
								.append(nsBindings.getPrefix(propNamespace))
								.append(":").append(propName);
						xPathBuilder.append(" | ");
						xPathBuilder
								.append("//wfs:FeatureCollection/gml:featureMembers/");
						xPathBuilder
								.append(nsBindings.getPrefix(typeNamespace))
								.append(":").append(typeName).append("/");
						xPathBuilder
								.append(nsBindings.getPrefix(propNamespace))
								.append(":").append(propName);

						String xPath = xPathBuilder.toString();

						XPath xpath = XPathFactory.newInstance().newXPath();
						xpath.setNamespaceContext(nsBindings);
						Object result = xpath.evaluate(xPath, rspEntity,
								XPathConstants.NODESET);
						if (result instanceof NodeList) {
							NodeList nodes = (NodeList) result;
							for (int i = 0; i < nodes.getLength(); i++) {
								Node node = nodes.item(i);
								String textContent = node.getTextContent();
								if (textContent != null
										&& !textContent.isEmpty()) {
									QName property = new QName(propNamespace,
											propName);
									return new FeatureData(featureType,
											property, textContent);
								}
							}
						}
					}
				}
			}
		}
		return null;
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

	private static List<XSElementDeclaration> getFeaturePropertiesByType(
			XSModel model, QName featureTypeName, XSTypeDefinition... typeDefs) {
		XSElementDeclaration elemDecl = model.getElementDeclaration(
				featureTypeName.getLocalPart(),
				featureTypeName.getNamespaceURI());
		XSComplexTypeDefinition featureTypeDef = (XSComplexTypeDefinition) elemDecl
				.getTypeDefinition();
		List<XSElementDeclaration> featureProps = XMLSchemaModelUtils
				.getAllElementsInParticle(featureTypeDef.getParticle());
		removeDeprecatedGMLElements(featureProps, model);
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

	private static void removeDeprecatedGMLElements(
			List<XSElementDeclaration> elemDecls, XSModel model) {
		XSElementDeclaration elemDecl = model.getElementDeclaration("location",
				GML);
		elemDecls.remove(elemDecl);
		elemDecl = model.getElementDeclaration("metaDataProperty", GML);
		elemDecls.remove(elemDecl);
	}

}