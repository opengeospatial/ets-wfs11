## Release Notes WFS 1.1.0

### 1.36 (2023-07-28)
- [#103](https://github.com/opengeospatial/ets-wfs11/issues/103): Version 1.35 produces error during start up on Beta environment

### 1.35 (2023-05-31)
- [#100](https://github.com/opengeospatial/ets-wfs11/pull/100): remove validating parser for bad version (VeERSION) test - wfs-1.1.0-…Basic-GetFeature-tc4
- [#98](https://github.com/opengeospatial/ets-wfs11/issues/98): Update dependency ets-gml32 to latest version
- [#101](https://github.com/opengeospatial/ets-wfs11/pull/101): Upgrade TEAM Engine dependencies to v5.6.1

### 1.34 (2022-01-26)
- [#96](https://github.com/opengeospatial/ets-wfs11/issues/96): wfs:wfs-1.1.0-Basic-GetFeature-tc4 -- checks look incorrect (wrong value)
- [#99](https://github.com/opengeospatial/ets-wfs11/pull/99): Set TEAM Engine dependencies to v5.5.2

### 1.33 (2021-10-28)
- [#84](https://github.com/opengeospatial/ets-wfs11/issues/84): Enable test execution using a RESTful API
- [#93](https://github.com/opengeospatial/ets-wfs11/issues/93): Enable Docker support
- [#92](https://github.com/opengeospatial/ets-wfs11/pull/92): Bump junit from 4.12 to 4.13.1
- [#83](https://github.com/opengeospatial/ets-wfs11/pull/83): Fixed Fortify Issues for DISASTIG
- [#82](https://github.com/opengeospatial/ets-wfs11/issues/82): Create SoapUI tests and integrate them into Maven and Jenkinsfile

### 1.32 (2018-05-16)
- Fix [#68](https://github.com/opengeospatial/ets-wfs11/issues/68): Add conformance class configuration into the wfs11 test
- Fix [#78](https://github.com/opengeospatial/ets-wfs11/issues/78): wfs:wfs-1.1.0-Basic-GetCapabilities-tc7 fails with InvocationTargetException
- Fix [#73](https://github.com/opengeospatial/ets-wfs11/issues/73): wfs:wfs-1.1.0-Basic-GetFeature-tc5 fails with missing testdata if DCP Urls has a qeury string but does not end with a '&'

### 1.31 (2017-10-27)
- Fix [#74](https://github.com/opengeospatial/ets-wfs11/issues/74): Test wfs-1.1.0-Basic-GetFeature-tc5 fails as property value is not encoded in filter

### 1.30 (2017-08-22)
- Fix [#69](https://github.com/opengeospatial/ets-wfs11/issues/69): WFS 1.1.0 DataSet
- Fix [#18](https://github.com/opengeospatial/ets-wfs11/issues/18): LockFeature Tests are issued even though the Lock attribute is not in FeatureTypeList

### 1.29 (2016-11-03)
- Fix [#66](https://github.com/opengeospatial/ets-wfs11/issues/66): XPath-issue with ows:Exception

### 1.28 (2016-07-06)
- Fix [#64](https://github.com/opengeospatial/ets-wfs11/issues/64): Replace Basic-WFS CC with Core test suite
- Fix [#60](https://github.com/opengeospatial/ets-wfs11/issues/60): DescribeFeatureType GET tests fail as Content-Type is evaluated too strict

### 1.27 (2016-06-08)
- Fix [#61](https://github.com/opengeospatial/ets-wfs11/issues/61): Update Test Notes - Overview and Structure of the test
- Fix [#42](https://github.com/opengeospatial/ets-wfs11/issues/42): Complete WFS 1.1.0 Basic Test
- Fix [#59](https://github.com/opengeospatial/ets-wfs11/issues/59): Confirming that features were deleted (Transaction-tc13.1)

### 1.26 (2016-04-29)
- Seperated locking from transaction CC (https://github.com/opengeospatial/ets-wfs11/issues/18)

### 1.25 (2015-10-27)
- wfs:wfs-1.1.0-Basic-GetCapabilities-tc12 fails if server supports WFS 2.0 (https://github.com/opengeospatial/ets-wfs11/issues/45)
- Move to bottom information about selecting the basic test and mark it with warning in the main ctl (https://github.com/opengeospatial/ets-wfs11/issues/47)

### 1.24 (2015-07-30)
- Update pom.xml to build with Maven 2 and minor edits
- Update of basic profile

### 1.23 (2015-06-03)
- New test basic profile [#1](https://github.com/opengeospatial/ets-wfs11/issues/1)

### 1.22 (2015-03-27)

- Fix [#24](https://github.com/opengeospatial/ets-wfs11/issues/24) - Update revision scheme
- Fix [#12](https://github.com/opengeospatial/ets-wfs11/issues/12) - wfs:wfs-1.1.0-Transaction-tc13.1

### r21 (2014-11-04)

- Fix issue [#15](https://github.com/opengeospatial/ets-wfs11/pull/15) - Replaced assertion with contains 
- Fix issue [#11](https://github.com/opengeospatial/ets-wfs11/issues/11)- Wrong filter for wfs:wfs-1.1.0-Transaction-tc7.1, with pull request:[#14](https://github.com/opengeospatial/ets-wfs11/pull/14) 

### r20 (2014-09-08)

- Fix issue [#5](https://github.com/opengeospatial/ets-wfs11/issues/5)and [#6](https://github.com/opengeospatial/ets-wfs11/issues/6) with pull [#7](https://github.com/opengeospatial/ets-wfs11/pull/7) - Transaction tc 11.1 and 13.1 broken because of missing parameter declaration

### r19 (2014-08-14)

- Migrate source code to GitHub.
- Adopt Apache Maven as the build tool and modify directory layout accordingly.
- Change license to Apache License, Version 2.0
- Fix issue [#3: Transaction tc 4.1 crashes because of wrong syntax](https://github.com/opengeospatial/ets-wfs11/issues/3)

### r18 (2014-06-10)

- Update transactional tcs dont to use GET KVP.
- Fix issue 876 trailing zeros cut.
- Fix 405.1 and 405.2 compare method.

### r17 (2014-02-07)

- Accepted 6215 commit - Resolving CITE-898 issue (BBOX in WSG84).
- Fixed issue CITE 914, 916 - XSLT compile issue due to a text comment.
- Added a note in the welcome main form to the reference implementation page.

### r16 (2013-12-17)

- Resolved 855.
- Resolved 875.

### r15 (2013-12-10)

- CITE-915 - check if idgen is supported for transactions.
- CITE 898 - Reversed fix for 898 until WG provides better guidance.

### r14 (2013-12-03)

- Resolved CITE-898 (BBOX in WGS84).

### r13 (2013-08-36)

- Solved issue 792.

### r12 (2013-03-22)

- Added namespace parameter to GetFeature-extra-param, GetFeature-result-type-results, fixing issue 772.
- Repaired filter expressions following issue 773.
- Added test data archives (referenced from overview doc); removed broken spec link.

### r11 (2013-02-08)

- Updated config.xml for TEAM-Engine v4.
- Added prefix to default namespace declaration (xmlns:xsd=&quot;http://www.w3.org/2001/XMLSchema&quot;) in functions.xml.
- Fixed classpath references for Schematron schemas.
