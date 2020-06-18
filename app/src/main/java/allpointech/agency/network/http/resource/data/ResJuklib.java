package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResJuklib extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        //return super.makeType();
        return HttpInfo.PUT;
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
//        public static final String juklib_date = "juklib_date";
//        public static final String juklib_time = "juklib_time";
//        public static final String juklib_bonus_type = "juklib_bonus_type";
//        public static final String juklib_device_serial = "juklib_device_serial";
////        public static final String juklib_business_code = "juklib_business_code";
//        public static final String juklib_phone_number = "juklib_phone_number";
//        public static final String juklib_birth_day = "juklib_birth_day";
//        public static final String juklib_pay_money = "juklib_pay_money";
//        public static final String juklib_pay_type = "juklib_pay_type";
//        public static final String juklib_pay_serial = "juklib_pay_serial";
//        public static final String juklib_point = "juklib_point";
//        public static final String juklib_stemp = "juklib_stemp";
//        public static final String juklib_game = "juklib_game";
//        public static final String juklib_result = "juklib_result";
        public static final String mobile = "mobile";
        public static final String serial = "serial";
        public static final String id = "id";
        public static final String approval = "approval";
        public static final String type = "type";
        public static final String point = "point";
        public static final String amount = "amount";
        public static final String payType = "payType";
        public static final String payDate = "payDate";
        public static final String vat = "vat";
        public static final String supply = "supply";
        public static final String creditCorp = "creditCorp";
        public static final String creditNum = "creditNum";
        public static final String rps = "rps";

    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
        public static final String receipt = "receipt";
        public static final String mall = "mall";
        public static final String rps = "rps";
        public static final String point = "point";

        public class RECEIPT_RES {
            public static final String approval = "approval";
            public static final String type = "type";
            public static final String amount = "amount";
            public static final String supply = "supply";
            public static final String vat = "vat";
            public static final String payType = "payType";
            public static final String payDate = "payDate";
            public static final String payTypeName = "payTypeName";
        }

        public class MALL_RES {
            public static final String name = "name";
            public static final String earn = "earn";
            public static final String rpsUse = "rpsUse";
            public static final String ssn = "ssn";
            public static final String address = "address";
            public static final String owner = "owner";
            public static final String leaflet = "leaflet";



        }
    }

    // request
    public void setParameter(String... params) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_date, params[0]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_time, params[1]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_bonus_type, params[2]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_device_serial, params[3]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_business_code, params[4]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_phone_number, params[4]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_birth_day, params[5]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_pay_money, params[6]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_pay_type, params[7]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_pay_serial, params[8]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_point, params[9]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_stemp, params[10]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_game, params[11]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.juklib_result, params[12]);

        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, params[1]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.id, params[2]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.approval, params[3]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.type, params[4]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.point, params[5]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.amount, params[6]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.payType, params[7]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.payDate, params[8]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.vat, params[9]);
        if (params.length > 10) {
            setMultipartData(MultiPartData.FORM, KEY_REQ.supply, params[10]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.creditCorp, params[11]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.creditNum, params[12]);
            setMultipartData(MultiPartData.FORM, KEY_REQ.rps, params[13]);
        }
    }
}
