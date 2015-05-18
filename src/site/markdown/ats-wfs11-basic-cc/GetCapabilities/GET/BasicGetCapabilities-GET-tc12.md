# BasicGetCapabilities-GET-tc12

**Purpose**:  If AcceptVersion is not specified, the service must respond with highest supported version.

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&SERVICE=WFS

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd 
* Response contains [wfs:WFS_Capabilities/@version='1.1.0'](#wfs:WFS_Capabilities/@version='1.1.0') 

**Reference(s)**: OGC 05-008c1, 7.3.2, p.16

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc14.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities/@version='1.1.0' <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities/@version='1.1.0'
