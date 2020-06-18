package allpointech.agency.webview;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by jay on 2019-04-02.
 */

public class WebViewInterface {

    private WebView mAppView;
    private Activity mContext;

    /**
     * 생성자.
     * @param activity : context
     * @param view : 적용될 웹뷰
     */
    public WebViewInterface(Activity activity, WebView view) {
        mAppView = view;
        mContext = activity;
    }
    /**
     * OB단말 설정 화면으로 이동.
     * @param deviceSerialNo : 메시지
     */
    @JavascriptInterface
    public void gotoDeviceSetup (String deviceSerialNo) { // Show toast for a short time
        //Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
