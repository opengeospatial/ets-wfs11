package org.opengis.cite.wfs11;

/**
 * <p>
 * WFS11 class.
 * </p>
 *
 */
public class WFS11 {

	/** Reference to the WFS11 capabilities document (URI). */
	public static final String CAPABILITIES_URL = "capabilities-url";

	/** Implements the Transaction request */
	public static final String WFS_TRANSACTION = "wfs-transaction";

	/** Implements optional LockFeature and GetFeatureWithLock */
	public static final String WFS_LOCKING = "wfs-locking";

	/**
	 * Implements the GetGmlObject request and supports (local) XLink processing in
	 * GetFeature requests.
	 */
	public static final String WFS_XLINK = "wfs-xlink";

	/**
	 * The profile indicates the two levels of the GMLSF(GML Simple Features): SF-0 :
	 * Level 0 (only simple non-spatial property types; Curve and Surface geometries are
	 * excluded) SF-1 : Level 1 (complex non-spatial property types, plus Curve and
	 * Surface geometries)
	 */
	public static final String PROFILE = "profile";

}
