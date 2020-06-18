package allpointech.agency.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.text.Html;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;


import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResConsignment;
import allpointech.agency.network.http.resource.data.ResPositionAgree;
import allpointech.agency.network.http.resource.data.ResTerms;
import allpointech.agency.utils.TNConstants;

/**
 * Created by jay on 2018. 6. 24..
 */

public class ShowAgreeFragment extends BaseFragment implements BaseAppCompatActivity.onKeyBackPressedListener, View.OnClickListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = ShowAgreeFragment.class.getSimpleName();

    private Toolbar mToolbar;
    private TextView mHtmlTextView;

    private int mWebViewType = 0;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mWebViewType = getArguments().getInt(TNConstants.USER_JUNMUN_TYPE.KEY_TYPE, 0);
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_show_agree;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mHtmlTextView = (TextView) view.findViewById(R.id.htmlText);

        String title = "";
        if (TNConstants.USER_JUNMUN_TYPE.TYPE_USE_AGREE == mWebViewType) {
            title = "회원이용 약관";
        } else if (TNConstants.USER_JUNMUN_TYPE.TYPE_PERSONAL_AGREE == mWebViewType) {
            title = "개인정보 수집 및 이용";
        } else if (TNConstants.USER_JUNMUN_TYPE.TYPE_POSITION_AGREE == mWebViewType) {
            title = "위치기반 서비스 이용";
        }
        mToolbar.setTitle(title);
    }

    private void requestConsignment() {
        ResConsignment res = new ResConsignment();

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestTerms() {
        ResTerms res = new ResTerms();

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestPosition() {
        ResPositionAgree res = new ResPositionAgree();

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    protected void initRequest() {
        if (mWebViewType == TNConstants.USER_JUNMUN_TYPE.TYPE_USE_AGREE) {
            requestConsignment();
        } else if (mWebViewType == TNConstants.USER_JUNMUN_TYPE.TYPE_PERSONAL_AGREE) {
            requestTerms();
        } else if (mWebViewType == TNConstants.USER_JUNMUN_TYPE.TYPE_POSITION_AGREE) {
            requestPosition();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_ok, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResConsignment) {
            ResConsignment res = (ResConsignment) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                String szConsignment = JSONParser.getString(obj, "message");
                mHtmlTextView.setText(Html.fromHtml(szConsignment));
                mHtmlTextView.setMovementMethod(new ScrollingMovementMethod());
            } else {
            }
        }
        if (o[0] instanceof ResTerms) {
            ResTerms res = (ResTerms) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                String szTerms = JSONParser.getString(obj, "message");
                mHtmlTextView.setText(Html.fromHtml(szTerms));
                mHtmlTextView.setMovementMethod(new ScrollingMovementMethod());
            } else {
            }
        }
        if (o[0] instanceof ResPositionAgree) {
            ResPositionAgree res = (ResPositionAgree) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                String szPositionAgree = JSONParser.getString(obj, "message");
                mHtmlTextView.setText(Html.fromHtml(szPositionAgree));
                mHtmlTextView.setMovementMethod(new ScrollingMovementMethod());
            } else {
            }
        }
    }

    @Override
    public void onHttpDebugEvent(String debug_msg) {

    }

    @Override
    public void onHttpNetFailEvent(int errorCode, String errorMsg) {

    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}