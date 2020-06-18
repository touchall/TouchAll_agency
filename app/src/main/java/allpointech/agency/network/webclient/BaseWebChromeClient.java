package allpointech.agency.network.webclient;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.tuna.ui.fragment.BaseDialogFragment;
import com.tuna.utils.SLog;

import allpointech.agency.dialog.MsgDialog;
import allpointech.agency.dialog.MsgSingleDialog;


public class BaseWebChromeClient extends WebChromeClient {

    private boolean isFragment = false;
    private FragmentManager mFragManager;

    public BaseWebChromeClient(FragmentManager fm) {
        mFragManager = fm;
        isFragment = true;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        SLog.LogD(url + ", " + message);

        if (!isFragment) {
            return super.onJsAlert(view, url, message, result);
        }

        Bundle b = new Bundle();
        b.putString("dialog_msg", message);
        MsgSingleDialog msg = new MsgSingleDialog();
        msg.setArguments(b);
        msg.setOnResultListener(new BaseDialogFragment.OnResultListener() {
            @Override
            public void onDialogResult(Object... obj) {
                if (obj.length > 0 && obj[0] instanceof Boolean) {
                    boolean isOk = (boolean) obj[0];
                    if (isOk) {
                        result.confirm();
                    }
                }
            }
        });
        msg.show(mFragManager, "web_alert");
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        SLog.LogD(url + ", " + message);

        if (!isFragment) {
            return super.onJsConfirm(view, url, message, result);
        }

        Bundle b = new Bundle();
        b.putString("dialog_msg", message);
        MsgDialog msg = new MsgDialog();
        msg.setArguments(b);
        msg.setOnResultListener(new BaseDialogFragment.OnResultListener() {
            @Override
            public void onDialogResult(Object... obj) {
                if (obj.length > 0 && obj[0] instanceof Boolean) {
                    boolean isOk = (boolean) obj[0];
                    if (isOk) {
                        result.confirm();
                    } else {
                        result.cancel();
                    }
                }
            }
        });
        msg.show(mFragManager, "web_confirm");
        return true;
    }

}