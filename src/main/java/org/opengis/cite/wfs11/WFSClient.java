package org.opengis.cite.wfs11;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware( true );
        DocumentBuilder builder = factory.newDocumentBuilder();
        XPath xpath = createXPath();
        String getUrl = retrieveUrl( "Get", xpath );
        if ( getUrl != null && !getUrl.isEmpty() ) {
            return handleGetUrl( typeName, builder, getUrl );
        } else {
            String postUrl = retrieveUrl( "Post", xpath );
            if ( postUrl != null && !postUrl.isEmpty() ) {
                return handlePost( builder, postUrl );

            } else
                return null;
        }
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

    private String retrieveUrl( String method, XPath xpath )
                    throws XPathExpressionException {
        String urlExpression = "//wfs:WFS_Capabilities/ows:OperationsMetadata/ows:Operation[@name='GetFeature']/ows:DCP/ows:HTTP/ows:"
                               + method + "/@xlink:href";
        return (String) xpath.evaluate( urlExpression, wfsCapabilities, XPathConstants.STRING );
    }

    private Document handleGetUrl( QName typeName, DocumentBuilder builder, String getUrl )
                    throws IOException, SAXException {
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
        String request = sb.toString();
        InputStream stream = new URL( request ).openStream();
        return builder.parse( stream );
    }

    private Document handlePost( DocumentBuilder builder, String postUrl )
                    throws IOException, SAXException {
        // TODO: Insert body!
        String body = "";
        HttpURLConnection connection = (HttpURLConnection) new URL( postUrl ).openConnection();
        connection.setRequestMethod( "POST" );
        connection.setDoInput( true );
        connection.setDoOutput( true );
        connection.setUseCaches( false );
        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
        connection.setRequestProperty( "Content-Length", String.valueOf( body.length() ) );
        OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
        writer.write( body );
        writer.flush();
        InputStream stream = connection.getInputStream();
        return builder.parse( stream );
    }

    public InputStream getFeatureType()
                    throws MalformedURLException, IOException {
        // TODO Auto-generated method stub
        return new URL(
                        "http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?&service=WFS&version=1.1.0&request=DescribeFeatureType" ).openStream();
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