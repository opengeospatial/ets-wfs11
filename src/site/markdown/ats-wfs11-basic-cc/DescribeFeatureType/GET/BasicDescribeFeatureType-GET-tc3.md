# BasicDescribeFeatureType-GET-tc3

**Purpose**: A request with no TypeName elements shall produce a response that includes definitions of all supported feature types in the requested schema language.

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by GET: wfs.DescribeFeatureType.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=DescribeFeatureType
* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetCapabilities
* Compare occurences of FeatureTypes within both responses

**Conditions**

* Response for DescribeFeatureType complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response for DescribeFeatureType contains all FeatureTypes stated within the GetCapabilities Response FeatureTypeList section

**Reference(s)**: OGC 04-094, 8.2, p.25

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-DescribeFeatureType-tc12.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to )
-----------------------------------------------| -------------------------------------------------------------------------
  | 

