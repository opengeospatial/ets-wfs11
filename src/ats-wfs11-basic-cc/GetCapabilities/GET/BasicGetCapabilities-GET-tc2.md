# BasicGetCapabilities-GET-tc2

**Purpose**: Version number specified in a given request must correspond to a version supported by the service (1.1.0)

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetCapabilities

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:WFS_Capabilities/@version=1.1.0](#wfs:WFS_Capabilities).


**Reference(s)**: OGC 04-094, 13.1, p.79

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc3.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities/@version="1.1.0"

