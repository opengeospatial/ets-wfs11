package org.opengis.cite.wfs11;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class GetFeatureTestUtilsIT {

	@Ignore
	@Test
	public void testTest() throws Exception {
		String wfsCapabilities = "http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?request=GetCapabilities&service=WFS";
		String featureTypeAndPropertyName = GetFeatureTestUtils
				.findFeatureTypeAndPropertyName(wfsCapabilities);

		assertThat(
				featureTypeAndPropertyName,
				is("http://cite.opengeospatial.org/gmlsf|AggregateGeoFeature|http://cite.opengeospatial.org/gmlsf|doubleProperty|2012.78"));
	}

}