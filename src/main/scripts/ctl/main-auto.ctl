<?xml version="1.0" encoding="UTF-8"?>
<ctl:package
 xmlns="http://www.w3.org/2001/XMLSchema"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:ctl="http://www.occamlab.com/ctl"
 xmlns:parsers="http://www.occamlab.com/te/parsers"
 xmlns:myparsers="http://teamengine.sourceforge.net/parsers"
 xmlns:saxon="http://saxon.sf.net/"
 xmlns:wfs="http://www.opengis.net/wfs" 
 xmlns:sf="http://cite.opengeospatial.org/gmlsf" 
 xmlns:ows="http://www.opengis.net/ows"
 xmlns:xi="http://www.w3.org/2001/XInclude"
 xmlns:xlink="http://www.w3.org/1999/xlink"  
 xmlns:xsd="http://www.w3.org/2001/XMLSchema">

  <xi:include href="functions.ctl" />
  <xi:include href="parsers.ctl" />
  <xi:include href="readiness-tests.ctl" />
  <xi:include href="asserts.ctl" />
  <xi:include href="basic/basic-main.xml" />
  <xi:include href="wfs-transaction/transaction-tests.ctl" />
  <xi:include href="wfs-locking/locking-tests.ctl" />
  <xi:include href="wfs-xlink/xlink-main.xml" />
	
	<ctl:suite name="ctl:wfs-1.1.0-compliance-suite-auto" version="1.1.0.2-M1">
		  <ctl:title>WFS 1.1.0 Compliance Test Suite (1.1.0.2-M1)</ctl:title>
		  <ctl:description>Verifies that a WFS 1.1.0 implementation complies with a given conformance class.</ctl:description>
          <ctl:link>docs/wfs/1.1.0/</ctl:link>
          <ctl:link>data/data-wfs-1.1.0.zip</ctl:link>
		  <ctl:starting-test>wfs:wfs-main-auto</ctl:starting-test>
	</ctl:suite>

  <ctl:test name="wfs:wfs-main-auto">
    <ctl:param name="capabilities-url" />
    <ctl:param name="wfs-transaction" />
    <ctl:param name="wfs-locking" />
    <ctl:param name="wfs-xlink" />
    <ctl:param name="profile" />
    <ctl:assertion>WFS 1.1.0 Tests</ctl:assertion>
    <ctl:comment>
      The capabilities document is first checked for the presence of required
      HTTP method bindings. Then a GetCapabilities request is submitted to the
      SUT using the GET method. A subsequent GetFeature request to retrieve
      one of the records in the test data set is then submitted and checked
      for a non-empty response. If any of these checks fail, execution of the
      test suite is aborted.
    </ctl:comment>
    <ctl:code>

      <!-- Get user input: -->
      <xsl:variable name="wfs.GetCapabilities.get.url" select="$capabilities-url" />
      <xsl:variable name="gmlsf.profile.level" select="$profile" />

      <!--TODO: Get GMLSF profile level from DescribeFeatureType and XPath expression (gmlsf conformance level 0 or 1) rather than user input-->

      <!-- Attempt to retrieve capabilities document -->

      <xsl:variable name="wfs.GetCapabilities.document">
        <ctl:request>
          <ctl:url>
            <xsl:value-of select="$wfs.GetCapabilities.get.url" />
          </ctl:url>
          <ctl:method>GET</ctl:method>
        </ctl:request>
      </xsl:variable>

      <!-- Call the readiness tests, which then call the conformance class tests -->
      <xsl:choose>
        <xsl:when test="not($wfs.GetCapabilities.document//wfs:WFS_Capabilities)">
          <ctl:message>FAILURE: Did not receive a wfs:WFS_Capabilities document! Skipping remaining tests.</ctl:message>
          <ctl:fail />
        </xsl:when>
        <xsl:otherwise>
          <!-- Ingest initial test data -->
          <!-- <xsl:variable name="ingest.data" select="$wfs-transaction"/>
          <xsl:if test="string-length($ingest.data) gt 0">
            <ctl:call-test name="wfs:ingest-test-data">
              <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document"/>
              <ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level"/>
            </ctl:call-test>
          </xsl:if> -->

          <ctl:call-test name="wfs:readiness-tests">
            <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document" />
            <ctl:with-param name="wfs-transaction" select="$wfs-transaction" />
          </ctl:call-test>

          <xsl:variable name="conformance.class">basic</xsl:variable>
          <xsl:variable name="conformance.class.method.get">true</xsl:variable>
          <xsl:variable name="conformance.class.method.post">true</xsl:variable>

          <xsl:variable name="GetCapabilities.get.url">
            <xsl:value-of
                    select="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetCapabilities']/ows:DCP/ows:HTTP/ows:Get/@xlink:href" />
          </xsl:variable>
          <xsl:variable name="GetFeature.get.url">
            <xsl:value-of
                    select="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetFeature']/ows:DCP/ows:HTTP/ows:Get/@xlink:href" />
          </xsl:variable>
          <xsl:variable name="GetFeature.post.url">
            <xsl:value-of
                    select="$wfs.GetCapabilities.document//ows:OperationsMetadata/ows:Operation[@name='GetFeature']/ows:DCP/ows:HTTP/ows:Post/@xlink:href" />
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
                <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document" />
                <ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level" />
                <ctl:with-param name="wfs-xlink" select="$wfs-xlink" />
                <ctl:with-param name="conformance.class" select="$conformance.class" />
                <ctl:with-param name="conformance.class.method.get" select="$conformance.class.method.get" />
                <ctl:with-param name="conformance.class.method.post" select="$conformance.class.method.post" />
              </ctl:call-test>
              <xsl:if test="string-length($wfs-transaction) gt 0">
                <ctl:call-test name="wfs:transaction-main">
                  <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document" />
                  <ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level" />
                </ctl:call-test>
              </xsl:if>
              <xsl:if test="string-length($wfs-locking) gt 0">
                <ctl:call-test name="wfs:locking-main">
                  <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document" />
                  <ctl:with-param name="gmlsf.profile.level" select="$gmlsf.profile.level" />
                </ctl:call-test>
              </xsl:if>
              <xsl:if test="string-length($wfs-xlink) gt 0">
                <ctl:call-test name="wfs:XLinkTests">
                  <ctl:with-param name="wfs.GetCapabilities.document" select="$wfs.GetCapabilities.document" />
                </ctl:call-test>
              </xsl:if>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
    </ctl:code>
  </ctl:test>
	
</ctl:package>

