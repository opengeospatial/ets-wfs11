# BasicGetCapabilities-GET-tc3

**Purpose**: Host names in a URI are case-insensitive

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request (all uppercase) by GET: wfs.GetCapabilities.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetCapabilities
* Send the following GetCapabilities request (all mixed-case) by GET: wfs.GetCapabilities.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetCapabilities
* Send the following GetCapabilities request (all lowercase) by GET: wfs.GetCapabilities.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetCapabilities

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd for each of the test steps
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) for each of the test steps


**Reference(s)**: OGC 04-094, 6.3.1, p.10, RFC 3986, 3.2.2 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc4.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities

