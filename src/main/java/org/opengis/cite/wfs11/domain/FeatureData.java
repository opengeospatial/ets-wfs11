package org.opengis.cite.wfs11.domain;

import javax.xml.namespace.QName;


public class FeatureData {
	private final QName featureType;
	private final QName propName;
	private final String data;

	public FeatureData(QName featureType, QName propName, String data) {
		this.featureType = featureType;
		this.propName = propName;
		this.data = data;
	}

	public QName getFeatureType() {
		return featureType;
	}

	public QName getPropName() {
		return propName;
	}

	public String getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "Data [featureType=" + featureType + ", propName=" + propName
				+ ", data=" + data + "]";
	}
	
}