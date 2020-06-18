package allpointech.agency.network.http;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.tuna.utils.SLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import allpointech.agency.BuildConfig;
import allpointech.agency.dialog.ProgressDialog;
import allpointech.agency.network.http.exception.ParsingException;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.ResBaseHttp;
import allpointech.agency.network.http.resource.ResourceError;
import allpointech.agency.network.http.utils.HttpPopupManager;


public class TNHttpMultiPartTaskEx extends AsyncTask<BaseHttpResource, Void, BaseHttpResource[]> implements HttpPopupManager.PopupDialog.OnResultListener {
    private static final String TAG = "HttpLog";
    private ProgressDialog mProgress;
    private Context mContext;
    private FragmentManager mFm;
    private static boolean debug = BuildConfig.DEBUG;
    private boolean isDialogEnable = true;
    private boolean isTransparent = false;

    // multi part
    //private String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection conn;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;
    // multi part

    private long mTimeGap = 0;

    public void setOnHttpResultListener(onHttpNetResultListener listener) {
        mListener = listener;
    }

    private onHttpNetResultListener mListener;

    public interface onHttpNetResultListener {
        public void onHttpNetSuccessEvent(BaseHttpResource[] o);

        // debug
        public void onHttpDebugEvent(String debug_msg);

        // debug
        public void onHttpNetFailEvent(int errorCode, String errorMsg);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setOnHttpFailResultListener(onHttpNetFailResultListener listener) {
        mFailListener = listener;
    }

    private onHttpNetFailResultListener mFailListener;

    public interface onHttpNetFailResultListener {
        public void onHttpNetFailResultListener(BaseHttpResource resource);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public TNHttpMultiPartTaskEx(Context c, FragmentManager fm) {
        super();
        this.mContext = c;
        this.mFm = fm;
    }

    @Override
    protected void onPreExecute() {
        mTimeGap = System.currentTimeMillis();

//        try {
//            mProgress = new ProgressDialog();
//            if (isDialogEnable) {
//                try {
//                    mProgress.show(mFm, "dialog_progress");
//                    if (isTransparent)
//                        mProgress.setTransparent(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        super.onPreExecute();
    }

    public void setDialogEnabled(boolean isEnable) {
        isDialogEnable = isEnable;
    }

    public void setTransparent(boolean isEnable) {
        isTransparent = isEnable;
    }

