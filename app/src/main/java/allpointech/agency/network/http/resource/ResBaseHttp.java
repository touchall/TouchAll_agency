package allpointech.agency.network.http.resource;

import com.tuna.utils.SLog;

import org.json.JSONObject;

import allpointech.agency.network.http.HttpInfo;


public class ResBaseHttp extends BaseHttpResource {

	private static final String TAG = "ResourceHttp";

	//public static final String RETURNCODE		= "code";
	public static final String RETURNCODE		= "result";
	//public static final String RETURNMSG		= "code_msg";
	public static final String RETURNMSG		= "message";
	//public static final String PAGE_SIZE		= "10";

	@Override
	public String makeType() throws Exception {
		return HttpInfo.POST;
	}

    @Override
    public String getTargetName() {
        return getClass().getSimpleName();
    }

    @Override
	public String makeURL() throws Exception {
		String url = HttpInfo.HTTP_HOST + ResourceList.API_MAP.get(getClass().getSimpleName());
		SLog.LogD("" + url);
		return url;
	}

//	@Override
//	public void setParameter(Bundle param) {
//		Iterator<String> il = param.keySet().iterator();
//		while (il.hasNext()) {
//			String key = il.next();
//			try {
//				mParams.put(key, param.getString(key));
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	@Override
	public String toString() {
		return mResponseData.toString();
	}
	@Override
	protected void parsor(JSONObject response) throws Exception {

		setParseData(response);
	}

}
