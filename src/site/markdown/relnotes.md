## Release Notes WFS 1.1.0

### 1.21 (2015-03-27)

- Fix [#24](https://github.com/opengeospatial/ets-wfs11/issues/24) - Update revision scheme
- Fix [#12](https://github.com/opengeospatial/ets-wfs11/issues/12) - wfs:wfs-1.1.0-Transaction-tc13.1

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