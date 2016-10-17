<?xml version="1.0" encoding="UTF-8"?>
<ctl:package
 xmlns="http://www.w3.org/2001/XMLSchema"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:ctl="http://www.occamlab.com/ctl"
 xmlns:parsers="http://www.occamlab.com/te/parsers"
 xmlns:p="http://teamengine.sourceforge.net/parsers"
 xmlns:saxon="http://saxon.sf.net/"
 xmlns:wfs="http://www.opengis.net/wfs"
 xmlns:gml="http://www.opengis.net/gml"
 xmlns:ows="http://www.opengis.net/ows"
 xmlns:xlink="http://www.w3.org/1999/xlink"
 xmlns:xi="http://www.w3.org/2001/XInclude"
 xmlns:xsd="http://www.w3.org/2001/XMLSchema">

  <!-- include test groups for the WFS-Locking conformance class -->
  <xi:include href="LockFeature/LockFeature-XML.xml"/>
  <xi:include href="GetFeatureWithLock/GetFeatureWithLock-XML.xml"/>

	<ctl:test name="wfs:locking-main" isConformanceClass="true" isBasic="false">
      <ctl:param name="wfs.GetCapabilities.document"/>
      <ctl:param name="gmlsf.profile.level"/>
      <ctl:assertion>Run test group for the WFS-Locking conformance class.</ctl:assertion>
	  <ctl:code>
	  
         <!-- determine if service supports atomic transactions -->
         <xsl:variable name="supports.atomic.trx" as="xsd:boolean">
           <xsl:variable name="rsp0">
			 <ctl:request>
				<ctl:url>
				  <xsl:value-of select="$wfs.GetCapabilities.document//ows:Operation[@name='Transaction']/ows:DCP/ows:HTTP/ows:Post/@xlink:href"/>
				</ctl:url>
                <ctl:method>POST</ctl:method>
				<ctl:body>
<wfs:Transaction service="WFS" version="1.1.0"
  xmlns:wfs="http://www.opengis.net/wfs"
  xmlns:gml="http://www.opengis.net/gml"
  xmlns:sf="http://cite.opengeospatial.org/gmlsf">
	<wfs:Insert handle="insert-1">
      <sf:UnknownFeature gml:id="id20080125">
        <gml:name codeSpace="http://cite.opengeospatial.org/gmlsf">id20080125</gml:name>
      </sf:UnknownFeature>
    </wfs:Insert>
</wfs:Transaction>
				</ctl:body>
				<p:XMLValidatingParser.GMLSF1/>
			 </ctl:request>
		   </xsl:variable>
           <xsl:choose>
             <xsl:when test="$rsp0//wfs:TransactionResults"><xsl:value-of select="false()"/></xsl:when>
             <xsl:otherwise>
               <!-- received exception report -->
               <xsl:value-of select="true()"/>
             </xsl:otherwise>
           </xsl:choose>
         </xsl:variable>

         <xsl:choose>
			 <xsl:when test="$supports.atomic.trx">
                 <ctl:message>The service under test supports atomic transactions.</ctl:message>
			 </xsl:when>
			 <xsl:otherwise>
                 <ctl:message>The service under test does NOT support atomic transactions.</ctl:message>
			 </xsl:otherwise>
         </xsl:choose>  
         
		 <xsl:if test="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='LockFeature']/ows:DCP/ows:HTTP/ows:Post/@xlink:href">
		   <ctl:message>LockFeature using the POST method is implemented.</ctl:message>
		   <ctl:call-test name="wfs:run-LockFeature-POST">
			 <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
			 <ctl:with-param name="supports.atomic.trx" select="$supports.atomic.trx"/>
		   </ctl:call-test>
		 </xsl:if>
		 <xsl:if test="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetFeatureWithLock']/ows:DCP/ows:HTTP/ows:Post/@xlink:href">
		   <ctl:message>GetFeatureWithLock using the POST method is implemented.</ctl:message>
		   <ctl:call-test name="wfs:run-GetFeatureWithLock-POST">
			 <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
			 <ctl:with-param name="supports.atomic.trx" select="$supports.atomic.trx"/>
		   </ctl:call-test>
		 </xsl:if>

      </ctl:code>
    </ctl:test>
</ctl:package>

