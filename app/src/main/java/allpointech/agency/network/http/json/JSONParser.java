package allpointech.agency.network.http.json;

import com.tuna.utils.SLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import allpointech.agency.network.http.resource.ResBaseHttp;

/**
 * Created by admin on 2015-12-16.
 */
public class JSONParser {
    private static boolean DEBUG = false;

    /**
     * @param str
     * @return
     */
    private static boolean isNull(String str) {
        if (str != null && !("null").equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static JSONObject getObject(JSONObject obj, String key) {
        JSONObject ret = null;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    ret = obj.getJSONObject(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + ret.toString());
                }
            }
        } catch (JSONException je) {
        }
        return ret;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static JSONArray getArray(JSONObject obj, String key) {
        JSONArray array = new JSONArray();

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    array = obj.getJSONArray(key);
                    if (DEBUG) SLog.LogD("JSON Array : " + key + "-" + array.toString());
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Array : " + key + "-" + array.length());
        }
        return array;
    }

    /**
     * @param jArray
     * @param index
     * @return
     */
    public static JSONObject getArrayItem(JSONArray jArray, int index) {
        JSONObject obj = null;
        try {
            if (jArray != null) {
                if (jArray.length() > index) {
                    obj = jArray.getJSONObject(index);
                    if (DEBUG) SLog.LogD("JSON Object : " + obj.toString());
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Object : " + index + "-" + jArray.length());
        }
        return obj;
    }

    /**
     * @param obj
     * @return
     */
    public static ArrayList<String> getKeyIterator(JSONObject obj) {
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> iter = obj.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            list.add(key);
            if (DEBUG) SLog.LogD("JSON Iterator : " + key + "-" + key);
        }
        return list;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static String getString(JSONObject obj, String key) {
        String str = "";

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    str = obj.getString(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + str);
                }

            }
        } catch (JSONException je) {
            SLog.LogD("get String : " + key + "-" + str);
        }
        return str;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static String getString(JSONObject obj, String key, String def_value) {
        String str = def_value;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    str = obj.getString(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + str);
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get String : " + key + "-" + str);
        }
        return str;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static String getStringIntFormat(JSONObject obj, String key) {
        String str = "";

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    str = valueToIntFormat(JSONParser.getInt(obj, key, 0));
                    if (DEBUG) SLog.LogD("JSON String Money : " + key + "-" + str);
                }

            }
        } catch (JSONException je) {
            SLog.LogD("get String : " + key + "-" + str);
        }
        return str;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static String getStringFloatFormat(JSONObject obj, String key) {
        String str = "";

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    str = valueToFloatFormat(JSONParser.getFloat(obj, key, 0));
                    if (DEBUG) SLog.LogD("JSON String Money : " + key + "-" + str);
                }

            }
        } catch (JSONException je) {
            SLog.LogD("get String : " + key + "-" + str);
        }
        return str;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static int getInt(JSONObject obj, String key, int def_value) {
        int value = def_value;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    value = obj.getInt(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + value);
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Int : " + key + "-" + value);
        }
        return value;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static long getLong(JSONObject obj, String key, long def_value) {
        long value = def_value;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    value = obj.getLong(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + value);
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get long : " + key + "-" + value);
        }
        return value;
    }

    /**
     * @param obj
     * @param key
     * @param def_value
     * @return
     */
    public static float getFloat(JSONObject obj, String key, float def_value) {
        float value = def_value;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    value = (float) obj.getDouble(key);
                    if (DEBUG) SLog.LogD("JSON double : " + key + "-" + value);
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Float : " + key + "-" + value);
        }
        return value;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static double getDouble(JSONObject obj, String key, double def_value) {
        double value = def_value;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    value = obj.getDouble(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + value);
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Double : " + key + "-" + value);
        }
        return value;
    }

    /**
     * @param obj
     * @param key
     * @return
     */
    public static boolean getBoolean(JSONObject obj, String key) {
        boolean ret = false;

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    ret = obj.getBoolean(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + ret);
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Boolean : " + key + "-" + ret);
        }
        return ret;
    }

    /**
     * @param obj
     * @param key
     * @return URL 주소 없을시 <Return_Not_URL> 리턴
     */
    public static String getUrl(JSONObject obj, String key) {
        String url = "Return_Not_URL";

        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    url = obj.getString(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + url);
                    if (!(url).contains("http")) {
                        url = "Return_Not_URL";
                    }
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Url String : " + key + "-" + url);
        }
        return url;
    }

    /**
     * @param obj
     * @param key
     * @return URL 주소 없을시 <NULL> 리턴
     */
    public static String getUrlNull(JSONObject obj, String key) {
        String url = null;
        try {
            if (obj != null) {
                if (obj.has(key) && !isNull(obj.getString(key))) {
                    url = obj.getString(key);
                    if (DEBUG) SLog.LogD("JSON String : " + key + "-" + url);
                    if (!(url).contains("http")) {
                        url = null;
                    }
                }
            }
        } catch (JSONException je) {
            SLog.LogD("get Url Null : " + key + "-" + "null");
        }
        return url;
    }

    public static void putObject(JSONObject obj, String key, JSONObject val) {
        try {
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put Object : " + key + "-" + val.toString());
        }
    }

    /**
     * @param obj
     * @param key
     * @param val
     */
    public static void putArray(JSONObject obj, String key, JSONArray val) {
        try {
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put Array : " + key + "-" + val.toString());
        }
    }

    /**
     * @param obj
     * @param key
     * @param val
     */
    public static void putString(JSONObject obj, String key, String val) {
        try {
            if (val == null) {
                val = "";
            }
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put String : " + key + "-" + val.toString());
        }
    }

    /**
     * @param obj
     * @param key
     * @param val
     */
    public static void putInt(JSONObject obj, String key, int val) {
        try {
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put Int : " + key + "-" + val);
        }
    }

    /**
     * @param obj
     * @param key
     * @param val
     */
    public static void putLong(JSONObject obj, String key, long val) {
        try {
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put Int : " + key + "-" + val);
        }
    }

    /**
     * @param obj
     * @param key
     * @param val
     */
    public static void putFloat(JSONObject obj, String key, float val) {
        try {
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put Float : " + key + "-" + val);
        }
    }

    /**
     * @param obj
     * @param key
     * @param val
     */
    public static void putBoolean(JSONObject obj, String key, boolean val) {
        try {
            obj.put(key, val);
        } catch (JSONException e) {
            SLog.LogD("put Boolean : " + key + "-" + val);
        }
    }

    /**
     * 통신 데이터의 성공유무
     *
     * @param obj 전달받은 OBJECT
     * @return SUCCESS(true), FAIL(false)
     */
    public static boolean isSuccess(JSONObject obj) {
        if (obj == null) {
            return false;
        }
//        int return_code = getInt(obj, ResBaseHttp.RETURNCODE, ResourceError.INVALID);
//        if (ResourceError.SUCCESS == return_code) {
//            if (DEBUG) SLog.LogD("isSuccess success : " + return_code);
//            return true;
//        } else {
//            if (DEBUG) SLog.LogD("isSuccess fail : " + return_code);
//            return false;
//        }

        boolean return_code = getBoolean(obj, ResBaseHttp.RETURNCODE);
        if (true == return_code) {
            if (DEBUG) SLog.LogD("isSuccess success : " + return_code);
            return true;
        } else {
            if (DEBUG) SLog.LogD("isSuccess fail : " + return_code);
            return false;
        }
    }

    public static String valueToIntFormat(int val) {
        String ret = "";
        try {
            DecimalFormat format = new DecimalFormat("#,###");
            ret = format.format(val);
        } catch (NumberFormatException nfe) {
            ret = "";
        } finally {
            return ret;
        }
    }

    public static String valueToFloatFormat(float val) {
        String ret = "";
        try {
            DecimalFormat format = new DecimalFormat("0.##");
            ret = format.format(val);
        } catch (NumberFormatException nfe) {
            ret = "";
        } finally {
            return ret;
        }
    }

}
