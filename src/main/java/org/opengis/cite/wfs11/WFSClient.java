package org.opengis.cite.wfs11;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
     * @return <code>null</code> if no GET or POST DCP URL is found
     * @throws Exception
     */
    public Document getFeatureByType( QName typeName )
                    throws Exception {
        String getUrl = retrieveUrl( "Get" );
        if ( getUrl != null && !getUrl.isEmpty() ) {
            return handleGetUrl( typeName, getUrl );
        } else {
            String postUrl = retrieveUrl( "Post" );
            if ( postUrl != null && !postUrl.isEmpty() ) {
                return handlePost( typeName, postUrl );

            } else
                return null;
        }
    }

    public InputStream getFeatureType()
                    throws MalformedURLException, IOException {
        // TODO Auto-generated method stub
        return new URL(
                        "http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?&service=WFS&version=1.1.0&request=DescribeFeatureType" ).openStream();
    }

    private String retrieveUrl( String method )
                    throws XPathExpressionException {
        XPath xpath = createXPath();
        String urlExpression = "//wfs:WFS_Capabilities/ows:OperationsMetadata/ows:Operation[@name='GetFeature']/ows:DCP/ows:HTTP/ows:"
                               + method + "/@xlink:href";
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

    private Document handleGetUrl( QName typeName, String getUrl )
                    throws IOException, SAXException, ParserConfigurationException {
        String request = buildGetRequest( typeName, getUrl );
        InputStream stream = new URL( request ).openStream();
        return createDocumentBuilder().parse( stream );
    }

    private String buildGetRequest( QName typeName, String getUrl ) {
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

    private Document handlePost( QName typeName, String postUrl )
                    throws IOException, SAXException, ParserConfigurationException {
        String body = buildPostBody( typeName );
        HttpURLConnection connection = (HttpURLConnection) new URL( postUrl ).openConnection();
        connection.setRequestMethod( "POST" );
        connection.setDoOutput( true );
        connection.setRequestProperty( "Content-Type", "text/plain; charset=UTF-8" );
        DataOutputStream writer = new DataOutputStream( connection.getOutputStream() );
        writer.writeBytes( body );
        writer.flush();
        writer.close();
        InputStream stream = connection.getInputStream();
        return createDocumentBuilder().parse( stream );
    }

    private String buildPostBody( QName typeName ) {
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

    // private static String parseDescribeFeatureTypeUrl(Document
    // wfsCapabilities)
    // throws XPathExpressionException {
    // XPath xpath = XPathFactory.newInstance().newXPath();
    // NamespaceBindings nsBindings = new NamespaceBindings();
    // nsBindings.addNamespaceBinding(Namespaces.OWS, "ows");
    // nsBindings.addNamespaceBinding(WFS_NAMESPACE, "wfs");
    // xpath.setNamespaceContext(nsBindings);
    // return
    // xpath.evaluate("$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='DescribeFeatureType']/ows:DCP/ows:HTTP/ows:Get/@xlink:href",
    // wfsCapabilities);
    // }

}