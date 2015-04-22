package org.opengis.cite.wfs11.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSTypeDefinition;
import org.opengis.cite.iso19136.util.XMLSchemaModelUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlUtils {

	/**
	 * Creates a {@link Node} out of a {@link String}
	 * 
	 * @param xmlString
	 *            never <code>null</code>
	 * @return the string as node, never <code>null</code>
	 */
	public static Node asNode(String xmlString) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		ByteArrayInputStream is = new ByteArrayInputStream(xmlString.getBytes());
		try {
			Document wfsCapabilities = builder.parse(is);
			return wfsCapabilities.getDocumentElement();
		} finally {
			is.close();
		}
	}

	/**
	 * Creates a {@link String} out of a {@link Node}
	 * 
	 * @param node
	 *            never <code>null</code>
	 * @return the node as string, never <code>null</code>
	 * @throws Exception
	 */
	public static String asString(Node node) throws Exception {
		StringWriter writer = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.transform(new DOMSource(node), new StreamResult(writer));
		return writer.toString();
	}

	/**
	 * @param the
	 *            node to create the name from, never <code>null</code>
	 * @return the node of the name as qualified name, never <code>null</code>
	 */
	public static QName buildQName(Node node) {
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

	/**
	 * Parses all properties of a feature type matching the given type
	 * 
	 * @param model
	 *            the parsed schema definition never <code>null</code>
	 * @param featureTypeName
	 *            the name of the feature type the properties should be parsed
	 *            from, never <code>null</code>
	 * @param typeDefs
	 *            a list of types the properties should match
	 * @return a list of {@link XSElementDeclaration}s part of the feature type
	 *         matching to one of the passed types, may be empty but never
	 *         <code>null</code>
	 */
	public static List<XSElementDeclaration> getFeaturePropertiesByType(
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
}