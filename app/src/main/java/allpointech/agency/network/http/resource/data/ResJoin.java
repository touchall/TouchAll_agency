package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResJoin extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.JSON;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
//        public static final String member_name = "member_name";
//        public static final String member_birth_day = "member_birth_day";
//        public static final String member_phonenumber = "member_phonenumber";
//        public static final String member_pwd = "member_pwd";
//        public static final String member_email = "member_email";
////        public static final String member_point_type = "member_point_type";
////        public static final String member_bank_code = "member_bank_code";
////        public static final String member_bank_account = "member_bank_account";
//        public static final String member_sex = "member_sex";
//        public static final String member_addr_state = "member_addr_state";
//        public static final String member_addr_city = "member_addr_city";
//        public static final String gcm_key = "gcm_key";
//        public static final String device_os = "device_os";

        public static final String mobile = "mobile";
        public static final String pw = "pw";
        public static final String name = "name";
        public static final String email = "email";
        public static final String birth = "birth";
        public static final String area = "area";
        public static final String gender = "gender";
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
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_name, params[0]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_birth_day, params[1]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_phonenumber, params[2]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_pwd, params[3]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_email, params[4]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.member_point_type, params[5]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.member_bank_code, params[6]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.member_bank_account, params[7]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_sex, params[5]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_addr_state, params[6]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.member_addr_city, params[7]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.gcm_key, params[8]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.device_os, params[9]);


        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.pw, params[1]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.name, params[2]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.email, params[3]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.birth, params[4]);
        if (params.length > 5) {
            setMultipartData(MultiPartData.FORM, KEY_REQ.area, params[5]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.gender, params[6]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.fcm, params[7]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.device, params[8]);
        }
    }
}
