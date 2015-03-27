package org.opengis.cite.wfs11;

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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WFSClient {

    private static final String WFS_NAMESPACE = "http://www.opengis.net/wfs";

    private static final String OWS_NAMESPACE = "http://www.opengis.net/ows";

    private static final String XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";

    private final Document wfsCapabilities;

    public WFSClient( Document wfsCapabilities ) {
        this.wfsCapabilities = wfsCapabilities;
    }

    /**
     *
     * @param typeName
     *            never <code>null</code>
     * @return <code>null</code> if no GET or POST DCP URL is found
     * @throws XPathExpressionException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Document getFeatureByType( QName typeName )
                    throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        String getUrl = retrieveUrl( "GetFeature", "Get" );
        if ( getUrl != null && !getUrl.isEmpty() ) {
            return handleGetFeatureGet( typeName, getUrl );
        } else {
            String postUrl = retrieveUrl( "GetFeature", "Post" );
            if ( postUrl != null && !postUrl.isEmpty() ) {
                return handleGetFeaturePost( typeName, postUrl );
            } else
                return null;
        }
    }

    /**
     * 
     * @return <code>null</code> if no GET or POST DCP URL is found
     * @throws XPathExpressionException
     * @throws IOException
     */
    public InputStream getFeatureType()
                    throws XPathExpressionException, IOException {
        String getUrl = retrieveUrl( "DescribeFeatureType", "Get" );
        if ( getUrl != null && !getUrl.isEmpty() ) {
            return handleGetFeatureTypeGet( getUrl );
        } else {
            String postUrl = retrieveUrl( "DescribeFeatureType", "Post" );
            if ( postUrl != null && !postUrl.isEmpty() ) {
                return handleGetFeatureTypePost( postUrl );
            } else
                return null;
        }
    }

    private String retrieveUrl( String operation, String method )
                    throws XPathExpressionException {
        XPath xpath = createXPath();
        String urlExpression = "//wfs:WFS_Capabilities/ows:OperationsMetadata/ows:Operation[@name='" + operation
                               + "']/ows:DCP/ows:HTTP/ows:" + method + "/@xlink:href";
        return (String) xpath.evaluate( urlExpression, wfsCapabilities, XPathConstants.STRING );
    }

    private XPath createXPath() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NamespaceBindings nsBindings = new NamespaceBindings();
        nsBindings.addNamespaceBinding( WFS_NAMESPACE, "wfs" );
        nsBindings.addNamespaceBinding( OWS_NAMESPACE, "ows" );
        nsBindings.addNamespaceBinding( XLINK_NAMESPACE, "xlink" );
        xpath.setNamespaceContext( nsBindings );
        return xpath;
    }

    private Document handleGetFeatureGet( QName typeName, String getUrl )
                    throws IOException, SAXException, ParserConfigurationException {
        String request = buildGetFeatureGetRequest( typeName, getUrl );
        InputStream stream = new URL( request ).openStream();
        return createDocumentBuilder().parse( stream );
    }

    private String buildGetFeatureGetRequest( QName typeName, String getUrl ) {
        StringBuilder sb = new StringBuilder();
        sb.append( getUrl );
        sb.append( "service=WFS&version=1.1.0&request=GetFeature&" );
        sb.append( "typename=" );
        String namespaceURI = typeName.getNamespaceURI();
        if ( namespaceURI != null )
            sb.append( "app:" );
        sb.append( typeName.getLocalPart() ).append( "&" );
        if ( namespaceURI != null ) {
            sb.append( "namespace=xmlns(app=" );
            sb.append( namespaceURI ).append( ")" );
        }
        return sb.toString();
    }

    private Document handleGetFeaturePost( QName typeName, String postUrl )
                    throws IOException, SAXException, ParserConfigurationException {
        HttpURLConnection connection = (HttpURLConnection) new URL( postUrl ).openConnection();
        connection.setRequestMethod( "POST" );
        connection.setDoOutput( true );
        connection.setRequestProperty( "Content-Type", "text/plain; charset=UTF-8" );
        DataOutputStream writer = new DataOutputStream( connection.getOutputStream() );
        String body = buildGetFeaturePostBody( typeName );
        writer.writeBytes( body );
        writer.flush();
        writer.close();
        InputStream stream = connection.getInputStream();
        return createDocumentBuilder().parse( stream );
    }

    private String buildGetFeaturePostBody( QName typeName ) {
        StringBuilder sb = new StringBuilder();
        String namespaceURI = typeName.getNamespaceURI();
        sb.append( "<wfs:GetFeature xmlns:wfs='http://www.opengis.net/wfs' version='1.1.0' service='WFS'><wfs:Query " );
        if ( namespaceURI != null ) {
            sb.append( "xmlns:app='" );
            sb.append( namespaceURI ).append( "'" );
        }
        sb.append( " typeName='" );
        if ( namespaceURI != null )
            sb.append( "app:" );
        sb.append( typeName.getLocalPart() );
        sb.append( "'/></wfs:GetFeature>" );
        return sb.toString();
    }

    private DocumentBuilder createDocumentBuilder()
                    throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware( true );
        return factory.newDocumentBuilder();
    }

    private InputStream handleGetFeatureTypeGet( String getUrl )
                    throws IOException {
        String request = buildGetFeatureTypeGetRequest( getUrl );
        return new URL( request ).openStream();
    }

    private String buildGetFeatureTypeGetRequest( String getUrl ) {
        StringBuilder sb = new StringBuilder();
        sb.append( getUrl );
        sb.append( "service=WFS&version=1.1.0&request=DescribeFeatureType" );
        return sb.toString();
    }

    private InputStream handleGetFeatureTypePost( String postUrl )
                    throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL( postUrl ).openConnection();
        connection.setRequestMethod( "POST" );
        connection.setDoOutput( true );
        connection.setRequestProperty( "Content-Type", "text/plain; charset=UTF-8" );
        DataOutputStream writer = new DataOutputStream( connection.getOutputStream() );
        String body = "<wfs:DescribeFeatureType xmlns:wfs='http://www.opengis.net/wfs' service='WFS' version='1.1.0'/>";
        writer.writeBytes( body );
        writer.flush();
        writer.close();
        return connection.getInputStream();
    }

}