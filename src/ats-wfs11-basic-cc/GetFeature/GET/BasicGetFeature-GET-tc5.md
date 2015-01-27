# BasicGetFeature-GET-tc4

**Purpose**: XPath 1.0 shall be used to address parts of an XML representation using ogc:PropertyName. Support for the AbbreviatedRelativeLocationPath construct is required

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname&FILTER=%3Cogc%3AFilter%20xmlns%3Aogc%3D%22http%3A%2F%2Fwww.opengis.net%2Fogc%22%20xmlns%3Agml%3D%22http%3A%2F%2Fwww.opengis.net%2Fgml%22%3E%3Cogc%3APropertyIsEqualTo%3E%3Cogc%3APropertyName%3E$PROPERTYNAME%5B1%5D%3C%2Fogc%3APropertyName%3E%3Cogc%3ALiteral%3E$CONTENT%3C%2Fogc%3ALiteral%3E%3C%2Fogc%3APropertyIsEqualTo%3E%3C%2Fogc%3AFilter%3E

**Conditions**

* Response to request complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response to contains [wfs:FeatureCollection]



**Reference(s)**: OGC 04-095, 7.4.2, p.18 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc30.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

