package allpointech.agency.network.gcm;

import android.content.Context;

public class GcmPreferenceUtil extends BasePreferenceUtil {
	
	private static GcmPreferenceUtil _instance = null;
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	public static synchronized GcmPreferenceUtil instance(Context $context) {
		if (_instance == null)
			_instance = new GcmPreferenceUtil($context);
		return _instance;
	}

	protected GcmPreferenceUtil(Context $context) {
		super($context);
	}

	public void putRedId(String $regId) {
		put(PROPERTY_REG_ID, $regId);
	}
 
	public String regId() {
		return get(PROPERTY_REG_ID);
	}

	public void putAppVersion(int $appVersion) {
		put(PROPERTY_APP_VERSION, $appVersion);
	}
 
	public int appVersion() {
		return get(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	}
	
}
