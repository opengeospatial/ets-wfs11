# BasicGetCapabilities-GET-tc11

**Purpose**:  A KVP-encoded GetCapabilities request shall include the following query parameters: service=WxS request=GetCapabilities?, where WxS is a mnemonic label for a service type

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&VERSION=1.1.0&SERVICE=WFS

**Conditions**

* Response complies to xml schema: http://schemas.opengis# BasicGetCapabilities-GET-tc10

**Reference(s)**: OGC 05-008c1, 7.1, p.10

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc13.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities/@version='1.1.0' <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities/@version='1.1.0'
