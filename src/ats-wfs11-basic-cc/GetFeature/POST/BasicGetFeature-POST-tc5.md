# BasicGetFeature-POST-tc5

**Purpose**: The response entity must include the @xsi:schemaLocation attribute to identify the set of GML application schemas against which the matching features are valid

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
 
  ```
   <?xml version="1.0" encoding="UTF-8"?>
<wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0" service="WFS">
<wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" featureVersion="ALL" srsName="urn:ogc:def:crs:EPSG::4326"/>
</wfs:GetFeature>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:FeatureCollection/@xsi:schemaLocation]



**Reference(s)**: OGC 04-094, 9.3.1, p.39 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc6.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection/@xsi:schemaLocation] | /wfs:FeatureCollection/@xsi:schemaLocation