    @Override
    protected void onPostExecute(final BaseHttpResource[] result) {
        super.onPostExecute(result);
//        if (isDialogEnable) {
//            try {
//                mProgress.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        boolean successFlag = true;

        for (int i = 0; i < result.length; i++) {
            int return_code = ResourceError.ERROR;
            String return_msg = null;

            if (result[i].mErrorCode == HttpURLConnection.HTTP_OK) {
                try {
                    JSONObject res_obj = (JSONObject) result[i].getParseData();

                    if (res_obj == null) {
                        SLog.LogE("Resource Name : '" + result[i].getTargetName() + "' ------ " + "통신중 응답을 정상적으로 받지 못하였음!");
                        successFlag = false;
                        continue;
                    }
                    SLog.LogW("##" + res_obj.toString());
                    if (!res_obj.isNull(ResBaseHttp.RETURNCODE)) {
                        String returnStr = res_obj.getString(ResBaseHttp.RETURNCODE);
                        String[] resStr = returnStr.split("_");
                        try {
                            //return_code = Integer.parseInt(resStr[0]);
                            return_code = Boolean.parseBoolean(resStr[0]) ? ResourceError.SUCCESS : ResourceError.ERROR;
                        } catch (NumberFormatException nfe) {
                            return_code = ResourceError.ERROR;
                        }
                    }
                    if (!res_obj.isNull(ResBaseHttp.RETURNMSG)) {
                        return_msg = res_obj.getString(ResBaseHttp.RETURNMSG);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else //if (result[i].mErrorCode == ResourceError.ERR_NOT_CONNECTED)
            {
                return_code = ResourceError.ERROR;//result[i].mErrorCode;

                //if (result[i].getParseData() != null)
                {
                    try {
                        JSONObject res_obj = (JSONObject) result[i].getParseData();

                        if (res_obj == null) {
                            SLog.LogE("Resource Name : '" + result[i].getTargetName() + "' ------ " + "통신중 응답을 정상적으로 받지 못하였음!");
                            successFlag = false;
                            continue;
                        }
                        SLog.LogW("##" + res_obj.toString());
                        if (!res_obj.isNull(ResBaseHttp.RETURNMSG)) {
                            return_msg = res_obj.getString(ResBaseHttp.RETURNMSG);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            switch (return_code) {
                case ResourceError.SUCCESS:
                    // 성공
                    break;
                case ResourceError.ERROR:
                    successFlag = false;

//                    final BaseHttpResource res = result[i];
//                    Bundle b = new Bundle();
//                    b.putString("dialog_msg", return_msg);
//
//                    if (isTransparent == false) {
//                        MsgSingleDialog dialog = new MsgSingleDialog();
//                        dialog.setArguments(b);
//                        dialog.setOnResultListener(new BaseDialogFragment.OnResultListener() {
//                            @Override
//                            public void onDialogResult(Object... objects) {
//                                if (mFailListener != null) {
//                                    mFailListener.onHttpNetFailResultListener(res);
//                                }
//                            }
//                        });
//                        dialog.show(mFm, "dialog");
//                    }

                    if (mListener != null) {
                        mListener.onHttpNetFailEvent(return_code, return_msg);
                    }
                    break;
                default:
                    successFlag = false;
//
//                    String message = ResourceError.getErrorMsg(return_code);
//                    if (message != null) {
//                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//                    } else {
//                        // invalid error
//                        Toast.makeText(mContext, "알 수 없는 오류입니다.", Toast.LENGTH_SHORT).show();
//                    }

                    if (mListener != null) {
                        mListener.onHttpNetFailEvent(return_code, return_msg);
                    }
                    break;
            }
        }
        if (successFlag) {
            if (mListener != null) {
                mListener.onHttpNetSuccessEvent(result);
            }
        }

        mTimeGap = System.currentTimeMillis() - mTimeGap;
        SLog.LogD("Time Gap : " + mTimeGap + "ms");
    }

    @Override
    public void onDialogResult(Dialog dialog, String... results) {
        HttpPopupManager.PopupDialog popup = (HttpPopupManager.PopupDialog) dialog;
        if (mListener != null)
            mListener.onHttpNetFailEvent(popup.nErrorCode, null);

        if (isDialogEnable)
            dialog.dismiss();
    }

    @Override
    protected BaseHttpResource[] doInBackground(BaseHttpResource... params) {
        BaseHttpResource[] retVal = new BaseHttpResource[params.length];
        if (debug) Log.d(TAG, "params length " + params.length);
        for (int i = 0; i < params.length; i++) {
            BaseHttpResource param = params[i];
            if (!isOnline()) {
                param.mErrorCode = ResourceError.ERR_NOT_CONNECTED;
                return new BaseHttpResource[]{param};
            }
            param = multipost(param);

            retVal[i] = param;
        }
        return retVal;
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private BaseHttpResource multipost(BaseHttpResource param) {

        // creates a unique boundary based on time stamp
        //boundary = "===" + System.currentTimeMillis() + "===";
        charset = HttpInfo.UTF8;

        try {
            String szUrl = param.getURL();

            if (param.getType() == HttpInfo.GET) {
                String getSubUrl = requestGetData(param);
                if (getSubUrl.length() > 0) {
                    szUrl += getSubUrl;
                }
            }
            else if (param.getType() == HttpInfo.GET_PARAM){
                String getParams = requestGetParam(param);
                if (getParams.length() > 0)
                    szUrl += getParams;
            }

            // SJH   ssl >>>>>>>>>>>>>>>>>>>>
            SSLConnect ssl = new SSLConnect();
            ssl.postHttps(szUrl, 1000, 1000);
            // SJH   >>>>>>>>>>>>>>>>>>>>

            URL url = new URL(szUrl);
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(HttpInfo.TIME_OUT);
            conn.setConnectTimeout(HttpInfo.TIME_OUT);

            if (param.getType() == HttpInfo.GET) {
                conn.setRequestMethod(param.getType());
                String token = param.getToken();
                if (token != null && token.length() > 0)
                    conn.addRequestProperty("Authorization", token);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            else if (param.getType() == HttpInfo.GET_TEXT) {
                conn.setRequestMethod(HttpInfo.GET);
                String token = param.getToken();
                if (token != null && token.length() > 0)
                    conn.addRequestProperty("Authorization", token);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            else if (param.getType() == HttpInfo.GET_PARAM) {
                conn.setRequestMethod(HttpInfo.GET);
                String token = param.getToken();
                if (token != null && token.length() > 0)
                    conn.addRequestProperty("Authorization", token);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            else if (param.getType() == HttpInfo.JSON){
                conn.setRequestMethod(HttpInfo.POST);
                conn.setUseCaches(false);
                conn.setDoOutput(true); // indicates POST method
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("User-Agent", HttpInfo.Header.USER_AGENT);
                outputStream = conn.getOutputStream();

                StringWriter sw = new StringWriter();
                writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

                // param에서 multi data를 조회
                requestMultiPartJson(param);
            }
            else if (param.getType() == HttpInfo.PUT){
                conn.setRequestMethod(param.getType());

                String token = param.getToken();
                if (token != null && token.length() > 0)
                    conn.addRequestProperty("Authorization", token);

                conn.setUseCaches(false);
                conn.setDoOutput(true); // indicates POST method
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("User-Agent", HttpInfo.Header.USER_AGENT);
                outputStream = conn.getOutputStream();

                StringWriter sw = new StringWriter();
                writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

                // param에서 multi data를 조회
                requestMultiPartJson(param);
            }
            else {
                conn.setRequestMethod(param.getType());
                conn.setUseCaches(false);
                conn.setDoOutput(true); // indicates POST method
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "multipart/form-data;");
                conn.setRequestProperty("User-Agent", HttpInfo.Header.USER_AGENT);
                //conn.setRequestProperty("Test", "P.R.T");
                outputStream = conn.getOutputStream();

                StringWriter sw = new StringWriter();
                writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

                // param에서 multi data를 조회
                requestMultiPartData(param);
            }

            param.mErrorCode = conn.getResponseCode();
            if (debug) SLog.LogD("> err : " + param.mErrorCode + "- 200이면 성공한거임.");

            if (param.mErrorCode == HttpURLConnection.HTTP_OK) {
                InputStream is = null;
                String response = null;
                JSONObject responseJSON = null;
                ByteArrayOutputStream baos = null;
                mListener.onHttpDebugEvent("> 응답 성공!!");
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                //byte[] byteBuffer = new byte[1024];
                byte[] byteBuffer = new byte[4096];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData, HttpInfo.UTF8);

                if (debug) SLog.LogW("> 응답 변환 전 데이터 : " + param.getTargetName() + "::" + response);
//				mListener.onHttpDebugEvent("> 응답 변환 전 데이터 : " + response);

                if (param.getType() == HttpInfo.GET_TEXT) {
                    responseJSON = new JSONObject();

                    try {
                        //responseJSON.put(ResBaseHttp.RETURNCODE, ResourceError.ERR_NOT_CONNECTED);
                        responseJSON.put("result", "true");
                        responseJSON.put("message", response);
                        param.parsorRes(responseJSON);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    responseJSON = new JSONObject(response);
                    param.parsorRes(responseJSON);
                }

                if (debug) SLog.LogW("> 응답 데이터 : " + responseJSON.toString());
//				mListener.onHttpDebugEvent("> 응답 데이터 : " + responseJSON.toString());
            } else {
                //debug

                InputStream is = null;
                String response = null;
                JSONObject responseJSON = null;
                ByteArrayOutputStream baos = null;
                mListener.onHttpDebugEvent("> 응답 에러!!");
                is = conn.getErrorStream();
                baos = new ByteArrayOutputStream();
                //byte[] byteBuffer = new byte[1024];
                byte[] byteBuffer = new byte[4096];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData, HttpInfo.UTF8);

                if (debug) SLog.LogW("> 응답 변환 전 데이터 : " + param.getTargetName() + "::" + response);
//				mListener.onHttpDebugEvent("> 응답 변환 전 데이터 : " + response);
                responseJSON = new JSONObject(response);
                param.parsorRes(responseJSON);

                if (debug) SLog.LogW("> 응답 에러 : " + param.mErrorCode);
//				mListener.onHttpDebugEvent("> 응답 에러 : "+param.mErrorCode);
            }
            conn.disconnect();
        } catch (ConnectException e) {
            JSONObject responseJSON = new JSONObject();

            param.mErrorCode = ResourceError.ERR_NOT_CONNECTED;
            try {
                responseJSON.put(ResBaseHttp.RETURNCODE, ResourceError.ERR_NOT_CONNECTED);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            try {
                param.parsorRes(responseJSON);
            } catch (ParsingException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            Log.e(TAG, "multipart post error " + e + "(" + param.getURL() + ")");
        }
        return param;
    }

    private void requestMultiPartData(BaseHttpResource param) {
        ArrayList<MultiPartData> multi_data = param.getMultipartData();
        if (debug) SLog.LogI("data size:" + multi_data.size());
        JSONObject jsonParam = new JSONObject();
        for (int i = 0; i < multi_data.size(); i++) {
            MultiPartData mp = multi_data.get(i);
            switch (mp.type) {
                case MultiPartData.HEADER: // header
                    addHeaderField(mp.name, mp.value);
                    break;
                case MultiPartData.FORM: // form
                    addFormField(mp.name, mp.value);
                    break;
                case MultiPartData.FORM_ONCE: // form
                    addFormFieldOnce(mp.name, mp.value);
                    break;
                case MultiPartData.FILE: // file
                    try {
                        addFilePart(mp.name, new File(mp.value));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if (debug) SLog.LogI("type:" + mp.type + ", key:" + mp.name + ", value:" + mp.value);
        }

        // multi part end
        try {
            writer.write(jsonParam.toString());
            finish();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void requestMultiPartJson(BaseHttpResource param) {
        ArrayList<MultiPartData> multi_data = param.getMultipartData();
        if (debug) SLog.LogI("data size:" + multi_data.size());
        JSONObject jsonParam = new JSONObject();
        for (int i = 0; i < multi_data.size(); i++) {
            MultiPartData mp = multi_data.get(i);
            switch (mp.type) {
                case MultiPartData.FORM: // form
                    //addFormField(mp.name, mp.value);
                    try {
                        jsonParam.put(mp.name, mp.value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if (debug) SLog.LogI("type:" + mp.type + ", key:" + mp.name + ", value:" + mp.value);
        }

        // multi part end
        try {
            writer.write(jsonParam.toString());
            finish();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String requestGetData(BaseHttpResource param) {
        String getParamData = "";
        ArrayList<String> get_datas = param.getGetUrlData();
        if (debug) SLog.LogI("get data size:" + get_datas.size());
        for (int i = 0; i < get_datas.size(); i++) {
            String sub_url = get_datas.get(i);
            getParamData += sub_url;
            if (debug) SLog.LogI("sub url:" + sub_url);
        }
        return getParamData;
    }

    private String requestGetParam(BaseHttpResource param) {
        String getParamData = "";
        ArrayList<MultiPartData> multi_data = param.getMultipartData();
        if (multi_data.size() > 0)
            getParamData += "?";
        for (int i = 0; i < multi_data.size(); i++) {
            MultiPartData mp = multi_data.get(i);
            switch (mp.type) {
                case MultiPartData.FORM: // form
                    //addFormField(mp.name, mp.value);
                    getParamData += mp.name;
                    getParamData += "=";
                    getParamData += mp.value;
                    if (i < multi_data.size() - 1)
                        getParamData += "&";
                    break;
            }
            if (debug) SLog.LogI("type:" + mp.type + ", key:" + mp.name + ", value:" + mp.value);
        }

        return getParamData;
    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */
    public void addFormField(String name, String value) {
        //writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    public void addFormFieldOnce(String name, String value) {
        //writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value);
        writer.flush();
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName  name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        //writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        String file_type = URLConnection.guessContentTypeFromName(fileName);
        writer.append("Content-Type: " + file_type.replaceAll("jpeg", "jpg")).append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        SLog.LogV("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"");
        SLog.LogV("Content-Type: " + URLConnection.guessContentTypeFromName(fileName));
        SLog.LogV("Content-Transfer-Encoding: binary");
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a header field to the request.
     *
     * @param name  - name of the header field
     * @param value - value of the header field
     */
    public void addHeaderField(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Completes the request and receives response from the server.
     *
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public void finish() throws IOException {
        writer.append(LINE_FEED).flush();
        //writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
    }

    //    public List<String> finish() throws IOException {
//        List<String> response = new ArrayList<String>();
// 
//        writer.append(LINE_FEED).flush();
//        writer.append("--" + boundary + "--").append(LINE_FEED);
//        writer.close();
// 
//        // checks server's status code first
//        int status = conn.getResponseCode();
//        if (status == HttpURLConnection.HTTP_OK) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                response.add(line);
//            }
//            reader.close();
//            conn.disconnect();
//        } else {
//            throw new IOException("Server returned non-OK status: " + status);
//        }
// 
//        return response;
//    }
    private String map_index = null;

    public void setStorePostion(String index) {
        map_index = index;
    }

    public String getStorePostion() {
        return map_index;
    }
}
