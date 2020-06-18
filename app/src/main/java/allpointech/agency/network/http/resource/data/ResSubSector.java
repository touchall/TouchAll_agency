package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResSubSector extends ResBaseHttp {

    @Override
    public String makeType() throws Exception {
        return HttpInfo.GET;
        //return super.makeType();
    }

    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {

    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String message = "message";
    }

    // request
    public void setParameterQuestion(String... params) {
        setGetUrlData(params[0]);
    }
}
