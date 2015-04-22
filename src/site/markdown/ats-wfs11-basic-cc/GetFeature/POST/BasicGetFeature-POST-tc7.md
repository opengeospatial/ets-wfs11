# BasicGetFeature-POST-tc7

**Purpose**: The following attributes are required for all XML request entities: @version (default '1.1.0'); @service (default 'WFS')

**Test method**

Execute the following Test Steps:

* Send the following GetFeature request by POST:
 
  ```
    <?xml version="1.0" encoding="UTF-8"?>
    <wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0" service="WFS">
      <wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
    </wfs:GetFeature>
  ```

* Send the following GetFeature request by POST:

  ```
    <?xml version="1.0" encoding="UTF-8"?>
    <wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0">
      <wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
    </wfs:GetFeature>
  ```

* Send the following GetFeature request by POST:

  ```
    <?xml version="1.0" encoding="UTF-8"?>
    <wfs:GetFeature xmlns:wfs="http://www.opengis.net/wfs" service="WFS">
      <wfs:Query xmlns:sf="http://cite.opengeospatial.org/gmlsf" typeName="$ftname" srsName="urn:ogc:def:crs:EPSG::4326"/>
    </wfs:GetFeature>
  ```

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd
* Response contains [wfs:FeatureCollection]



**Reference(s)**: OGC 04-094, 7.8, p.24 

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetFeature-tc11.x


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception )
-----------------------------------------------| -------------------------------------------------------------------------
 [ows:Exception] | /ows:Exception

