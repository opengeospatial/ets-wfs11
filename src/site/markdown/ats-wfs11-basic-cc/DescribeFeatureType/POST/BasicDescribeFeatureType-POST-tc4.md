# BasicDescribeFeatureType-POST-tc4

**Purpose**:  The following attributes are required for all XML request entities: @version (default '1.1.0'); @service (default 'WFS')

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType requests by POST: 

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <wfs:DescribeFeatureType   xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0" service="WFS">
  </wfs:DescribeFeatureType>
  ```
  
* Send the following DescribeFeatureType requests by POST: 

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <wfs:DescribeFeatureType   xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0">
  </wfs:DescribeFeatureType>
  ```

* Send the following DescribeFeatureType requests by POST: 

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <wfs:DescribeFeatureType   xmlns:wfs="http://www.opengis.net/wfs" service="WFS" >
  </wfs:DescribeFeatureType>
  ```
  
**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd


**Reference(s)**: OGC 04-094, 8.1, p.24

**Test type**: Automated

**Notes**

former ETS tc: 


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception )
-----------------------------------------------| -------------------------------------------------------------------------
  [ows:Exception] | /ows:Exception| 

