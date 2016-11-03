# BasicDescribeFeatureType-GET-tc5

**Purpose**:   The response for an invalid DescribeFeatureType request is an exception

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?VERSION=1.1.0&REQUEST=DescribeFeatureType


**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd
* Response contains [ows:Exception](#ows:Exception)

**Reference(s)**: OGC 04-094, 8.4, p.27

**Test type**: Automated

**Notes**

former ETS tc: wfs:DescribeFeatureType-invalid-featureType


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception)
-----------------------------------------------| -------------------------------------------------------------------------
  []ows:Exception | /ows:Exception

