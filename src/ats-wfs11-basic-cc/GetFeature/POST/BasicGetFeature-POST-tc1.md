# BasicGetFeature-POST-tc1

**Purpose**: The default output format is XML that complies with GML 3.1.1

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
´´´<?xml version="1.0" encoding="UTF-8"?>
<wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0" service="WFS">
<wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
</wfs:GetFeature>´´´

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:FeatureCollection]



**Reference(s)**: OGC 04-094, 9.2, p.34 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc1.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

