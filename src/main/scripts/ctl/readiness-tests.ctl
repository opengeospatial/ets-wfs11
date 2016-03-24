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

	<ctl:test name="wfs:readiness-tests">
		<ctl:param name="wfs.GetCapabilities.document"/>
		<ctl:param name="wfs-transaction"/>
        <ctl:param name="wfs-locking"/>
        <ctl:param name="wfs-xlink"/>
		<ctl:param name="gmlsf.profile.level"/>
		<ctl:param name="conformance.class"/>
		<ctl:param name="conformance.class.method.get"/>
		<ctl:param name="conformance.class.method.post"/>
		<ctl:assertion>
        Assess readiness of the IUT. Check the retrieved capabilities document for
        available service endpoints; determine if the service is available and
        is ready to undergo further testing.
        </ctl:assertion>
        <ctl:comment>
        The capabilities document is first checked for the presence of required
        HTTP method bindings. Then a GetCapabilities request is submitted to the
        SUT using the GET method. A subsequent GetFeature request to retrieve
        one of the records in the test data set is then submitted and checked
        for a non-empty response. If any of these checks fail, execution of the
        test suite is aborted.
        </ctl:comment>
		<ctl:code>
          <xsl:choose>
            <xsl:when test="string-length($wfs-transaction) gt 0">
              <ctl:call-test name="ctl:SchematronValidatingParser">
		        <ctl:with-param name="doc" select="$wfs.GetCapabilities.document" />
		        <ctl:with-param name="schema">/sch/wfs/1.1.0/Capabilities.sch</ctl:with-param>
		        <ctl:with-param name="phase">RequiredTransactionBindingsPhase</ctl:with-param>
	          </ctl:call-test>
            </xsl:when>
            <xsl:otherwise>
              <ctl:call-test name="ctl:SchematronValidatingParser">
		        <ctl:with-param name="doc" select="$wfs.GetCapabilities.document" />
		        <ctl:with-param name="schema">/sch/wfs/1.1.0/Capabilities.sch</ctl:with-param>
		        <ctl:with-param name="phase">RequiredBasicElementsPhase</ctl:with-param>
	          </ctl:call-test>
            </xsl:otherwise>
          </xsl:choose>

            <xsl:variable name="GetCapabilities.get.url">
		        <xsl:value-of select="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetCapabilities']/ows:DCP/ows:HTTP/ows:Get/@xlink:href"/>
		    </xsl:variable>
            <xsl:variable name="GetFeature.get.url">
			    <xsl:value-of select="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetFeature']/ows:DCP/ows:HTTP/ows:Get/@xlink:href"/>
		    </xsl:variable>
            <xsl:variable name="GetFeature.post.url">
			    <xsl:value-of select="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetFeature']/ows:DCP/ows:HTTP/ows:Post/@xlink:href"/>
		    </xsl:variable>

            <xsl:choose>
              <xsl:when test="not(starts-with($GetCapabilities.get.url,'http'))">
                  <ctl:message>
                  FAILURE: HTTP endpoint for GetCapabilities using GET method not found in capabilities document.
                  </ctl:message>
                  <ctl:fail />
              </xsl:when>
              <xsl:when test="not(starts-with($GetFeature.post.url,'http')) and not(starts-with($GetFeature.get.url,'http'))">
                  <ctl:message>
                  FAILURE: HTTP endpoint for GetFeature using POST or GET method not found in capabilities document.
                  </ctl:message>
                  <ctl:fail />
              </xsl:when>
              <xsl:otherwise>
					 <ctl:call-test name="wfs:basic-main">
						<ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
						<ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level"/>
						<ctl:with-param name="wfs-xlink" select="$wfs-xlink"/>
						<ctl:with-param name="conformance.class" select="$conformance.class"/>
						<ctl:with-param name="conformance.class.method.get" select="$conformance.class.method.get"/>
						<ctl:with-param name="conformance.class.method.post" select="$conformance.class.method.post"/>
					 </ctl:call-test>
					 <xsl:if test="string-length($wfs-transaction) gt 0">
						<ctl:call-test name="wfs:transaction-main">
							<ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
							<ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level"/>
						</ctl:call-test>
					 </xsl:if>
                     <xsl:if test="string-length($wfs-locking) gt 0">
                      <ctl:call-test name="wfs:locking-main">
                        <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
                        <ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level"/>
                      </ctl:call-test>
                     </xsl:if>
					 <xsl:if test="string-length($wfs-xlink) gt 0">
                       <ctl:call-test name="wfs:XLinkTests">
							<ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
						</ctl:call-test>
                     </xsl:if>
              </xsl:otherwise>
            </xsl:choose>
		</ctl:code>
	</ctl:test>

</ctl:package>

