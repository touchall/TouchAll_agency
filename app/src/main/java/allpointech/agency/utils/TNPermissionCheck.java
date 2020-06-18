package allpointech.agency.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.tuna.utils.SLog;

import java.util.ArrayList;

import allpointech.agency.R;


/**
 * Created by Ace on 2016-08-11.
 */
public class TNPermissionCheck extends Activity {
    private static final int REQUEST_PERMISSION = 3845;
    private String[] receivePermissionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            receivePermissionArray = getIntent().getStringArrayExtra("permission");
            SLog.LogD("Ace : " + receivePermissionArray);
            if (receivePermissionArray.length > 0) {
                requestPermission();
            }
        }
        setContentView(R.layout.activity_transparent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermission() {
        if (lacksPermission(receivePermissionArray).length > 0) {
            requestPermissions(lacksPermission(receivePermissionArray), REQUEST_PERMISSION);
        } else {
            Intent i = new Intent();
            i.putExtra("result_granted", receivePermissionArray);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public String[] lacksPermission(String... permissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                result.add(permission);
            }
        }
        return result.toArray(new String[result.size()]);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> result_granted = new ArrayList<>();
        ArrayList<String> result_denial = new ArrayList<>();
        switch (requestCode) {
            case REQUEST_PERMISSION:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        result_granted.add(permissions[i]);
                    } else {
                        result_denial.add(permissions[i]);
                    }
                }
                Intent i = new Intent();
                i.putExtra("result_granted", result_granted.toArray(new String[result_granted.size()]));
                i.putExtra("result_denial", result_denial.toArray(new String[result_denial.size()]));
                setResult(RESULT_OK, i);
                finish();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
