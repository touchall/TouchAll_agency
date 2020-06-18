package allpointech.agency;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

//import com.crashlytics.android.Crashlytics;
import com.tuna.utils.SLog;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import io.fabric.sdk.android.Fabric;

/**
 * Created by user on 2016-03-25.
 */
public class AppInfo extends Application {
    public static final boolean DEBUG = BuildConfig.DEBUG;
//    public static String mMemberIdx = null;
    public static boolean AudioCheck = false;

    @Override
    public void onCreate() {
        super.onCreate();
        SLog.setMode(DEBUG);
        //Fabric.with(this, new Crashlytics());
        //FacebookSdk.sdkInitialize(getApplicationContext());
        Log.e("key_hash", getKeyHash(getApplicationContext(), getPackageName()));
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String getVersion(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    public static File getDirectory(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        return f;
    }

    public static File getTempDirectory(Context c) {
        File f = c.getExternalFilesDir("temp");
        if (!f.exists()) {
            f.mkdir();
        }
        return f;
    }

    public static String getKeyHash(Context context, String package_name) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(package_name, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }

    private static int pendingNotificationsCount = 0;

    public static int getPendingNotificationsCount() {
        return pendingNotificationsCount;
    }

    public static void setPendingNotificationsCount(int pendingNotifications) {
        pendingNotificationsCount = pendingNotifications;
    }

}
