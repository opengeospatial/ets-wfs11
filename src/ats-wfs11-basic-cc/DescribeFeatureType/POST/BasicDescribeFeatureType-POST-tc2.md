# BasicDescribeFeatureType-POST-tc2

**Purpose**:  Application schemas that comply with GML 3.1.1 must be a supported. These are presented if no outputFormat is specified

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by POST: wfs.DescribeFeatureType.get.url

  ```
  <wfs:DescribeFeatureType service="WFS"  xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0">
  </wfs:DescribeFeatureType>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd


**Reference(s)**: OGC 04-094, 8.1, p.24

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-DescribeFeatureType-tc7.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to )
-----------------------------------------------| -------------------------------------------------------------------------
  | 

