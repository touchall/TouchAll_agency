package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResFranchiseeStoreStempInfo extends ResBaseHttp {
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
        for (int i = 0 ; i < params.length ; i++)
            setGetUrlData(params[i]);
    }

    public void setToken(String token) {
        super.setToken(token);
    }



    @Override
    public String makeType() throws Exception {
        return HttpInfo.GET_EX;
    }
}
