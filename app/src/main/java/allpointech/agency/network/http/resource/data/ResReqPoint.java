package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResReqPoint extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.GET;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String id = "id";
        public static final String sid = "sid";
        public static final String serial = "serial";
//        public static final String kind = "kind";
//        public static final String earn = "earn";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
    }

//    public void setParameterId(String Id) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.id, Id);
//    }
//
//    public void setParameterSid(String Sid) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.sid, Sid);
//    }
//
//    public void setParameterSerial(String Sid) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.sid, Sid);
//    }

    // request
    public void setParameterId(String Id) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.id, Id);
    }
    public void setParameterSid(String Sid) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.sid, Sid);
    }
    public void setParameterSerial(String Serial) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, Serial);
    }
//    public void setParameter(String... params) {
////        setMultipartData(MultiPartData.FORM, KEY_REQ.point_date, params[0]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.point_time, params[1]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.point_action, params[2]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.point_device_serial, params[3]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.id, params[0]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.sid, params[1]);
//        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, params[2]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.kind, params[3]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.earn, params[4]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.point_phone_number, params[0]);
////        setMultipartData(MultiPartData.FORM, KEY_REQ.point_month, params[1]);
//    }
}
