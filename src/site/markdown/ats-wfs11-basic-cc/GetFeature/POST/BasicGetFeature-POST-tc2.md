# BasicGetFeature-POST-tc2

**Purpose**: If @resultType='hits', then only the size of the resulting feature collection and a timestamp is returned (i.e. @numberOfFeatures, @timeStamp). Only instances of requested feature types are included in the total.

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
 
  ```
    <?xml version="1.0" encoding="UTF-8"?>
    <wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0" service="WFS" resultType="hits">
      <wfs:Query typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
    </wfs:GetFeature>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:FeatureCollection/@timeStamp && wfs:FeatureCollection/@numberOfFeatures]
* Response contains no [wfs:FeatureCollection/gml:featureMember && wfs:FeatureCollection/gml:featureMembers/*]



**Reference(s)**: OGC 04-094, 9.3, p.38 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc2.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:FeatureCollection )
-----------------------------------------------| -------------------------------------------------------------------------
 [wfs:FeatureCollection] | /wfs:FeatureCollection

