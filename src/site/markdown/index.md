# Overview

This test suite is based on the following OGC specifications:

  * _Web Feature Service Implementation Specification_, version 1.1.0 [OGC 04-094](https://portal.opengeospatial.org/files/?artifact_id=8339)
  * _Filter Encoding Implementation Specification_, version 1.1.0 [OGC 04-095](http://portal.opengeospatial.org/files/?artifact_id=8340)
  * _OpenGIS Web Services Common Specification_, version 1.0.0 [OGC 05-008c1](https://portal.opengeospatial.org/files/?artifact_id=8798)
  * _Geography Markup Language (GML) -- Simple features profile_, version 1.0.0 [OGC 06-049r1](http://portal.opengeospatial.org/files/?artifact_id=15201)

## What is tested

Four conformance classes can be tested. 

  * **Basic conformance class (Requires no test data)**
    * GetCapabilities, GET and POST methods
    * DescribeFeature, GET and POST methods
    * GetFeature, GET and POST methods
  * **Transaction conformance class (Requires test data)**
    * Transaction, POST method
  * **Locking conformance class (Requires test data)**
    * LockFeature, POST method
    * GetFeatureWithLock, POST method
  * **XLink conformance class (Requires test data)**

The Basic conformane class  is the minimum class to get OGC certification and the test is data agnostic. WFS-Transaction, WFS-Locking and WFS-XLink requires test data that needs to be added before executing the test. The Locking conformance class expects that the feature types **sf:PrimitiveGeoFeature**, **sf:AggregateGeoFeature** and **sf:EntitéGénérique** are lockable. It is **not** checked if locking is enabled per feature type or not. 

The test, including the test data, is based on simple features profile (i.e. levels SF-0 and SF-1). GML complex properties conforming to SF-2 are not tested.

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
