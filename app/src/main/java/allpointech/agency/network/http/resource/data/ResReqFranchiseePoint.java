package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResReqFranchiseePoint extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        //return super.makeType();
        return HttpInfo.GET_PARAM;
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String id = "id";
        public static final String sid = "sid";
        public static final String serial = "serial";
//        public static final String type = "type";
//        public static final String kind = "kind";
//        public static final String earn = "earn";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
    }

    // request
    public void setParameterId(String userId) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.id, userId);
    }
    public void setParameterSid(String storeId) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.sid, storeId);
    }
    public void setParameterSerial(String deviceSerial) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, deviceSerial);
    }
//    public void setParameterType(String findType) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.type, findType);
//    }
//    public void setParameterKind(String pointKind) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.kind, pointKind);
//    }
//    public void setParameterEarn(String pointEarn) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.earn, pointEarn);
//    }
}
