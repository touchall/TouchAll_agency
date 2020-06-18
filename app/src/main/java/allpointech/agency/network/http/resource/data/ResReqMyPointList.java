package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResReqMyPointList extends ResBaseHttp {
    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String point_phone_number = "point_phone_number";
        public static final String point_month = "point_month";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {
        public static final String myPointByMonth = "myPointByMonth";

        public class ROW {
            public static final String mbcode = "mbcode";
            public static final String mbseq = "mbseq";
            public static final String uncode = "uncode"; // '가맹점코드',
            public static final String mbbasemonth = "mbbasemonth"; // '정산기준월',
            public static final String mbpaydate = "mbpaydate"; // '결제일시',
            public static final String mbsamt = "mbsamt"; // '결제금액',
            public static final String mbpointtype = "mbpointtype"; // '포인트적입유형',
            public static final String mbsavetype = "mbsavetype"; // '적립구분',
            public static final String mbuspt = "mbuspt"; // '가용포인트',
            public static final String mbocpt = "mbocpt"; // '발생포인트',
            public static final String mbusst = "mbusst"; // '가용스탬프',
            public static final String mbocst = "mbocst"; // '발생스탬프',
            public static final String mbusrps = "mbusrps"; // '가용가위바위보',
            public static final String mbocrps = "mbocrps"; // '발생가위바위보',
            public static final String mbocchg = "mbocchg"; // '거스름돈',
            public static final String mbustpt = "mbustpt"; // '0',
            public static final String mbustst = "mbustst"; // '0',
            public static final String mbustrps = "mbustrps"; // '0'
        }
    }

    // request
    public void setParameter(String... params) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.point_phone_number, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.point_month, params[1]);
    }
}
