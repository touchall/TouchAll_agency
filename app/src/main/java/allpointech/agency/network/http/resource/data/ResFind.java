package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResFind extends ResBaseHttp {
    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String member_email = "member_email";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
    }

    // request
    public void setParameter(String... params) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.member_email, params[0]);
    }
}
