package allpointech.agency.webview;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import allpointech.agency.R;
import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.webclient.BaseWebChromeClient;
import allpointech.agency.network.webclient.BaseWebViewClient;
import allpointech.agency.user.UserMainFragment;
import allpointech.agency.utils.TNConstants;

/**
 * Created by Ace on 2016-12-06.
 */

public class WebViewFragment extends BaseFragment implements BaseAppCompatActivity.onKeyBackPressedListener {
    public static final String FRAGMENT_TAG = WebViewFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private WebView mWebView;
    private WebViewInterface mWebViewInterface;

    private int mWebViewType;
    private String mWebViewToken;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mWebViewType = getArguments().getInt(TNConstants.WEBVIEW.KEY_WEBVIEW_TYPE, 0);
            mWebViewToken = getArguments().getString(TNConstants.WEBVIEW.KEY_WEBVIEW_TOKEN);
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setVisibility(View.GONE);
        String title = "";
        String url = HttpInfo.HTTP_HOST;
        if (TNConstants.WEBVIEW.TYPE_INTRO_COMPANY == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_INTRO_COMPANY;
            title = "회사소개";//getString(R.string.toolbar_help);
        } else if (TNConstants.WEBVIEW.TYPE_INTRO_TOUCHALL == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_INTRO_TOUCHALL;
            title = "터치올소개";//getString(R.string.toolbar_help);
        } else if (TNConstants.WEBVIEW.TYPE_EVENT == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_EVENT;
            title = "이벤트";//getString(R.string.toolbar_notice);
        } else if (TNConstants.WEBVIEW.TYPE_NOTICE == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_NOTICE;
            title = "공지사항";//getString(R.string.toolbar_policy);
        } else if (TNConstants.WEBVIEW.TYPE_FAQ == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_FAQ;
            title = "자주묻는 질문";//getString(R.string.toolbar_tou);
        }

        // franchisee
        if (TNConstants.WEBVIEW.TYPE_FRANCHISEE_USERS_INFO == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_FRANCHISEE_USERS_INFO;
            title = "이용자 조회";//getString(R.string.toolbar_help);
        }
        else if (TNConstants.WEBVIEW.TYPE_FRANCHISEE_DETAIL_LIST == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_FRANCHISEE_DETAIL_LIST;
            title = "상세 취급내역";//getString(R.string.toolbar_help);
        }
        else if (TNConstants.WEBVIEW.TYPE_FRANCHISEE_MONTHLY_LIST == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_FRANCHISEE_MONTHLY_LIST;
            title = "주,월간 내역";//getString(R.string.toolbar_help);
        }
        else if (TNConstants.WEBVIEW.TYPE_FRANCHISEE_AD_TIPS == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_FRANCHISEE_AD_TIPS;
            title = "My 맞춤내역 Tip";//getString(R.string.toolbar_help);
        }
        else if (TNConstants.WEBVIEW.TYPE_FRANCHISEE_NOTICES == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_FRANCHISEE_NOTICES;
            title = "공지,자료실";//getString(R.string.toolbar_help);
        }

        if (TNConstants.WEBVIEW.TYPE_AGENCY == mWebViewType) {
            url = TNConstants.WEBVIEW.URL_AGENCY;
            title = "대리점";//getString(R.string.toolbar_help);
        }

        //url = "http://www.yahoo.com";
        mToolbar.setTitle(title);

        mWebView = (WebView) view.findViewById(R.id.web_view);
        settingWebview(mWebView);
        mWebViewInterface = new WebViewInterface(getActivity(), mWebView);
        mWebView.addJavascriptInterface(mWebViewInterface, "Android");
        mWebView.setWebChromeClient(new BaseWebChromeClient(getFragmentManager()));
        mWebView.setWebViewClient(new BaseWebViewClient());

        mWebView.clearCache(true);
        if (mWebViewToken != null && mWebViewToken.length() > 0){
            Map<String, String> extraHeaders = new HashMap<String, String>();
            //extraHeaders.put("Access-Token", "access_token");
            //extraHeaders.put("Access-Token", mWebViewToken);
            extraHeaders.put("Authorization", mWebViewToken);

            mWebView.loadUrl(url , extraHeaders);
        }
        else {
            mWebView.loadUrl(url);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                UserMainFragment fragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);
                if (fragment != null) {
                    fragment.openDrawer();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initRequest() {

    }

    private void settingWebview(WebView webview) {
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setUseWideViewPort(true);
        webview.setVerticalScrollbarOverlay(true);
        webview.getSettings().setJavaScriptEnabled(true);

        //WebSettings set = webview.getSettings();
        //set.setUserAgentString(set.getUserAgentString() + "/APP_SWC_Android/Version=");

//        webview.getSettings().setUserAgentString(webview.getSettings().getUserAgentString());
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setGeolocationEnabled(true);
//        webview.getSettings().setUseWideViewPort(true);
//        webview.getSettings().setSaveFormData(false);
//        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
//        webview.getSettings().setSupportMultipleWindows(false);
//        webview.getSettings().setDatabaseEnabled(true);
//        webview.getSettings().setDomStorageEnabled(true);
//
//        webview.getSettings().setLoadsImagesAutomatically(true);
//        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public void onBack() {
//        if (TNConstants.MemberType.USER.equalsIgnoreCase(TNPreference.getMemberType(getActivity()))) {
//            replaceAnimationTagFragment(R.id.contents, new AskFragment(), AskFragment.FRAGMENT_TAG, 0, 0);
//        } else {
//            replaceAnimationTagFragment(R.id.contents, new AnswerFragment(), AnswerFragment.FRAGMENT_TAG, 0, 0);
//        }

//        //replaceAnimationTagFragment(R.id.contents, new UserMainViewFragment(), UserMainViewFragment.FRAGMENT_TAG, 0, 0);
//        FranchiseeMainFragment fragment = (FranchiseeMainFragment) getFragmentManager().findFragmentByTag(FranchiseeMainFragment.FRAGMENT_TAG);
//        if (fragment != null) {
//            fragment.onGoHome();
//        }
    }
}
