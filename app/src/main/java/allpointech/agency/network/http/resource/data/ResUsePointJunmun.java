package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResUsePointJunmun extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        //return super.makeType();
        return HttpInfo.PUT;
        //return HttpInfo.GET_PARAM;
        //return HttpInfo.POST;
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String mobile = "mobile";
        public static final String serial = "serial";
        public static final String kind = "kind";
        public static final String amount = "amount";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
//        public static final String result = "result";
//        public static final String message = "message";
//
//        public static final String receipt = "receipt";
//        public static final String mall = "mall";
//        public static final String point = "point";
//        public static final String duplicate = "duplicate";
//
//        public class RECEIPT_RES {
//            public static final String approval = "approval";
//            public static final String type = "type";
//            public static final String amount = "amount";
//            public static final String supply = "supply";
//            public static final String vat = "vat";
//            public static final String payType = "payType";
//            public static final String payDate = "payDate";
//            public static final String payTypeName = "payTypeName";
//        }
//
//        public class MALL_RES {
//            public static final String name = "name";
//            public static final String earn = "earn";
//            public static final String rpsUse = "rpsUse";
//            public static final String ssn = "ssn";
//            public static final String address = "address";
//            public static final String owner = "owner";
//            public static final String leaflet = "leaflet";
//        }
    }

    // request
    public void setParameterMobile(String param) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, param);
    }

    public void setParameterSerial(String param) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, param);
    }

    public void setParameterKind(String param) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.kind, param);
    }

    public void setParameterAmount(String param) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.amount, param);
    }
}
