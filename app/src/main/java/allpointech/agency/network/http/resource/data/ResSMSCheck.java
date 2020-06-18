package allpointech.agency.network.http.resource.data;


import com.tuna.utils.SLog;

import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;
import allpointech.agency.network.http.resource.ResourceList;

public class ResSMSCheck extends ResBaseHttp {

    String reg_code;

    public ResSMSCheck(String regCode) {
        super();
        reg_code = regCode;
    }

    @Override
    public String makeURL() throws Exception {
        //return super.makeURL();

        //String url = HttpInfo.HTTP_HOST + ResourceList.API_MAP.get(getClass().getSimpleName()) + "{" + reg_code + "}";
        String url = HttpInfo.HTTP_HOST + ResourceList.API_MAP.get(getClass().getSimpleName()) + reg_code;
        SLog.LogD("" + url);
        return url;

    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
//        public static final String tohpno = "tohpno";
//        public static final String regcode = "regcode";
        public static final String mobile = "mobile";
        public static final String smscode = "smscode";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String result = "result";
        public static final String message = "message";
    }

    // request
    public void setParameter(String... params) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.smscode, params[1]);
    }

}
