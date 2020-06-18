package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResBankReq extends ResBaseHttp {

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
        public static final String banks = "banks";

        public class ROW {
            public static final String code_catagory = "code_catagory";
            public static final String code_sub_catagory = "code_sub_catagory";
            public static final String code_main = "code_main";
            public static final String code_sort_no = "code_sort_no";
            public static final String code_name = "code_name";
            public static final String code_description = "code_description";
            public static final String code_use_yn = "code_use_yn";
        }
    }

    // request
    public void setParameter(String... params) {
    }
}
