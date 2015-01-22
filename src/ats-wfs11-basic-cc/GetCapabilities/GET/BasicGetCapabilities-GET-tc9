# BasicGetCapabilities-GET-tc9

**Purpose**: A response message containing an entity body must contain a Content-Type entity header field that correctly indicates the media type of the message body.

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&VERSION=1.1.0&SERVICE=WFS

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/wfs/1.1.0/wfs.xsd for each the test steps
* Response contains [wfs:WFS_Capabilities](#wfs:WFS_Capabilities) for each the test steps
* Response header contains response/headers/header[@name='Content-Type']
* Response header contenttype value is of type 'text/xml' or 'application/xml'


**Reference(s)**: OGC 05-008c1, 11.7, p.61

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc11.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to wfs:WFS_Capabilities)
-----------------------------------------------| -------------------------------------------------------------------------
wfs:WFS_Capabilities <a name="wfs:WFS_Capabilities"></a>   | /wfs:WFS_Capabilities

