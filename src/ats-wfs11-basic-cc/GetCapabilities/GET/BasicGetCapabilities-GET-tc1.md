# BasicGetCapabilities-GET-tc1

**Purpose**: The GET method request must be supported (using HTTP GET)

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?SERVICE=WFS%VERSION=1.1.0&REQUEST=GetCapabilities
* Response shall comply xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response shall contain [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) element.

**Reference(s)**: OGC 04-094, 13.1, p.79

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc1.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | //wfs:WFS_Capabilities[1]

