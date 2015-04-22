package org.opengis.cite.wfs11;

import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class GetFeatureTestExtensionIT {

	@Ignore
	@Test
	public void testTest() throws Exception {
		Node wfsCapabilities = parseWfsCapabilities();

		Node result = GetFeatureTestExtension
				.findFeatureTypeAndPropertyName(wfsCapabilities);

		assertThat(
				result,
				hasValue("//FeatureData/FeatureType/localName",
						"AggregateGeoFeature"));
		assertThat(
				result,
				hasValue("//FeatureData/FeatureType/namespace",
						"http://cite.opengeospatial.org/gmlsf"));
		assertThat(result,
				hasValue("//FeatureData/Property/localName", "doubleProperty"));
		assertThat(
				result,
				hasValue("//FeatureData/Property/namespace",
						"http://cite.opengeospatial.org/gmlsf"));
		assertThat(result, hasValue("//FeatureData/value", "2012.78"));
	}

	private BaseMatcher<Node> hasValue(final String xPath, final String value) {
		return new BaseMatcher<Node>() {

			public boolean matches(Object object) {
				Node node = (Node) object;
				String valueFromResult = extractValue(node, xPath);
				return value.equals(valueFromResult);
			}

			public void describeTo(Description description) {
				description.appendText("XML must contain the value '" + value
						+ " at " + xPath);
			}
		};
	}

	private static String extractValue(Node result, String xPath) {
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			return (String) xpath
					.evaluate(xPath, result, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}

	private Node parseWfsCapabilities() throws ParserConfigurationException,
			SAXException, IOException {
		String wfsCapabilitiesUrl = "http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?request=GetCapabilities&service=WFS";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document wfsCapabilities = builder.parse(wfsCapabilitiesUrl);
		return wfsCapabilities.getDocumentElement();
	}

}