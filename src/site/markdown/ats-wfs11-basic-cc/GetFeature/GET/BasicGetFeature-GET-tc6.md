# BasicGetFeature-GET-tc7

**Purpose**: If a request is unrecognizable or the service cannot process the request, a valid exception report must be returned

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by GET: wfs.GetFeature.get.url?SERVIsCE=WFS&VERSION=1.1.0&REQUsEST=GetFeature&typename=$ftname

**Conditions**

* Response to request complies to xml schema: http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd
* Response to contains [ows:Exception]



**Reference(s)**: OGC 04-094, 7.7, p. 23 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc47.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception )
-----------------------------------------------| -------------------------------------------------------------------------
 [ows:Exception] | /ows:Exception

