package org.opengis.cite.wfs11;

import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.OWS_NAMESPACE;
import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.WFS_NAMESPACE;
import static org.opengis.cite.wfs11.util.NamespaceBindingUtils.XLINK_NAMESPACE;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.opengis.cite.iso19136.util.NamespaceBindings;
import org.opengis.cite.wfs11.util.NamespaceBindingUtils.NamespaceBindingBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * <p>
 * WFSClient class.
 * </p>
 *
 */
public class WFSClient {

	private final Node wfsCapabilities;

	/**
	 * <p>
	 * Constructor for WFSClient.
	 * </p>
	 * @param wfsCapabilities a {@link org.w3c.dom.Node} object
	 */
	public WFSClient(Node wfsCapabilities) {
		this.wfsCapabilities = wfsCapabilities;
	}

	/**
	 * <p>
	 * getFeatureByType.
	 * </p>
	 * @param typeName never <code>null</code>
	 * @return <code>null</code> if no GET or POST DCP URL is found
	 * @throws javax.xml.xpath.XPathExpressionException
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws org.xml.sax.SAXException
	 * @throws java.io.IOException
	 */
	public Document getFeatureByType(QName typeName)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		String getUrl = retrieveUrl("GetFeature", "Get");
		if (getUrl != null && !getUrl.isEmpty()) {
			return handleGetFeatureGet(typeName, getUrl);
		}
		else {
			String postUrl = retrieveUrl("GetFeature", "Post");
			if (postUrl != null && !postUrl.isEmpty()) {
				return handleGetFeaturePost(typeName, postUrl);
			}
			else
				return null;
		}
	}

	/**
	 * <p>
	 * getFeatureType.
	 * </p>
	 * @return <code>null</code> if no GET or POST DCP URL is found
	 * @throws javax.xml.xpath.XPathExpressionException
	 * @throws java.io.IOException
	 */
	public InputStream getFeatureType() throws XPathExpressionException, IOException {
		String getUrl = retrieveUrl("DescribeFeatureType", "Get");
		if (getUrl != null && !getUrl.isEmpty()) {
			return handleGetFeatureTypeGet(getUrl);
		}
		else {
			String postUrl = retrieveUrl("DescribeFeatureType", "Post");
			if (postUrl != null && !postUrl.isEmpty()) {
				return handleGetFeatureTypePost(postUrl);
			}
			else
				return null;
		}
	}

	private String retrieveUrl(String operation, String method) throws XPathExpressionException {
		XPath xpath = createXPath();
		String urlExpression = "//wfs:WFS_Capabilities/ows:OperationsMetadata/ows:Operation[@name='" + operation
				+ "']/ows:DCP/ows:HTTP/ows:" + method + "/@xlink:href";
		return (String) xpath.evaluate(urlExpression, wfsCapabilities, XPathConstants.STRING);
	}

	private XPath createXPath() {
		XPath xpath = XPathFactory.newInstance().newXPath();
		NamespaceBindings nsBindings = new NamespaceBindingBuilder().add("wfs", WFS_NAMESPACE)
			.add("ows", OWS_NAMESPACE)
			.add("xlink", XLINK_NAMESPACE)
			.build();
		xpath.setNamespaceContext(nsBindings);
		return xpath;
	}

	private Document handleGetFeatureGet(QName typeName, String getUrl)
			throws IOException, SAXException, ParserConfigurationException {
		String request = buildGetFeatureGetRequest(typeName, getUrl);
		InputStream stream = new URL(request).openStream();
		return createDocumentBuilder().parse(stream);
	}

	private String buildGetFeatureGetRequest(QName typeName, String getUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append(fixGetUrl(getUrl));
		sb.append("service=WFS&version=1.1.0&request=GetFeature&");
		sb.append("typename=");
		String namespaceURI = typeName.getNamespaceURI();
		if (namespaceURI != null)
			sb.append("app:");
		sb.append(typeName.getLocalPart()).append("&");
		if (namespaceURI != null) {
			sb.append("namespace=xmlns(app=");
			sb.append(namespaceURI).append(")");
		}
		return sb.toString();
	}

	private Document handleGetFeaturePost(QName typeName, String postUrl)
			throws IOException, SAXException, ParserConfigurationException {
		HttpURLConnection connection = (HttpURLConnection) new URL(postUrl).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		String body = buildGetFeaturePostBody(typeName);
		writer.writeBytes(body);
		writer.flush();
		writer.close();
		InputStream stream = connection.getInputStream();
		return createDocumentBuilder().parse(stream);
	}

	private String buildGetFeaturePostBody(QName typeName) {
		StringBuilder sb = new StringBuilder();
		String namespaceURI = typeName.getNamespaceURI();
		sb.append("<wfs:GetFeature xmlns:wfs='http://www.opengis.net/wfs' version='1.1.0' service='WFS'><wfs:Query ");
		if (namespaceURI != null) {
			sb.append("xmlns:app='");
			sb.append(namespaceURI).append("'");
		}
		sb.append(" typeName='");
		if (namespaceURI != null)
			sb.append("app:");
		sb.append(typeName.getLocalPart());
		sb.append("'/></wfs:GetFeature>");
		return sb.toString();
	}

	private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String FEATURE = null;
		// This is the PRIMARY defense. If DTDs (doctypes) are disallowed, almost all XML
		// entity attacks are prevented
		// Xerces 2 only -
		// http://xerces.apache.org/xerces2-j/features.html#disallow-doctype-decl
		FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
		factory.setFeature(FEATURE, true);
		factory.setNamespaceAware(true);
		return factory.newDocumentBuilder();
	}

	private InputStream handleGetFeatureTypeGet(String getUrl) throws IOException {
		String request = buildGetFeatureTypeGetRequest(getUrl);
		return new URL(request).openStream();
	}

	private String buildGetFeatureTypeGetRequest(String getUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append(fixGetUrl(getUrl));
		sb.append("service=WFS&version=1.1.0&request=DescribeFeatureType");
		return sb.toString();
	}

	private InputStream handleGetFeatureTypePost(String postUrl) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(postUrl).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");
		DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
		String body = "<wfs:DescribeFeatureType xmlns:wfs='http://www.opengis.net/wfs' service='WFS' version='1.1.0'/>";
		writer.writeBytes(body);
		writer.flush();
		writer.close();
		return connection.getInputStream();
	}

	private String fixGetUrl(String getUrl) {
		if (getUrl != null) {
			if (getUrl.indexOf("?") == -1)
				return getUrl + "?";
			if (!getUrl.endsWith("?") && !getUrl.endsWith("&"))
				return getUrl + "&";
		}
		return getUrl;
	}

}
