# BasicDescribeFeatureType-POST-tc3

**Purpose**:  A request with no TypeName elements shall produce a response that includes definitions of all supported feature types in the requested schema language

**Test method**

Execute the following Test Steps:

* Send the following DescribeFeatureType request by POST: wfs.DescribeFeatureType.post.url

  ```
  <wfs:DescribeFeatureType service="WFS"  xmlns:wfs="http://www.opengis.net/wfs" version="1.1.0">
  </wfs:DescribeFeatureType>
  ```
* Send following GetCapabilities reuqest by GET:  wfs.GetCapabilities.get.url?request=GetCapabilities&version=1.1.0&service=WFS
* Compare occurences of FeatureType names within both responses.

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/gml/3.1.1/base/gml.xsd
* Occurences of FeatureType names within both responses are equal. 

**Reference(s)**: OGC 04-094, 8.1, p.24

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-DescribeFeatureType-tc11.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to )
-----------------------------------------------| -------------------------------------------------------------------------
  | 

