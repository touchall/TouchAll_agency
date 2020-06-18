package allpointech.agency.network.http.resource;

import android.os.Bundle;
import android.util.Base64;

import com.tuna.utils.SLog;

import org.json.JSONObject;

import java.util.ArrayList;

import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.exception.ParsingException;

public abstract class BaseHttpResource {
    public int mErrorCode = 0;
    public String mErrorMsg = null;
    public String mErrorTitle = null;
    public String mUrl;
    public Object mTag;
    //	protected ArrayList<NameValuePair> mParams = new ArrayList<NameValuePair>();
    public JSONObject mParams = new JSONObject();
    public Bundle mResponseData = new Bundle();
    private String type = HttpInfo.POST;
    private JSONObject parseData;
    private ArrayList<MultiPartData> multi_part_data_list = new ArrayList<MultiPartData>();
    private ArrayList<String> get_url_list = new ArrayList<>();
    private String mToken;
    private String mCertification;

    protected abstract void parsor(JSONObject response) throws Exception;
//	public abstract void setParameter(Bundle param);

    public abstract String makeURL() throws Exception;

    public abstract String makeType() throws Exception;

    public abstract String getTargetName();

    public String getURL() {
        String retVal = null;
        try {
            setType(makeType());
            retVal = makeURL();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SLog.LogD("" + retVal);
        return retVal;
    }

    public void parsorRes(JSONObject response) throws ParsingException {
        try {
            parsor(response);
        } catch (Exception e) {
            ParsingException ex = new ParsingException(e);
            throw ex;
        }
    }

    public String B64decode(String B64Data) {
        String encodecredentials = new String(Base64.decode(B64Data, 0));
        return encodecredentials;
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public Object getTag() {
        return mTag;
    }

    public JSONObject getHttpResponseData() {
        // TODO Auto-generated method stub
        return mParams;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getParseData() {
        return parseData;
    }

    protected void setParseData(JSONObject parseData) {
        this.parseData = parseData;
    }

    public void setMultipartData(int type, String name, String value) {
        multi_part_data_list.add(new MultiPartData(type, name, value));
    }

    public void setGetUrlData(String value) {
        get_url_list.add(value);
    }

    public ArrayList<MultiPartData> getMultipartData() {
        return this.multi_part_data_list;
    }

    public ArrayList<String> getGetUrlData() {
        return this.get_url_list;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    public String getToken() { return this.mToken; }

    public void setCertification(String certification) {
        this.mCertification = certification;
    }

    public String getCertification() { return this.mCertification; }
}
