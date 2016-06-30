# Overview

This test suite is based on the following OGC specifications:

  * _Web Feature Service Implementation Specification_, version 1.1.0 [OGC 04-094](https://portal.opengeospatial.org/files/?artifact_id=8339)
  * _Filter Encoding Implementation Specification_, version 1.1.0 [OGC 04-095](http://portal.opengeospatial.org/files/?artifact_id=8340)
  * _OpenGIS Web Services Common Specification_, version 1.0.0 [OGC 05-008c1](https://portal.opengeospatial.org/files/?artifact_id=8798)
  * _Geography Markup Language (GML) -- Simple features profile_, version 1.0.0 [OGC 06-049r1](http://portal.opengeospatial.org/files/?artifact_id=15201)

Not all of GML 3.1.1 is covered by this test suite. The test data make use of
only those elements allowed by the simple features profile at levels SF-0 and
SF-1.

## What is tested

Following conformance classes are tested:

  * **Basic conformance class (Requires no test data)**
    * GetCapabilities, GET and POST methods
    * DescribeFeature, POST and GET methods
    * GetFeature, POST and GET methods
  * **Transaction conformance class (Requires test data)**
    * Transaction, POST method
  * **Locking conformance class (Requires test data)**
    * LockFeature, POST method
    * GetFeatureWithLock, POST method
  * **XLink conformance class (Requires test data)**

WFS-Transaction, WFS-Locking and WFS-XLink requires test data, don't forget to add them! WFS-Basic is data agnostic.

The Abstract Test Suite (ATS) is available [here](abstract-test-suite.html).

## What is not tested

  * SOAP protocol bindings
  * Feature versioning
  * Spatial predicates Beyond, DWithin (broken filter schema)
  * Full GML 3.1.1 compliance (uses GMLSF 1.0 profile, Levels 0 and 1)

## Test data for Full test

The [WFS-1.1.0 test data](data-wfs-1.1.0.zip) are available for GMLSF levels 0
and 1. Loading the feature data into the datastore is a precondition for
compliance testing.

Note that GMLSF Levels 0 and 1 **DO NOT** support the use of feature
collections through WFS interfaces. The collections defined here are provided
for convenience only--they cannot themselves be treated as GML features.
