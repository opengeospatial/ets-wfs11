package org.opengis.cite.wfs11;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.dom.Document;

public class WFSClient {

    public WFSClient( Document wfsCapabilities ) {
        // TODO Auto-generated constructor stub
    }

    public Document getFeatureByType( QName typeName )
                    throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware( true );
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder sb = new StringBuilder();
        sb.append( "http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?" );
        sb.append( "service=WFS&version=1.1.0&request=GetFeature&" );
        sb.append( "bbox=34.94,-10.52,71.96,32.19,urn:ogc:def:crs:EPSG::4326&" );
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
        System.out.println( request );
        InputStream stream = new URL( request ).openStream();
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