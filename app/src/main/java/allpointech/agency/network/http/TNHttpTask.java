package allpointech.agency.network.http;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.tuna.utils.SLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import allpointech.agency.BuildConfig;
import allpointech.agency.network.http.exception.ParsingException;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.ResourceError;
import allpointech.agency.network.http.utils.HttpPopupManager;
import allpointech.agency.network.http.utils.HttpProgressDialog;


public class TNHttpTask extends AsyncTask<BaseHttpResource, Void, BaseHttpResource[]> implements HttpPopupManager.PopupDialog.OnResultListener {
    private static final String TAG = "HttpLog";
    private Dialog mDialog;
    private Context mContext;
    private static boolean debug = BuildConfig.DEBUG;
    private onHttpNetResultListener mListener;
    private boolean isDialogEnable = true;

    public interface onHttpNetResultListener {
        public void onHttpNetSuccessEvent(BaseHttpResource[] o);

        // debug
        public void onHttpDebugEvent(String debug_msg);

        // debug
        public void onHttpNetFailEvent(int errorCode);

        public void onHttpNetFailEvent(int errorCode, String errorMsg);

        public void onHttpNetFailEvent(BaseHttpResource[] o);
    }

    public TNHttpTask(Context c) {
        super();
        mContext = c;
    }

    @Override
    protected void onPreExecute() {
        try {
            mDialog = new HttpProgressDialog(mContext);
            if (isDialogEnable) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPreExecute();
    }

    public void setDialogEnabled(boolean isEnable) {
        isDialogEnable = isEnable;
    }

    @Override
    protected void onPostExecute(BaseHttpResource[] result) {
        super.onPostExecute(result);
        if (isDialogEnable)
            mDialog.dismiss();

        for (int i = 0; i < result.length; i++) {
            switch (result[i].mErrorCode) {
                case HttpURLConnection.HTTP_OK:
                    continue;
                case ResourceError.ERR_NOT_CONNECTED:
//			case ResourceError.ERR_CONNECT_TIMEOUT:
//			case ResourceError.ERR_SOCKET_TIMEOUT:
//			case ResourceError.ERR_UNKNOWN_HOST:
//			case ResourceError.ERR_PARSING:
//			case ResourceError.ERR_UNKNOWN:
                    HttpPopupManager.getPopup(mContext, result[i].mErrorCode, result[i].mErrorMsg, this).show();
                    return;
                default:
                    if (result[i].mErrorTitle != null) {
                        HttpPopupManager.getPopup(mContext, result[i].mErrorCode, result[i].mErrorMsg, result[i].mErrorTitle, this).show();
                    } else {
                        HttpPopupManager.getPopup(mContext, result[i].mErrorCode, result[i].mErrorMsg, this).show();
                    }
                    if (mListener != null)
                        //mListener.onHttpNetFailEvent(result[i].mErrorCode, result[i].mErrorMsg);
                        mListener.onHttpNetFailEvent(result);
                    return;
            }
        }

        if (mListener != null) {
            mListener.onHttpNetSuccessEvent(result);
        }
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
            param = jsonConnect(param);

            retVal[i] = param;
        }
        return retVal;
    }

    public void setOnHttpResultListener(onHttpNetResultListener listener) {
        mListener = listener;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private BaseHttpResource jsonConnect(BaseHttpResource param) {
        URL cn_url = null;
        JSONObject responseJSON = null;
        try {
            cn_url = new URL(param.getURL());
            HttpURLConnection conn = null;

            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;

            conn = (HttpURLConnection) cn_url.openConnection();
            conn.setConnectTimeout(HttpInfo.TIME_OUT);
            conn.setReadTimeout(HttpInfo.TIME_OUT);
            conn.setRequestMethod(param.getType());
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("User-Agent", HttpInfo.Header.USER_AGENT);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            mListener.onHttpDebugEvent("연결 정보 : " + conn.toString() + "\n");
            os = conn.getOutputStream();
            SLog.LogD("send data = " + param.getHttpResponseData().toString());
            //debug
            mListener.onHttpDebugEvent("전송 데이터 : " + param.getHttpResponseData().toString());
            os.write(param.getHttpResponseData().toString().getBytes(HttpInfo.UTF8));
            os.flush();

            String response;

            param.mErrorCode = conn.getResponseCode();

            SLog.LogD("responseCode:" + param.mErrorCode);

            if (param.mErrorCode == HttpURLConnection.HTTP_OK) {
                mListener.onHttpDebugEvent("> 응답 성공!!");
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData, HttpInfo.UTF8);


                mListener.onHttpDebugEvent("> 응답 변환 전 데이터 : " + response);
                responseJSON = new JSONObject(response);

                try {
                    param.parsorRes(responseJSON);
                } catch (ParsingException e) {
                    e.printStackTrace();
                }

                mListener.onHttpDebugEvent("> 응답 데이터 : " + responseJSON.toString());
            } else {
                //debug
                mListener.onHttpDebugEvent("> 응답 에러 : " + param.mErrorCode);

            }
            conn.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return param;
    }

}
