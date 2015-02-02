# BasicGetFeature-POST-tc4

**Purpose**: The response entity must be schema valid and include any optional feature properties requested by the client. The document element must be a wfs:FeatureCollection

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
 
  ```
   <?xml version="1.0" encoding="UTF-8"?>
<foo:GetFeature xmlns:foo="http://www.opengis.net/wfs" version="1.1.0" service="WFS">
<foo:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
</foo:GetFeature>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:FeatureCollection]



**Reference(s)**: OGC 04-094, 9.2, p.37 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc5.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

