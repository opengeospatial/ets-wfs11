package org.opengis.cite.wfs11;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class GetFeatureTestConditionsIT {

	@Test
	public void testCheckFeatureTypesDescribedInSchema_SingleFeatureType()
			throws Exception {
		boolean featureTypesDescribedInSchema = GetFeatureTestConditions
				.checkFeatureTypesDescribedInSchema(
						capabilitiesSingleFeatureType(),
						describeFeatureTypeResponse());

		assertThat(featureTypesDescribedInSchema, is(true));
	}

	@Test
	public void testCheckFeatureTypesDescribedInSchema_FeatureTypeFromImportedSchema()
			throws Exception {
		boolean featureTypesDescribedInSchema = GetFeatureTestConditions
				.checkFeatureTypesDescribedInSchema(
						capabilitiesFeatureTypeFromImportedSchema(),
						describeFeatureTypeResponse());

		assertThat(featureTypesDescribedInSchema, is(true));
	}

	@Test
	public void testCheckFeatureTypesDescribedInSchema_UndefinedFeatureType()
			throws Exception {
		boolean featureTypesDescribedInSchema = GetFeatureTestConditions
				.checkFeatureTypesDescribedInSchema(
						capabilitiesSingleFeatureTypeUndefined(),
						describeFeatureTypeResponse());

		assertThat(featureTypesDescribedInSchema, is(false));
	}

	@Test
	public void testCheckFeatureTypesDescribedInSchema_FeatureTypeWithWrongNamespace()
			throws Exception {
		boolean featureTypesDescribedInSchema = GetFeatureTestConditions
				.checkFeatureTypesDescribedInSchema(
						capabilitiesSingleFeatureTypeWrongNamespace(),
						describeFeatureTypeResponse());

		assertThat(featureTypesDescribedInSchema, is(false));
	}

	private Node capabilitiesSingleFeatureType() throws Exception {
		return asNode("capabilitiesSingleFeatureType.xml");
	}

	private Node capabilitiesFeatureTypeFromImportedSchema() throws Exception {
		return asNode("capabilitiesFeatureTypeFromImportedSchema.xml");
	}

	private Node capabilitiesSingleFeatureTypeUndefined() throws Exception {
		return asNode("capabilitiesSingleFeatureTypeUndefined.xml");
	}

	private Node capabilitiesSingleFeatureTypeWrongNamespace() throws Exception {
		return asNode("capabilitiesSingleFeatureTypeWrongNamespace.xml");
	}

	private Node describeFeatureTypeResponse() throws Exception {
		return asNode("describeFeatureType.xml");
	}

	private Node asNode(String resource) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = GetFeatureTestConditionsIT.class
				.getResourceAsStream(resource);
		try {
			Document wfsCapabilities = builder.parse(is);
			return wfsCapabilities.getDocumentElement();
		} finally {
			is.close();
		}
	}

}