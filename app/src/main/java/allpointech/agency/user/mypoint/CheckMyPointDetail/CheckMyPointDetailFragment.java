package allpointech.agency.user.mypoint.CheckMyPointDetail;

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
import allpointech.agency.network.http.resource.data.ResReqPoint;
import allpointech.agency.utils.TNConstants;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckMyPointDetailFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = CheckMyPointDetailFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView mTvTitle;

    private int mDetailType = TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_POINT;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mDetailType = getArguments().getInt(TNConstants.MYPOINT_DETAIL_TYPE.KEY_TYPE);
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_check_mypoint_detail;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mTvTitle = (TextView)view.findViewById(R.id.tv_mypoint_detail_title);

        switch (mDetailType) {
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_POINT:
                mTvTitle.setText("포인트 - 통합형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_GROUP_POINT:
                mTvTitle.setText("포인트 - 그룹형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_SINGLE_POINT:
                mTvTitle.setText("포인트 - 단독형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_STEMP:
                mTvTitle.setText("스탬프 - 통합형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_GROUP_STEMP:
                mTvTitle.setText("스탬프 - 그룹형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_SINGLE_STEMP:
                mTvTitle.setText("스탬프 - 단독형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_GAME:
                mTvTitle.setText("가위바위보 - 통합형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_GROUP_GAME:
                mTvTitle.setText("가위바위보 - 그룹형");
                break;
            case TNConstants.MYPOINT_DETAIL_TYPE.TYPE_SINGLE_GAME:
                mTvTitle.setText("가위바위보 - 단독형");
                break;
        }

    }

//    private void requestMyPointDetail() {
//
////        Date currentTime = Calendar.getInstance().getTime();
////        //String year = new SimpleDateFormat("yyyy").format(currentTime);
////        String month = new SimpleDateFormat("MM").format(currentTime);
////
//////        mSpinnerMonth.setSelection(Integer.parseInt(month)-1);
////
////        String point_month = new SimpleDateFormat("yyyy-MM-01").format(currentTime);
////
////        ResReqMyPoint res = new ResReqMyPoint();
////        res.setParameter(
////                TNPreference.getMemphoneNumber(getActivity()),
////                point_month);
////
////        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
////        task.setOnHttpResultListener(this);
////        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
//    }

//    private void requestFranchiseePoint(String device_serial) {
//
//        ResReqFranchiseePoint res = new ResReqFranchiseePoint();
//        res.setParameter("20180101",
//                "000000",
//                "0",
//                device_serial,
//                "01094433244");
//
//        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
//        task.setOnHttpResultListener(this);
//        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
//
//    }

    @Override
    protected void initRequest() {
        CheckMyPointDetailListFragment listFragment = new CheckMyPointDetailListFragment();
        listFragment.setDetailType(mDetailType);
        replaceAnimationTagFragment(R.id.details_component, listFragment, CheckMyPointDetailListFragment.FRAGMENT_TAG, 0, 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        if (o[0] instanceof ResReqPoint) {
            ResReqPoint res = (ResReqPoint) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
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
        getActivity().finish();
    }
}
