# BasicGetFeature-GET-tc4

**Purpose**: The following query parameters are required for all KVP-encoded service requests except GetCapabilities: service, request, version

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname
* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SeERVICE=WFS&VERSION=1.1.0&REQUEST=GetFeature&typename=$ftname
* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VeERSION=1.1.0&REQUEST=GetFeature&typename=$ftname
* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVICE=WFS&VERSION=1.1.0&ReEQUEST=GetFeature&typename=$ftname

**Conditions**

* Response to first request complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Response to first contains [wfs:FeatureCollection]
* Responses to all other requests comply to http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd


**Reference(s)**: OGC 05-008c1, 9.2.1, p.36 (Table 20) 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc23.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

