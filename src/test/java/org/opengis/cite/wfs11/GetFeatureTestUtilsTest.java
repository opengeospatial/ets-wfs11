package org.opengis.cite.wfs11;

import org.junit.Test;

public class GetFeatureTestUtilsTest {

	@Test
	public void testTest() throws Exception {
		String wfsCapabilities = "http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?request=GetCapabilities&service=WFS";
		String featureTypeAndPropertyName = GetFeatureTestUtils
				.findFeatureTypeAndPropertyName(wfsCapabilities);

		System.out.println(featureTypeAndPropertyName);
	}

}