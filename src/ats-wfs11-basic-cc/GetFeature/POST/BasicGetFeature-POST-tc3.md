# BasicGetFeature-POST-tc3

**Purpose**: If @maxFeatures > 0, then the number of features included in the response shall not exceed the specified number. Members of a feature collection do NOT count toward the total
**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
 
  ```
   <?xml version="1.0" encoding="UTF-8"?>
<wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0" service="WFS" maxFeatures="2">
<wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
</wfs:GetFeature>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains not more than [gml:featureMembers] elements than specified within [wfs:GetFeature/@maxfeatures]



**Reference(s)**: OGC 04-094, 9.3, p.38 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc2.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection
  [gml:featureMembers] | /wfs:FeatureCollection/gml:featureMembers

