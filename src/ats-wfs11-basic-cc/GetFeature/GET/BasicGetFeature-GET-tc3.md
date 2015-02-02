# BasicGetFeature-GET-tc3

**Purpose**: A GetFeature request with resultType=Results returns the actual features, not just the count of number of hits

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname&resulttype=results

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response contains [wfs:FeatureCollection]


**Reference(s)**: OGC 04-094, 9.3, p.38 

**Test type**: Automated

**Notes**

former ETS tc: wfs:GetFeature-result-type-results


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

