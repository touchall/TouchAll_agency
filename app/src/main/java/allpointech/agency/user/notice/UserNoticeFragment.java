package allpointech.agency.user.notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.user.UserMainFragment;

/**
 * Created by jay on 2018. 6. 19..
 */

public class UserNoticeFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener, UserNoticeListFragment.OnNoticeListClickListener {
    public static final String FRAGMENT_TAG = UserNoticeFragment.class.getSimpleName();

    private Toolbar mToolbar;



    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_notice;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    @Override
    protected void initRequest() {

        UserNoticeListFragment fragment = new UserNoticeListFragment();
        fragment.setOnNoticeListClickListener(this);
        replaceAnimationTagFragment(R.id.notice_component, fragment, UserNoticeListFragment.FRAGMENT_TAG, 0, 0);

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
                UserMainFragment fragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);
                if (fragment != null) {
                    fragment.openDrawer();
                }
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
    public void onNoticeListClick(int position, JSONObject object) {

        if (object != null) {
            Intent i = new Intent(getActivity(), UserNoticeDetailActivity.class);
            i.putExtra("obj", object.toString());
            startActivity(i);
        }
    }

    @Override
    public void onBack() {
        UserMainFragment fragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);
        if (fragment != null) {
            fragment.onBack();
        }
    }

}
