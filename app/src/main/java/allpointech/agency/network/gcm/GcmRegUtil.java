package allpointech.agency.network.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tuna.utils.SLog;

import java.io.IOException;

import allpointech.agency.R;


/**
 * Created by admin on 2015-11-22.
 */
public class GcmRegUtil {
    // [[ GCM
    private static GoogleCloudMessaging gcm;
    private static String regId;

    public static void unRegister(final Context context) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
                try {
                    gcm.unregister();
                    restoreRegistrationId(context);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d(GcmRegUtil.class.getSimpleName(), "|" + msg + "|");
            }
        }.execute(null, null, null);
    }

    public static String getRegId(Context context) {
        //String str = "";
        if (regId != null) {
            SLog.LogD("regId Already:" + regId);
            return regId;
        } else {
            if (getGcmKey(context)) {
                SLog.LogD("regId make:" + regId);
                return regId;
            } else {
                return null;
            }
        }
        //return null;
    }
//
//    public static String getRegId(Context context) {
//        return "";
//    }

    private static boolean getGcmKey(Context context) {
        // ---> GCM
        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (checkPlayServices(context)) {
            gcm = GoogleCloudMessaging.getInstance(context);
            regId = getRegistrationId(context);
            SLog.LogV("regId->" + regId);
            if (regId.isEmpty()) {
                registerInBackground(context);
            }
            return true;
        } else {
            SLog.LogD("No valid Google Play Services APK found.");
        }
        return false;
    }

    private static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context, 9000).show();
            } else {
                SLog.LogD("|This device is not supported.|");
            }
            return false;
        }
        return true;
    }

    private static String getRegistrationId(Context context) {
        String registrationId = GcmPreferenceUtil.instance(context).regId();
        if (TextUtils.isEmpty(registrationId)) {
            SLog.LogD("|Registration not found.|");
            return "";
        }
        int registeredVersion = GcmPreferenceUtil.instance(context).appVersion();
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            SLog.LogD("|App version changed.|");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private static void registerInBackground(final Context context) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    String id = context.getString(R.string.google_sender_id);
                    SLog.LogD("Sender ID:" + id);
                    regId = gcm.register(id);
                    msg = "Device registered, registration ID=" + regId;
                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                SLog.LogD("|" + msg + "|");
            }
        }.execute(null, null, null);
    }

    private static void storeRegistrationId(Context context, String regId) {
        int appVersion = getAppVersion(context);
        SLog.LogD("|" + "Saving regId on app version " + appVersion + "|");
        GcmPreferenceUtil.instance(context).putRedId(regId);
        GcmPreferenceUtil.instance(context).putAppVersion(appVersion);
    }

    private static void restoreRegistrationId(Context context) {
        GcmPreferenceUtil.instance(context).putRedId(null);
        GcmPreferenceUtil.instance(context).putAppVersion(-1);
    }
    // GCM ]]
}
