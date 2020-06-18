package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResUsePoint extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        //return super.makeType();
        return HttpInfo.PUT;
        //return HttpInfo.GET_PARAM;
        //return HttpInfo.POST;
        //return  HttpInfo.OPTIONS;
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String mobile = "mobile";
        public static final String certification = "certification";
//        public static final String serial = "serial";
//        public static final String kind = "kind";
//        public static final String amount = "amount";
//        public static final String type = "type";
//        public static final String id = "id";
//        public static final String kind = "kind";
//        public static final String amount = "amount";
//        public static final String approval = "approval";
//        public static final String mobile = "mobile";
//        public static final String serial = "serial";
//        public static final String id = "id";
//        public static final String approval = "approval";
//        public static final String type = "type";
//        public static final String point = "point";
//        public static final String amount = "amount";
//        public static final String payType = "payType";
//        public static final String payDate = "payDate";
//        public static final String vat = "vat";
//        public static final String supply = "supply";
//        public static final String creditCorp = "creditCorp";
//        public static final String creditNum = "creditNum";
//        public static final String rps = "rps";

    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String result = "result";
        public static final String message = "message";

        public static final String receipt = "receipt";
        public static final String mall = "mall";
        public static final String point = "point";
        public static final String duplicate = "duplicate";

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
        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.certification, params[1]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, params[1]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.kind, params[2]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.amount, params[3]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.type, params[2]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.id, params[3]);
//
//        setMultipartData(MultiPartData.FORM, KEY_REQ.approval, params[6]);
    }
}
