package allpointech.agency.network.http.resource;

import java.util.HashMap;

/**
 * @Desc : err code
 * @ClassName : ResourceError
 * @FileName : ResourceError.java
 * @Author : sunkyu.yun@rocateer.com
 * @history <dd><dl>
 * - |  |
 * </dl></dd>
 */
public class ResourceError {
    public static final int INVALID = -1;
    // SUCCESS
    public static final int SUCCESS = 1000;
    // SUCCESS

    // ERROR
    public static final int ERROR = -1;
    public static final int ERROR_SEND = -2;

    public static final int ERR_NOT_CONNECTED = 111400;
    // ERROR

    private static final HashMap<Integer, String> ERROR_MAP = new HashMap<Integer, String>();

    static {
        ERROR_MAP.put(ERR_NOT_CONNECTED, "네트워크 연결이 원활하지 않습니다.");
    }

    public static String getErrorMsg(int errorCode) {
        return ERROR_MAP.get(errorCode);
    }
}
