package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResFranchiseeStoreDevicePreUpdate extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.PUT;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String serial = "serial";
        public static final String earn = "earn";
        public static final String kinds = "kinds";
        public static final String point = "point";
        public static final String stamp = "stamp";
        public static final String rps = "rps";
        public static final String transfer = "transfer";
        public static final String delay = "delay";
        public static final String advancement = "advancement";
        public static final String amount = "amount";
        public static final String method = "method";
        public static final String time = "time";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String serial = "serial";
        public static final String sid = "sid";
        public static final String store = "store";
        public static final String earn = "earn";
        public static final String pointUse = "pointUse";
        public static final String stampUse = "stampUse";
        public static final String rpsUse = "rpsUse";
        public static final String remnant = "remnant";
        public static final String receipt = "receipt";
        public static final String leaflet = "leaflet";
        public static final String vat = "vat";
        public static final String kinds = "kinds";
        public static final String point = "point";
        public static final String stamp = "stamp";
        public static final String rps = "rps";
        public static final String transfer = "transfer";
        public static final String delay = "delay";
        public static final String advancement = "advancement";
        public static final String amount = "amount";
        public static final String amountRatio = "amountRatio";
        public static final String method = "method";
        public static final String methodRatio = "methodRatio";
        public static final String time = "time";
        public static final String stime = "stime";
        public static final String etime = "etime";
        public static final String createdAt = "createdAt";
        public static final String lastModified = "lastModified";
        public static final String telegram = "telegram";

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
        setMultipartData(MultiPartData.FORM, KEY_REQ.delay, params[7]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.advancement, params[8]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.amount, params[9]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.method, params[10]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.time, params[11]);
    }
}
