# BasicGetFeature-GET-tc8

**Purpose**: a web feature service must generate a <wfs:FeatureCollection> element with no content (i.e. empty) but must populate the values of the timeStamp attribute and the numberOfFeatures attribute 

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname&Resulttype=hits

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:FeatureCollection]
* Response contains [wfs:FeatureCollection/@timeStamp && wfs:FeatureCollection/@numberOfFeatures]



**Reference(s)**: OGC 04-094, 9.3, p. 38 

**Test type**: Automated

**Notes**

former ETS tc: 


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

