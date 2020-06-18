package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResFindMyPoint extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.GET_PARAM;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String name = "name";
        public static final String sdate = "sdate";
        public static final String edate = "edate";
        public static final String area = "area";
        public static final String sectors = "sectors";
        public static final String type = "type";
        public static final String kind = "kind";
        public static final String earn = "earn";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
    }

    public void setParameterName(String Name) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.name, Name);
    }

    public void setParameterStartDate(String startDate) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.sdate, startDate);
    }

    public void setParameterEndDate(String endDate) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.edate, endDate);
    }

    public void setParameterArea(String Area) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.area, Area);
    }

    public void setParameterSectors(String Sectors) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.sectors, Sectors);
    }

    public void setParameterType(String Type) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.type, Type);
    }

    public void setParameterKind(String Kind) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.kind, Kind);
    }

    public void setParameterEarn(String Earn) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.earn, Earn);
    }
}
