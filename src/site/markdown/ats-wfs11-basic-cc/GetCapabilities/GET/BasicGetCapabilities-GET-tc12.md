# BasicGetCapabilities-GET-tc12

**Purpose**:  If AcceptVersion is not specified, the service must respond with highest supported version.

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&SERVICE=WFS

**Conditions**
 
* Response contains [WFS_Capabilities/@version='1.1.0'](#WFS_Capabilities/@version='1.1.0') if 1.1.0 is the highest supported version ([ServiceTypeVersion](#ServiceTypeVersion))
* Response contains [WFS_Capabilities/@version='2.0.0'](#WFS_Capabilities/@version='2.0.0') if 2.2.0 is the highest supported version ([ServiceTypeVersion](#ServiceTypeVersion))

**Reference(s)**: OGC 05-008c1, 7.3.2, p.16

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc14.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
WFS_Capabilities/@version='1.1.0' <a name="WFS_Capabilities"></a>   | /WFS_Capabilities/@version='1.1.0'
WFS_Capabilities/@version='2.0.0' <a name="WFS_Capabilities"></a>   | /WFS_Capabilities/@version='2.0.0'
ServiceTypeVersion | //ServiceTypeVersion
