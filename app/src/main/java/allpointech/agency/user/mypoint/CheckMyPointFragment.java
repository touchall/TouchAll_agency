package allpointech.agency.user.mypoint;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.user.UserMainFragment;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckMyPointFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = CheckMyPointFragment.class.getSimpleName();

    private Toolbar mToolbar;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            //mDeviceSerial = getArguments().getString("device_serial");
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_check_mypoint;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);


    }

    @Override
    protected void initRequest() {
        replaceAnimationTagFragment(R.id.point_list_component, new CheckMyPointListFragment(), CheckMyPointListFragment.FRAGMENT_TAG, 0, 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
//
//        switch (view.getId()) {
//            case R.id.btn_total_point:
//            case R.id.btn_group_point:
//            case R.id.btn_single_point:
//            case R.id.btn_total_stemp:
//            case R.id.btn_group_stemp:
//            case R.id.btn_single_stemp:
//            case R.id.btn_total_game:
//            case R.id.btn_group_game:
//            case R.id.btn_single_game:
//            {
//                Intent i = new Intent(getActivity(), CheckMyPointDetailActivity.class);
//                switch (view.getId()) {
//                    case R.id.btn_total_point:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_POINT);
//                        break;
//                    case R.id.btn_group_point:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_GROUP_POINT);
//                        break;
//                    case R.id.btn_single_point:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_SINGLE_POINT);
//                        break;
//                    case R.id.btn_total_stemp:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_STEMP);
//                        break;
//                    case R.id.btn_group_stemp:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_GROUP_STEMP);
//                        break;
//                    case R.id.btn_single_stemp:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_SINGLE_STEMP);
//                        break;
//                    case R.id.btn_total_game:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_GAME);
//                        break;
//                    case R.id.btn_group_game:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_GROUP_GAME);
//                        break;
//                    case R.id.btn_single_game:
//                        i.putExtra(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE, TNConstants.MYPOINT_DETAIL_TYPE.TYPE_SINGLE_GAME);
//                        break;
//                }
//
//                startActivity(i);
//            }
//                break;
//            case R.id.radio_select_all:
//            {
//                mRadioAll.setChecked(true);
//                mRadioInsert.setChecked(false);
//                mRadioUse.setChecked(false);
//            }
//                break;
//            case R.id.radio_select_insert:
//            {
//                mRadioAll.setChecked(false);
//                mRadioInsert.setChecked(true);
//                mRadioUse.setChecked(false);
//            }
//                break;
//            case R.id.radio_select_use:
//            {
//                mRadioAll.setChecked(false);
//                mRadioInsert.setChecked(false);
//                mRadioUse.setChecked(true);
//            }
//                break;
//        }
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
