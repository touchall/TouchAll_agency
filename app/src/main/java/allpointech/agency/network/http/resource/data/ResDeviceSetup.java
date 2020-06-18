package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResDeviceSetup extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.PUT;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
//        public static final String setup_date = "setup_date";
//        public static final String setup_time = "setup_time";
//        public static final String setup_type = "setup_type";
//        public static final String setup_device_serial = "setup_device_serial";
//        public static final String setup_group_type = "setup_group_type";
//        public static final String setup_group_code = "setup_group_code";
//        public static final String setup_bonus_kinds = "setup_bonus_kinds";
//        public static final String setup_bonus_rate = "setup_bonus_rate";
//        public static final String setup_stemp_rate = "setup_stemp_rate";
//        public static final String setup_game_rate = "setup_game_rate";
//        public static final String setup_regular_rate = "setup_regular_rate";
//        public static final String setup_pay_cost_rate = "setup_pay_cost_rate";
//        public static final String setup_pay_type_rate = "setup_pay_type_rate";
//        public static final String setup_time_rate = "setup_time_rate";
//        public static final String setup_send_type = "setup_send_type";
//        public static final String setup_wait_time = "setup_wait_time";
//        public static final String setup_phonenumber = "setup_phonenumber";
        public static final String serial = "serial";
        public static final String earn = "earn";
        public static final String kinds = "kinds";
        public static final String point = "point";
        public static final String stamp = "stamp";
        public static final String rps = "rps";
        public static final String transfer = "transfer";
        public static final String applyDow = "applyDow";
        public static final String advancement = "advancement";
        public static final String amount = "amount";
        public static final String amountRatio = "amountRatio";
        public static final String method = "method";
        public static final String methodRatio = "methodRatio";
        public static final String time = "time";

    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
//        public static final String franchisee = "franchisee";
//        public static final String insert_point = "insert_point";

//        public class KEY_FRANCHISEE {
//            public static final String uncode = "uncode";
//            public static final String unname = "unname";
//        }
//
//        public class KEY_INSERTPOINT {
//            public static final String mbcode = "mbcode";
//            public static final String mbseq = "mbseq";
//            public static final String mbocpt = "mbocpt";  // 적립 포인트
//        }
    }

    // request
    public void setParameter(String... params) {

        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.earn, params[1]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.kinds, params[2]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.point, params[3]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.stamp, params[4]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.rps, params[5]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.transfer, params[6]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.applyDow, params[7]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.advancement, params[8]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.amount, params[9]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.amountRatio, params[10]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.method, params[11]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.methodRatio, params[12]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.time, params[13]);

//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_date, params[0]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_time, params[1]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_type, params[2]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_device_serial, params[3]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_group_type, params[4]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_group_code, params[5]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_bonus_kinds, params[6]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_bonus_rate, params[7]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_stemp_rate, params[8]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_game_rate, params[9]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_regular_rate, params[10]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_pay_cost_rate, params[11]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_pay_type_rate, params[12]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_time_rate, params[13]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_send_type, params[14]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_wait_time, params[15]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.setup_phonenumber, params[16]);
    }
}
