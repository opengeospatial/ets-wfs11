package org.opengis.cite.wfs11;

import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.GML_NAMESPACE;
import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.WFS_NAMESPACE;
import static org.opengis.cite.wfs11.util.XmlUtils.buildQName;
import static org.opengis.cite.wfs11.util.XmlUtils.reloadNode;

import java.io.ByteArrayInputStream;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.opengis.cite.iso19136.util.NamespaceBindings;
import org.opengis.cite.wfs11.domain.FeatureData;
import org.opengis.cite.wfs11.util.NamespaceBindingUtils.NamespaceBindingBuilder;
import org.opengis.cite.wfs11.util.XmlUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

/**
 * Contains some test conditions to extend ctl scripts.
 */
public class GetFeatureTestConditions {

	/**
	 * Checks if all of the feature members matches the filter condition in the
	 * feature data (PropertyIsEqualTo)
	 * 
	 * @param featureCollection
	 *            the GetFeature response, never <code>null</code>
	 * @param featureData
	 *            the featureData used to request the features, never
	 *            <code>null</code>
	 * @return <code>true</code> if at least one feature member does not match
	 *         the filter conditionor if the feature collection does not contain
	 *         a feature, <code>false</code> otherwise
	 * @throws Exception
	 *             if an error occurred
	 */
	public static boolean checkFeaturesMatchingFilter(Node featureCollection,
			Node featureData) throws Exception {
		try {
			FeatureData parsedFeatureData = parseFeatureData(reloadNode(featureData));

			NodeList featureMembers = findFeatureMembers(
					reloadNode(featureCollection), parsedFeatureData);
			for (int i = 0; i < featureMembers.getLength(); i++) {
				Node featureMember = featureMembers.item(i);
				if (!hasProperty(featureMember, parsedFeatureData))
					return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Checks if all featureTypes in the capabilities document are available in
	 * the DescribeFeatureType response
	 * 
	 * @param capabilitiesResponse
	 *            never <code>null</code>
	 * @param describeFeatureTypeResponse
	 *            never <code>null</code>
	 * @return <code>true</code> if all feature types in the capabilities
	 *         document are available in the DescribeFeatureType response or the
	 *         capabilities document does not contain a feature type,
	 *         <code>false</code> otherwise
	 * @throws Exception
	 */
	public static boolean checkFeatureTypesDescribedInSchema(
			Node capabilitiesResponse, Node describeFeatureTypeResponse)
			throws Exception {
		try {
			XSModel model = parseSchema(reloadNode(describeFeatureTypeResponse));

			NodeList featureTypeNames = parseFeatureTypeNames(reloadNode(capabilitiesResponse));
			for (int i = 0; i < featureTypeNames.getLength(); i++) {
				Node featureTypeNameNode = featureTypeNames.item(i);
				QName featureTypeName = buildQName(featureTypeNameNode);

				XSElementDeclaration elementDeclaration = model
						.getElementDeclaration(featureTypeName.getLocalPart(),
								featureTypeName.getNamespaceURI());
				if (elementDeclaration == null)
					return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static XSModel parseSchema(Node node) throws Exception {
		DOMImplementationRegistry registry = DOMImplementationRegistry
				.newInstance();
		XSImplementation impl = (XSImplementation) registry
				.getDOMImplementation("XS-Loader");
		XSLoader schemaLoader = impl.createXSLoader(null);
		DOMInputImpl lsInput = new DOMInputImpl();
		ByteArrayInputStream bis = new ByteArrayInputStream(XmlUtils.asString(
				node).getBytes());
		try {
			lsInput.setByteStream(bis);
			return schemaLoader.load(lsInput);
		} finally {
			bis.close();
		}
	}

	private static NodeList parseFeatureTypeNames(Node capabilities)
			throws XPathExpressionException {
		NamespaceBindings nsBindings = new NamespaceBindingBuilder().add("wfs",
				WFS_NAMESPACE).build();
		String xPath = "//wfs:FeatureTypeList/wfs:FeatureType/wfs:Name";

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(nsBindings);
		return (NodeList) xpath.evaluate(xPath, capabilities,
				XPathConstants.NODESET);
	}

	private static FeatureData parseFeatureData(Node featureData)
			throws XPathExpressionException {
		String featureTypeName = evaluate(
				"//FeatureData/FeatureType/localName", featureData);
		String featureTypeNamespace = evaluate(
				"//FeatureData/FeatureType/namespace", featureData);
		String propertyName = evaluate("//FeatureData/Property/localName",
				featureData);
		String propertyNamespace = evaluate("//FeatureData/Property/namespace",
				featureData);
		String value = evaluate("//FeatureData/value", featureData);
		return new FeatureData(
				new QName(featureTypeNamespace, featureTypeName), new QName(
						propertyNamespace, propertyName), value);
	}

	private static NodeList findFeatureMembers(Node featureCollection,
			FeatureData featureData) throws XPathExpressionException {
		String typeNamespace = featureData.getFeatureType().getNamespaceURI();
		String typeName = featureData.getFeatureType().getLocalPart();

		NamespaceBindings nsBindings = new NamespaceBindingBuilder()
				.add("n1", typeNamespace).add("wfs", WFS_NAMESPACE)
				.add("gml", GML_NAMESPACE).build();

		StringBuilder xPathBuilder = new StringBuilder();
		xPathBuilder.append("//wfs:FeatureCollection/gml:featureMember/");
		xPathBuilder.append(nsBindings.getPrefix(typeNamespace)).append(":")
				.append(typeName);
		xPathBuilder.append(" | ");
		xPathBuilder.append("//wfs:FeatureCollection/gml:featureMembers/");
		xPathBuilder.append(nsBindings.getPrefix(typeNamespace)).append(":")
				.append(typeName);

		String xPath = xPathBuilder.toString();

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(nsBindings);
		return (NodeList) xpath.evaluate(xPath, featureCollection,
				XPathConstants.NODESET);
	}

	private static boolean hasProperty(Node featureMember,
			FeatureData featureData) throws XPathExpressionException {
		String propertyNamespace = featureData.getPropName().getNamespaceURI();
		String propertyName = featureData.getPropName().getLocalPart();
		String value = featureData.getData();

		NodeList featureProperties = parseProperties(featureMember,
				propertyNamespace, propertyName);

		for (int i = 0; i < featureProperties.getLength(); i++) {
			Node property = featureProperties.item(i);
			String textContent = property.getTextContent();
			if (value.equals(textContent))
				return true;
		}
		return false;
	}

	private static NodeList parseProperties(Node featureMember,
			String propertyNamespace, String propertyName)
			throws XPathExpressionException {
		NamespaceBindings nsBindings = new NamespaceBindingBuilder()
				.add("n1", propertyNamespace).add("wfs", WFS_NAMESPACE)
				.add("gml", GML_NAMESPACE).build();

		StringBuilder xPathBuilder = new StringBuilder();
		xPathBuilder.append("//");
		xPathBuilder.append(nsBindings.getPrefix(propertyNamespace));
		xPathBuilder.append(":");
		xPathBuilder.append(propertyName);

		String xPath = xPathBuilder.toString();

		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(nsBindings);

		return (NodeList) xpath.evaluate(xPath, featureMember,
				XPathConstants.NODESET);
	}

	private static String evaluate(String xPath, Node featureData)
			throws XPathExpressionException {
		return (String) XPathFactory.newInstance().newXPath()
				.evaluate(xPath, featureData, XPathConstants.STRING);
	}

}