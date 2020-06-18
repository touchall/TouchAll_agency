package allpointech.agency.user.store;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResStore;
import allpointech.agency.user.UserMainFragment;

/**
 * Created by jay on 2018-07-10.
 */

public class FindStoreFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = FindStoreFragment.class.getSimpleName();

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
        return R.layout.fragment_user_find_store;
    }

    private void requestStore() {
        ResStore res = new ResStore();
        //res.setToken(TNPreference.getMemToken(getActivity()));

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    protected void initRequest() {
        requestStore();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.user_favorite, menu);
        inflater.inflate(R.menu.menu_find, menu);
        inflater.inflate(R.menu.menu_map, menu);
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
            case R.id.action_find_list:
                replaceAnimationTagFragment(R.id.find_store_component, new FindStoreListFragment(), FindStoreListFragment.FRAGMENT_TAG, 0, 0);
                break;
            case R.id.action_find_map:
                //replaceAnimationTagFragment(R.id.find_store_component, new FindStoreMapFragment(), FindStoreMapFragment.FRAGMENT_TAG, 0, 0);
                Toast.makeText(getActivity(), "준비 중입니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        replaceAnimationTagFragment(R.id.find_store_component, new FindStoreListFragment(), FindStoreListFragment.FRAGMENT_TAG, 0, 0);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResStore) {
            ResStore res = (ResStore)o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
//                JSONObject obj_info = JSONParser.getObject(obj, ResStore.KEY_RES.message);
//                if (obj_info != null) {
//                    String code = JSONParser.getString(obj_info, "code");
//                    String name = JSONParser.getString(obj_info, "name");
//                    int length = JSONParser.getInt(obj_info, "length", 0);
//                    String status = JSONParser.getString(obj_info, "status");
//                }
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
    public void onClick(View v) {

    }

    @Override
    public void onBack() {
        //replaceAnimationTagFragment(R.id.contents, new UserMainViewFragment(), UserMainViewFragment.FRAGMENT_TAG, 0, 0);
        UserMainFragment fragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);
        if (fragment != null) {
            fragment.onGoHome();
        }
    }

}
