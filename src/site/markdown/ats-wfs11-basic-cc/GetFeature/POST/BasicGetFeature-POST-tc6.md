# BasicGetFeature-POST-tc6

**Purpose**: If a request is unrecognizable or the service cannot process the request, a valid exception report must be returned

**Test method**

Execute the following Test Steps:

* Send the following GetFeature requests by POST:
 
  ```
    <?xml version="1.0" encoding="UTF-8"?>
    <wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" xmlns:ogc="http://www.opengis.net/ogc" service="WFS" version="1.1.0">
      <wfs:Query typeName="Asdf" srsName="urn:ogc:def:crs:EPSG::4326" />
    </wfs:GetFeature>
  ```
  
  ```
    <?xml version="1.0" encoding="UTF-8"?>
    <wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" xmlns:ogc="http://www.opengis.net/ogc" service="WFS" version="1.1.0">
      <wfs:Query typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326">
        <wfs:PropertyName>asdfProperty</wfs:PropertyName>
      </wfs:Query>
    </wfs:GetFeature>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd
* Response contains [ows:Exception]



**Reference(s)**: OGC 04-094, 7.7, p. 23 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc9.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception )
-----------------------------------------------| -------------------------------------------------------------------------
 [ows:Exception] | /ows:Exception

