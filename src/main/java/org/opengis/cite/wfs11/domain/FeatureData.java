package org.opengis.cite.wfs11.domain;

import javax.xml.namespace.QName;

/**
 * <p>
 * FeatureData class.
 * </p>
 *
 */
public class FeatureData {

	private final QName featureType;

	private final QName propName;

	private final String data;

	/**
	 * <p>
	 * Constructor for FeatureData.
	 * </p>
	 * @param featureType a {@link javax.xml.namespace.QName} object
	 * @param propName a {@link javax.xml.namespace.QName} object
	 * @param data a {@link java.lang.String} object
	 */
	public FeatureData(QName featureType, QName propName, String data) {
		this.featureType = featureType;
		this.propName = propName;
		this.data = data;
	}

	/**
	 * <p>
	 * Getter for the field <code>featureType</code>.
	 * </p>
	 * @return a {@link javax.xml.namespace.QName} object
	 */
	public QName getFeatureType() {
		return featureType;
	}

	/**
	 * <p>
	 * Getter for the field <code>propName</code>.
	 * </p>
	 * @return a {@link javax.xml.namespace.QName} object
	 */
	public QName getPropName() {
		return propName;
	}

	/**
	 * <p>
	 * Getter for the field <code>data</code>.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getData() {
		return data;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "Data [featureType=" + featureType + ", propName=" + propName + ", data=" + data + "]";
	}

}
