# BasicDescribeFeatureType-POST-tc1

**Purpose**:  <assertion>The following attributes are required for all XML request entities: @version (default '1.1.0'); @service (default 'WFS').

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by POST: wfs.DescribeFeatureType.get.url

  ```
  <wfs:DescribeFeatureType service="WFS"  xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0">
  </wfs:DescribeFeatureType>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd


**Reference(s)**: OGC 04-094, 7.8, p.24

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-DescribeFeatureType-tc3.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to )
-----------------------------------------------| -------------------------------------------------------------------------
  | 

