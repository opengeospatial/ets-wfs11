package org.opengis.cite.wfs11.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.opengis.cite.iso19136.util.NamespaceBindings;

public class NamespaceBindingUtils {

	public static final String WFS_NAMESPACE = "http://www.opengis.net/wfs";

	public static final String OWS_NAMESPACE = "http://www.opengis.net/ows";

	public static final String XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";

	public static final String GML_NAMESPACE = "http://www.opengis.net/gml";

	public static class NamespaceBindingBuilder {

		private Map<String, String> prefix2namespaces = new HashMap<String, String>();

		public NamespaceBindingBuilder add(String prefix, String namespaceUrl) {
			prefix2namespaces.put(prefix, namespaceUrl);
			return this;
		}

		public NamespaceBindings build() {
			NamespaceBindings nsBindings = new NamespaceBindings();
			for (Entry<String, String> prefix2namespace : prefix2namespaces
					.entrySet()) {
				nsBindings.addNamespaceBinding(prefix2namespace.getValue(),
						prefix2namespace.getKey());
			}
			return nsBindings;
		}
	}

}