# BasicGetCapabilities-GET-tc5

**Purpose**: Unrecognized parameters in KVP-encoded query strings shall be ignored.

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetCapabilities&ASDF=asdf:asdf&versionXXX=x.x.x,x.y.z

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd for each of the test steps
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) for each of the test steps


**Reference(s)**: OGC 04-094, 14.2.1, p.94

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc7.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities

