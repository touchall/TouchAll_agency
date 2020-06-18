package allpointech.agency.network.http;

public class HttpInfo {
    public static int PORT = -1;
    //public static String HTTP_HOST = "http://ians.iptime.org:8080/touchall/";
    //public static String HTTP_HOST = "http://119.193.102.77:8080/touchall/";
    //public static String HTTP_HOST = "https://dev.touchall.co.kr/api/";
    public static String HTTP_HOST = "https://dev.touchall.co.kr/api/";

    public static int TIME_OUT = 60 * 1000;  // SJH

    public static String GET = "GET";
    public static String GET_EX = "GET_EX";
    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String JSON = "JSON";
    public static String GET_PARAM = "GET_PARAM";
    public static String GET_PARAM_EX = "GET_PARAM_EX";
    public static String GET_TEXT = "GET_TEXT";
    public static String OPTIONS = "OPTIONS";

    public class Header {
        public static final String USER_AGENT = "TouchAll";
        public static final String USER_ACCEPT_LANGUAGE = "ko";
    }

    public static String UTF8 = "UTF-8";
}
