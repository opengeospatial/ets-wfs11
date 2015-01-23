# BasicDescribeFeatureType-GET-tc2

**Purpose**: Application schemas that comply with GML 3.1.1 must be a supported. These are presented if no outputFormat is specified.

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=DescribeFeatureType

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd


**Reference(s)**: OGC 05-008c1, 11.5.3, p.57

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-DescribeFeatureType-tc8.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to )
-----------------------------------------------| -------------------------------------------------------------------------
  | 

