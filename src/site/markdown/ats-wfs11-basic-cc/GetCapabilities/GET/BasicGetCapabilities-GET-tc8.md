# BasicGetCapabilities-GET-tc8

**Purpose**: Parameter names in KVP strings shall be handled in a case-insensitive manner.

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&VERSION=1.1.0&SERVICE=WFS
* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?request=GetCapabilities&version=1.1.0&service=WFS
* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?rEQuEsT=GetCapabilities&VeRsIoN=1.1.0&sErViCe=WFS

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd for each the test steps
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) for each the test steps


**Reference(s)**: OGC 05-008c1, 11.5.2, p.56

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc10.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities

