# BasicGetFeature-POST-tc9

**Purpose**: The @typeName attribute must identify a list of known feature types. Aliases may be declared for use in a filter expression

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
 
  ```
<?xml version="1.0" encoding="UTF-8"?>
<wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" service="WFS" version="1.1.0">
<wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
</wfs:GetFeature>
  ```

**Conditions**

* Response to first request complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response to first request  contains [wfs:FeatureCollection]



**Reference(s)**:  OGC 04-094, 9.2, p.35 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc13.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

