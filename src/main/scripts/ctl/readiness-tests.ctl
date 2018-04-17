<?xml version="1.0" encoding="UTF-8"?>
<ctl:package
        xmlns="http://www.w3.org/2001/XMLSchema"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:ctl="http://www.occamlab.com/ctl"
        xmlns:xi="http://www.w3.org/2001/XInclude"
        xmlns:wfs="http://www.opengis.net/wfs">

  <xi:include href="parsers.ctl"/>

  <ctl:test name="wfs:readiness-tests" isConformanceClass="true" isBasic="true">
    <ctl:param name="wfs.GetCapabilities.document" />
    <ctl:param name="wfs-transaction" />
    <ctl:assertion>
      Assess readiness of the IUT. Check the retrieved capabilities document for
      available service endpoints.
    </ctl:assertion>
    <ctl:comment>
      The capabilities document is first checked for the presence of required
      HTTP method bindings.
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

    </ctl:code>
  </ctl:test>

</ctl:package>

