package allpointech.agency.utils;

/**
 * Created by daze on 2016-03-25.
 */
public class TNConstants {
    public static final int EXIT_DELAY = 2500;

    public class REQUEST_CODE {
        public static final int APP_LOGIN = 9998;
        public static final int APP_FINISH = 9999;
        public static final int REFRESH = 9090;
        public static final int SECESSION = 888;

        public static final int DEVICE_SETUP = 8767;
        public static final int INSERT_POINT = 8766;
        public static final int CANCEL_POINT = 8765;
        public static final int CHECK_FRANCHISEE = 8764;
    }

    // 퍼미션
    public class PERMISSION {
        public static final int PERMISSION = 100;
        public static final int PERMISSION_LOCATION = 10001;
        public static final int PERMISSION_RECORD = 10002;
    }

    public class MemberType {
        public static final String TYPE = "type";
    }

    public class WEBVIEW {
        public static final String URL_ROOT = "https://dev.touchall.co.kr";
        public static final String URL_INTRO_COMPANY = URL_ROOT + "/intro/company";
        public static final String URL_INTRO_TOUCHALL = URL_ROOT + "/intro/touchall";
        public static final String URL_EVENT = URL_ROOT + "/customer/event";
        public static final String URL_NOTICE = URL_ROOT + "/customer/notice";
        public static final String URL_FAQ = URL_ROOT + "/customer/faq";


        public static final String URL_FRANCHISEE_USERS_INFO = URL_ROOT + "/store/mall/list";
        public static final String URL_FRANCHISEE_DETAIL_LIST = URL_ROOT + "/store/point/history";
        public static final String URL_FRANCHISEE_MONTHLY_LIST = URL_ROOT + "/store/point/period";
        public static final String URL_FRANCHISEE_AD_TIPS = URL_ROOT + "/store/tip/align";
        public static final String URL_FRANCHISEE_NOTICES = URL_ROOT + "/store/article/notice";

        public static final String URL_AGENCY = URL_ROOT + "/agency";

        public static final String KEY_WEBVIEW_TYPE = "key_webview_type";
        public static final int TYPE_INTRO_COMPANY = 0;
        public static final int TYPE_INTRO_TOUCHALL = 1;
        public static final int TYPE_EVENT = 2;
        public static final int TYPE_NOTICE = 3;
        public static final int TYPE_FAQ = 4;


        public static final String KEY_WEBVIEW_TOKEN = "key_webview_token";
        public static final int TYPE_FRANCHISEE_USERS_INFO = 10;
        public static final int TYPE_FRANCHISEE_DETAIL_LIST = 11;
        public static final int TYPE_FRANCHISEE_MONTHLY_LIST = 12;
        public static final int TYPE_FRANCHISEE_AD_TIPS = 13;
        public static final int TYPE_FRANCHISEE_NOTICES = 14;

        public static final int TYPE_AGENCY = 20;
    }

    public class UserSettingType {
        public static final String TYPE = "user_setting_type";
    }

    public class USER_JUNMUN_TYPE {
        public static final String KEY_TYPE = "junmun_type";
        public static final int TYPE_USE_AGREE       = 0;
        public static final int TYPE_PERSONAL_AGREE  = 1;
        public static final int TYPE_POSITION_AGREE  = 2;
    }

    public class MYPOINT_DETAIL_TYPE {
        public static final String KEY_TYPE = "mypoint_detail_type";
        public static final int TYPE_TOTAL_POINT   = 0;
        public static final int TYPE_GROUP_POINT   = 1;
        public static final int TYPE_SINGLE_POINT  = 2;
        public static final int TYPE_TOTAL_STEMP   = 3;
        public static final int TYPE_GROUP_STEMP   = 4;
        public static final int TYPE_SINGLE_STEMP  = 5;
        public static final int TYPE_TOTAL_GAME     = 6;
        public static final int TYPE_GROUP_GAME     = 7;
        public static final int TYPE_SINGLE_GAME    = 8;
    }
}
