package allpointech.agency.user.notice;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.resource.BaseHttpResource;

/**
 * Created by jay on 2018. 6. 19..
 */

public class UserNoticeDetailFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = UserNoticeDetailFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private String mObject;

    private TextView mTvNoticeDetailTitle;
    private TextView mTvNoticeDetailMsg;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mObject = getArguments().getString("obj");
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_notice_detail;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mTvNoticeDetailTitle = (TextView) view.findViewById(R.id.tv_notice_detail_title);
        mTvNoticeDetailMsg = (TextView) view.findViewById(R.id.tv_notice_detail_msg);
        if (mObject != null) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(mObject);
            } catch (JSONException e) {
                return;
            }
//            String notice_subject = JSONParser.getString(obj, ResNotice.KEY_RES.ROW.bbs_subject);
//            String notice_contents = JSONParser.getString(obj, ResNotice.KEY_RES.ROW.bbs_contents);
//            //String notice_contents = JSONParser.getString(obj, ResNotice.KEY_RES.ROW.bbs_contents);
//
//            mTvNoticeDetailTitle.setText(Html.fromHtml(notice_subject));
//            mTvNoticeDetailMsg.setText(Html.fromHtml(notice_contents));
        }
    }

    @Override
    protected void initRequest() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.user_favorite, menu);
        //inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {

    }

    @Override
    public void onHttpDebugEvent(String debug_msg) {

    }

    @Override
    public void onHttpNetFailEvent(int errorCode, String errorMsg) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
        }
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

}
