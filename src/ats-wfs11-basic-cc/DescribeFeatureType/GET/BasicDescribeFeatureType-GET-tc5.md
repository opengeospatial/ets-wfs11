# BasicDescribeFeatureType-GET-tc5

**Purpose**:   The response for an invalid DescribeFeatureType request is an exception

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?VERSION=1.1.0&REQUEST=DescribeFeatureType
* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?SERVICE=WFS&REQUEST=DescribeFeatureType


**Conditions**

* Response for DescribeFeatureType complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd for each request
* Response for DescribeFeatureType contains [ows:Exception](#ows:Exception) for each request

**Reference(s)**: OGC 04-094, 8.4, p.27

**Test type**: Automated

**Notes**

former ETS tc: wfs:DescribeFeatureType-invalid-featureType


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception)
-----------------------------------------------| -------------------------------------------------------------------------
  []ows:Exception | /ows:Exception

