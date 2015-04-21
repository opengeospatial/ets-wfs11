# BasicGetCapabilities-GET-tc13

**Purpose**:  In the event that a GetCapabilities request cannot be processed for any reason, the response entity shall include an exception report

**Test method**

Execute the following Test Steps:

* Send the following GetCapabilities request by GET: wfs.GetCapabilities.get.url?REQUEST=GetCapabilities&version=1.1.0

**Conditions**

* Response complies to xml schema: http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd
* Response contains [ows:Exception/@locator='service'](#ows:Exception/@locator='service') 

**Reference(s)**: OGC 05-008c1, 7.4.1, p.19

**Test type**: Automated

**Notes**

former ETS tc: wfs:wfs-1.1.0-Basic-GetCapabilities-tc16.1


## Contextual XPath references

The namespace prefixes used as described in [README.md](./README.md#namespaces).

Abbreviation                                   |  XPath expression (relative to ows:Exception)
-----------------------------------------------| -------------------------------------------------------------------------
ows:Exception/@locator='service' <a name="ows:Exception/@locator='service'"></a>   | /ows:Exception/@locator='service'
