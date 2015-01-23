# BasicDescribeFeatureType-GET-tc1

**Purpose**: The following query parameters are required for all KVP-encoded service requests except GetCapabilities: service, request, version

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=DescribeFeatureType

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd


**Reference(s)**: OGC 05-008c1, 9.2.1, p.36 (Table 20)

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-DescribeFeatureType-tc4.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities

