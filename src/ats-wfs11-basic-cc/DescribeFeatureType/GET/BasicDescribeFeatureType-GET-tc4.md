# BasicDescribeFeatureType-GET-tc4

**Purpose**:  <ctl:assertion>The MIME returned for a DescribeFeatureType request where no specific output format is requested is "text/xml; subtype=gml/3.1.1".

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=DescribeFeatureType


**Conditions**

* Response for DescribeFeatureType complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response header contains headers/header/@name = 'content-type']
* Response content-type is 'text/xml; subtype=gml/3.1.1'

**Reference(s)**: OGC 04-094, 6.4, p.11, OGC 04-094, 8.2, p.25

**Test type**: Automated

**Notes**

former ETS tc: wfs:DescribeFeatureType-output-format-default


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to )
-----------------------------------------------| -------------------------------------------------------------------------
  | 

