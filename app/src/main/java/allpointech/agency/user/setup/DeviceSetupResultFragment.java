package allpointech.agency.user.setup;

import android.os.AsyncTask;
import android.os.Bundle;
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
import allpointech.agency.network.http.resource.data.ResReqTelegram;
import allpointech.agency.utils.TNPreference;

/**
 * Created by jay on 2018. 6. 27..
 */

public class DeviceSetupResultFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = DeviceSetupResultFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView mTvFranchiseeName;

    private TextView mTvInsertPointResult;

    private String mJunmun;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mJunmun = getArguments().getString("device_setup");
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_device_setup;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mTvFranchiseeName = (TextView) view.findViewById(R.id.tv_insert_point_franchisee);

        mTvInsertPointResult = (TextView) view.findViewById(R.id.tv_insert_point_result);
    }

    private void requestDeviceSetup() {
//        int index = 0;
//        String setup_date = mJunmun.substring(index, index+8); index+=8;
//        String setup_time = mJunmun.substring(index, index+6); index+=6;
//        String setup_type = mJunmun.substring(index, index+1); index+=1;
//        String device_serial = mJunmun.substring(index, index+14); index+=14;
//        String group_type = mJunmun.substring(index, index+1); index+=1;
//        String group_code = mJunmun.substring(index, index+10); index+=10;
//        String bonus_kinds = mJunmun.substring(index, index+6); index+=6;
//        String bonus_rate = mJunmun.substring(index, index+3); index+=3;
//        String stemp_rate = mJunmun.substring(index, index+5); index+=5;
//        String game_rate = mJunmun.substring(index, index+5); index+=5;
//        String regular_rate = mJunmun.substring(index, index+2); index+=2;
//        String pay_cost_rate = mJunmun.substring(index, index+10); index+=10;
//        String pay_type_rate = mJunmun.substring(index, index+3); index+=3;
//        String time_rate = mJunmun.substring(index, index+10); index+=10;
//        String send_type = mJunmun.substring(index, index+1); index+=1;
//        String wait_time = mJunmun.substring(index, index+3); index+=3;
//
//
//        ResDeviceSetup res = new ResDeviceSetup();
//        res.setToken(TNPreference.getMemToken(getActivity()));
//
//        bonus_kinds = bonus_kinds.replaceAll("1", "Y");
//        bonus_kinds = bonus_kinds.replaceAll("0", "N");
//
//        res.setParameter(device_serial,
//                group_type,
//                bonus_kinds,
//                bonus_rate,
//                stemp_rate,
//                game_rate,
//                send_type,
//                wait_time,
//                regular_rate,
//                pay_cost_rate.substring(0, 7),
//                pay_cost_rate.substring(8, 9),
//                pay_type_rate.substring(0, 0),
//                pay_type_rate.substring(1, 2),
//                time_rate);
//
////        res.setParameter("KR201808100003",
////                group_type,
////                bonus_kinds,
////                bonus_rate,
////                stemp_rate,
////                game_rate,
////                send_type,
////                wait_time,
////                regular_rate,
////                pay_cost_rate.substring(0, 7),
////                pay_cost_rate.substring(8, 9),
////                pay_type_rate.substring(0, 0),
////                pay_type_rate.substring(1, 2),
////                time_rate);

        ResReqTelegram res = new ResReqTelegram();
        res.setToken(TNPreference.getMemToken(getActivity()));
        res.setParameterMobile(TNPreference.getMemphoneNumber(getActivity()));
        res.setParameterTelegram(mJunmun);
        res.setParameterRps("N");

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    protected void initRequest() {
        requestDeviceSetup();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                UserMainFragment fragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);
//                if (fragment != null) {
//                    fragment.openDrawer();
//                }
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {

//        if (o[0] instanceof ResDeviceSetup) {
//            ResDeviceSetup res = (ResDeviceSetup) o[0];
//            JSONObject obj = res.getParseData();
//            if (JSONParser.isSuccess(obj)) {
////                JSONObject obj_franchisee = JSONParser.getObject(obj, ResJuklib.KEY_RES.franchisee);
////                JSONObject obj_insertPoint = JSONParser.getObject(obj, ResJuklib.KEY_RES.insert_point);
////                String franchisee_type = JSONParser.getString(obj_franchisee, ResJuklib.KEY_RES.KEY_FRANCHISEE.uncode).substring(0, 1);
////                String franchisee_name = JSONParser.getString(obj_franchisee, ResJuklib.KEY_RES.KEY_FRANCHISEE.unname);
////                String insert_point = JSONParser.getString(obj_insertPoint, ResJuklib.KEY_RES.KEY_INSERTPOINT.mbocpt);
////
////                if (franchisee_type.equals("G")) {
////                    franchisee_type = "그룹형";
////                }
////                else if (franchisee_type.equals("S")) {
////                    franchisee_type = "단독형";
////                }
////                else if (franchisee_type.equals("T")) {
////                    franchisee_type = "통합형";
////                }
//
//                mTvFranchiseeName.setText("보너스 설정이 정상 처리 되었습니다.");
//                mTvInsertPointResult.setText("");
//            }

        if (o[0] instanceof ResReqTelegram) {
            ResReqTelegram res = (ResReqTelegram) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
//                JSONObject obj_franchisee = JSONParser.getObject(obj, ResJuklib.KEY_RES.franchisee);
//                JSONObject obj_insertPoint = JSONParser.getObject(obj, ResJuklib.KEY_RES.insert_point);
//                String franchisee_type = JSONParser.getString(obj_franchisee, ResJuklib.KEY_RES.KEY_FRANCHISEE.uncode).substring(0, 1);
//                String franchisee_name = JSONParser.getString(obj_franchisee, ResJuklib.KEY_RES.KEY_FRANCHISEE.unname);
//                String insert_point = JSONParser.getString(obj_insertPoint, ResJuklib.KEY_RES.KEY_INSERTPOINT.mbocpt);
//
//                if (franchisee_type.equals("G")) {
//                    franchisee_type = "그룹형";
//                }
//                else if (franchisee_type.equals("S")) {
//                    franchisee_type = "단독형";
//                }
//                else if (franchisee_type.equals("T")) {
//                    franchisee_type = "통합형";
//                }

                mTvFranchiseeName.setText("보너스 설정이 정상 처리 되었습니다.");
                mTvInsertPointResult.setText("");
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
    public void onClick(View view) {

    }

    @Override
    public void onBack() {
        //replaceAnimationTagFragment(R.id.contents, new UserMainViewFragment(), UserMainViewFragment.FRAGMENT_TAG, 0, 0);
        getActivity().finish();
    }

}
