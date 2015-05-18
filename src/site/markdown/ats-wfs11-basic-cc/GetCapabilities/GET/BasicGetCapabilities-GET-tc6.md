# BasicGetCapabilities-GET-tc6

**Purpose**: Parameter ordering in KVP-encoded query strings is not significant

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&VERSION=1.1.0&SERVICE=WFS
* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?VERSION=1.1.0&SERVICE=WFS&REQUEST=GetCapabilities

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd for each of the test steps
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) for each of the test steps


**Reference(s)**: OGC 04-094, 14.2.1, p.94

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc8.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities

