# BasicGetFeature-GET-tc2

**Purpose**: A valid HTTP Get request with an extra parameter not recognized by the WFS does not return an exception.

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname&bogus=parameter

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response contains [wfs:FeatureCollection]


**Reference(s)**: OGC 04-094, 9.3, p.38 

**Test type**: Automated

**Notes**

former ETS tc: wfs:GetFeature-extra-param


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

