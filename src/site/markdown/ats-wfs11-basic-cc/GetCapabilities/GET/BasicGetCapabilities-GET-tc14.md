# BasicGetCapabilities-GET-tc14

**Purpose**:  A valid service metadata document must conform to a content model based on the ows:CapabilitiesBaseType definition. Additional service-specific elements may be defined as needed.

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&SERVICE=WFS&Version=1.1.0

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd 
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) 

**Reference(s)**: OGC 05-008c1, 7.4.8, p.29

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc17.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities
