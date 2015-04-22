# BasicGetCapabilities-GET-tc7

**Purpose**: A valid http URL value shall conform to the syntax specified in RFC 2616. http_URL = 'http:' '' host [ ':' port ] [ abs_path [ '?' query ]]

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&VERSION=1.1.0&SERVICE=WFS
* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url,REQUEST=GetCapabilities#VERSION=1.1.0#SERVICE=WFS

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd for the first test step
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) for the first test step
* Response contains [ows:ExceptionReport](#ows:ExceptionReport) for the second test step


**Reference(s)**: OGC 05-008c1, 11.1, p.54

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc9.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities
ows:ExceptionReport <a name="ows:ExceptionReport"></a>   | /ows:ExceptionReport

