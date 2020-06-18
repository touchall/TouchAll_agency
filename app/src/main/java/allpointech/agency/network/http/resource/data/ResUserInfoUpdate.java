package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResUserInfoUpdate extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.PUT;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String mobile = "mobile";
        public static final String pw = "pw";
        public static final String name = "name";
        public static final String email = "email";
        public static final String birth = "birth";
        public static final String area = "area";
        public static final String gender = "gender";
        public static final String bank = "bank";
        public static final String account = "account";
        public static final String holder = "holder";
        public static final String fcm = "fcm";
        public static final String device = "device";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
    }

    // request
    public void setParameter(String... params) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.pw, params[1]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.name, params[2]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.email, params[3]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.birth, params[4]);
        //if (params.length > 5)
        {
            setMultipartData(MultiPartData.FORM, KEY_REQ.area, params[5]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.gender, params[6]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.bank, params[7]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.account, params[8]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.holder, params[9]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.fcm, params[10]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.device, params[11]);
        }
    }
}
