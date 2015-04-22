package org.opengis.cite.wfs11;

import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.GML_NAMESPACE;
import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.WFS_NAMESPACE;
import static org.opengis.cite.wfs11.util.XmlUtils.reloadNode;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.opengis.cite.iso19136.util.NamespaceBindings;
import org.opengis.cite.wfs11.domain.FeatureData;
import org.opengis.cite.wfs11.util.NamespaceBindingUtils.NamespaceBindingBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	 *         the filter condition, <code>false</code> otherwise or if the
	 *         feature collection does not contain a feature
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