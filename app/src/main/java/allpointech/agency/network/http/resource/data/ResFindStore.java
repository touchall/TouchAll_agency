package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResFindStore extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.GET_PARAM;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
//        public static final String keywordNumber = "keywordNumber";
//        public static final String keywrodString = "keywrodString";
//        public static final String sectors = "sectors";
//        public static final String addr = "addr";
//        public static final String address = "address";
//        public static final String ssn = "ssn";
//        public static final String sid = "sid";
//        public static final String limit = "limit";
//        public static final String offset = "offset";
        public static final String serial = "serial";
        public static final String name = "name";
        public static final String area = "area";
        public static final String earn = "earn";
        public static final String sectors = "sectors";
        public static final String pageSize = "pageSize";
        public static final String offset = "offset";


    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
    }

    public void setParameterSerial(String Serial) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.serial, Serial);
    }

    public void setParameterName(String Name) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.name, Name);
    }

    public void setParameterArea(String Area) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.area, Area);
    }

    public void setParameterEarn(String Earn) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.earn, Earn);
    }

    public void setParameterSectors(String Sectors) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.sectors, Sectors);
    }

    public void setParameterPageSize(String PageSize) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.pageSize, PageSize);
    }

    public void setParameterOffset(String OffSet) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.offset, OffSet);
    }
//
//    public void setParameterKeywordNumber(String KeywordNumber) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.keywordNumber, KeywordNumber);
//    }
//
//    public void setParameterKeywordString(String KeywordString) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.keywrodString, KeywordString);
//    }
//
//    public void setParameterSectors(String Sectors) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.sectors, Sectors);
//    }
//
//    public void setParameterAddr(String Addr) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.addr, Addr);
//    }
//
//    public void setParameterAddress(String Address) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.address, Address);
//    }
//
//    public void setParameterSSN(String SSN) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.ssn, SSN);
//    }
//
//    public void setParameterSID(String SID) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.sid, SID);
//    }
//
//    public void setParameterLIMIT(String LIMIT) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.limit, LIMIT);
//    }
//
//    public void setParameterOFFSET(String OFFSET) {
//        setMultipartData(MultiPartData.FORM, KEY_REQ.offset, OFFSET);
//    }
}
