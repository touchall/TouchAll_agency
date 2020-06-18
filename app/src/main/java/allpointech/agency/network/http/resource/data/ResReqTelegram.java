package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResReqTelegram extends ResBaseHttp {
//
//    @Override
//    public String makeType() throws Exception {
//        return HttpInfo.GET_PARAM;
//    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String mobile = "mobile";
        public static final String telegram = "telegram";
        public static final String rps = "rps";

    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
    }

    // request
    public void setParameterMobile(String mobile) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, mobile);
    }
    public void setParameterTelegram(String telegram) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.telegram, telegram);
    }
    public void setParameterRps(String rps) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.rps, rps);
    }
}
