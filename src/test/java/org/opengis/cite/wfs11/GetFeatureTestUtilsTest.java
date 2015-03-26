package org.opengis.cite.wfs11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Test;

public class GetFeatureTestUtilsTest {

	@Test
	public void testTest() throws Exception {
		String wfsCapabilities = readCapabilities();
//		GetFeatureTestUtils.findFeatureTypeAndPropertyName(wfsCapabilities);
	}

	private String readCapabilities() throws Exception {
		// DocumentBuilderFactory factory =
		// DocumentBuilderFactory.newInstance();
		//
		// factory.setNamespaceAware(true);
		// DocumentBuilder builder = factory.newDocumentBuilder();

		InputStream stream = new URL(
				"http://cite.lat-lon.de/deegree-webservices-3.3.6/services/wfs110?request=GetCapabilities&service=WFS")
				.openStream();
		return getStringFromInputStream(stream);
		// return builder.parse(stream);
	}

	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
}