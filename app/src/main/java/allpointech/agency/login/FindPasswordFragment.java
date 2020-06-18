package allpointech.agency.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResFind;

/**
 * Created by daze on 2016-11-15.
 */

public class FindPasswordFragment extends BaseFragment implements BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = FindPasswordFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView mTvFindMsg;
    private TextInputEditText mEdEmail;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_find_password;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mEdEmail = (TextInputEditText) view.findViewById(R.id.edit_email);
        mTvFindMsg = (TextView) view.findViewById(R.id.tv_find_msg);
    }

    @Override
    protected void initRequest() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_ok, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBack();
                break;
            case R.id.action_ok:
                if (onCheck()) {
                    requestFindPassword();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean onCheck() {
        if (mEdEmail.getText().length() == 0) {
            showToast(getString(R.string.msg_error_find_email));
            return false;
        }
        return true;
    }

    private void requestFindPassword() {
        //통신
        // retune_code 에 따라 mTvFindMsg msg 변경
        ResFind res = new ResFind();
        res.setParameter(mEdEmail.getText().toString());

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResFind) {
            ResFind res = (ResFind) o[0];
            JSONObject obj = res.getParseData();
            mTvFindMsg.setVisibility(View.VISIBLE);
            if (JSONParser.isSuccess(obj)) {
                mTvFindMsg.setText(getString(R.string.msg_success_find));
            } else {
                mTvFindMsg.setText(getString(R.string.msg_error_find_fail));
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
        replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new LoginFragment(), LoginFragment.FRAGMENT_TAG, 0, 0);
    }


}
