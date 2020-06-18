package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResSMSReq extends ResBaseHttp {
//
//    @Override
//    public String makeType() throws Exception {
//        //return super.makeType();
//        return HttpInfo.JSON;
//    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String mobile = "mobile";
        public static final String regcode = "regcode";
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
        //setMultipartData(MultiPartData.FORM, KEY_REQ.regcode, "123456");
    }
}
