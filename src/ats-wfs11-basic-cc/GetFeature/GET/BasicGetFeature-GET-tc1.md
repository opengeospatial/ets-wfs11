# BasicGetFeature-GET-tc1

**Purpose**: The MIME returned for a GetFeature request where no specific output format is requested is "text/xml; subtype=gml/3.1.1".

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response contains [wfs:FeatureCollection]
* Content-Type of response is "text/xml; subtype=gml/3.1.1"


**Reference(s)**: OGC 04-094, 9.3, p.38 

**Test type**: Automated

**Notes**

former ETS tc: wfs:GetFeature-output-format-default


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

