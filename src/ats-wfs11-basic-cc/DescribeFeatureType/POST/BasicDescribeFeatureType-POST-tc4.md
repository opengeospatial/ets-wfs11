# BasicDescribeFeatureType-POST-tc4

**Purpose**:  Application schemas that comply with GML 3.1.1 must be a supported. These are presented if no outputFormat is specified

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType requests by POST: wfs.DescribeFeatureType.post.url

  ```
  <wfs:DescribeFeatureType   xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0">
  </wfs:DescribeFeatureType>
  ```
  
  ```
  <wfs:DescribeFeatureType   xmlns:wfs="http://www.opengis.net/wfs" service="WFS" >
  </wfs:DescribeFeatureType>
  ```

**Conditions**


* Response for DescribeFeatureType complies to xml schema: http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd for each request
* Response for DescribeFeatureType contains [ows:Exception](#ows:Exception) for each request


**Reference(s)**: OGC 04-094, 8.1, p.24

**Test type**: Automated

**Notes**

former ETS tc: 


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception )
-----------------------------------------------| -------------------------------------------------------------------------
  [ows:Exception] | /ows:Exception| 

