package allpointech.agency.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;

import allpointech.agency.R;
import allpointech.agency.network.gcm.GcmRegUtil;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.utils.TNConstants;
import allpointech.agency.utils.TNPreference;

/**
 * Created by daze on 2016-11-15.
 */

public class JoinStep1Fragment extends BaseFragment implements BaseAppCompatActivity.onKeyBackPressedListener, View.OnClickListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = JoinStep1Fragment.class.getSimpleName();

    private Toolbar mToolbar;

    private CheckBox mChkBox_AgreeAll;

    private CheckBox mChkBox_Agree1_0;
    private CheckBox mChkBox_Agree1_1;

    private TextView mAgreeViewAll_0;

    private CheckBox mChkBox_Agree2_0;
    private CheckBox mChkBox_Agree2_1;

    private TextView mAgreeViewAll_1;

    private CheckBox mChkBox_Agree3_0;
    private CheckBox mChkBox_Agree3_1;

    private TextView mAgreeViewAll_2;

    //private TextView mBtnNext;
    private Button mBtnNext;

    private ArrayList<JSONObject> mDataList = new ArrayList<>();

    private String mCountryIdx = "";

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        GcmRegUtil.getRegId(getActivity());
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_join_step1;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mChkBox_AgreeAll = (CheckBox) view.findViewById(R.id.check_join_step1_yes_all);
        mChkBox_AgreeAll.setChecked(false);
        mChkBox_AgreeAll.setOnClickListener(this);

        mChkBox_Agree1_0 = (CheckBox) view.findViewById(R.id.check_join_step1_yes_0);
        mChkBox_Agree1_0.setChecked(false);
        mChkBox_Agree1_0.setOnClickListener(this);
        mChkBox_Agree1_1 = (CheckBox) view.findViewById(R.id.check_join_step1_no_0);
        mChkBox_Agree1_1.setChecked(true);
        mChkBox_Agree1_1.setOnClickListener(this);

        mAgreeViewAll_0 = (TextView) view.findViewById(R.id.btn_join_step1_viewall_0);
        mAgreeViewAll_0.setOnClickListener(this);


        mChkBox_Agree2_0 = (CheckBox) view.findViewById(R.id.check_join_step1_yes_1);
        mChkBox_Agree2_0.setChecked(false);
        mChkBox_Agree2_0.setOnClickListener(this);
        mChkBox_Agree2_1 = (CheckBox) view.findViewById(R.id.check_join_step1_no_1);
        mChkBox_Agree2_1.setChecked(true);
        mChkBox_Agree2_1.setOnClickListener(this);

        mAgreeViewAll_1 = (TextView) view.findViewById(R.id.btn_join_step1_viewall_1);
        mAgreeViewAll_1.setOnClickListener(this);

        mChkBox_Agree3_0 = (CheckBox) view.findViewById(R.id.check_join_step1_yes_2);
        mChkBox_Agree3_0.setChecked(false);
        mChkBox_Agree3_0.setOnClickListener(this);
        mChkBox_Agree3_1 = (CheckBox) view.findViewById(R.id.check_join_step1_no_2);
        mChkBox_Agree3_1.setChecked(true);
        mChkBox_Agree3_1.setOnClickListener(this);

        mAgreeViewAll_2 = (TextView) view.findViewById(R.id.btn_join_step1_viewall_2);
        mAgreeViewAll_2.setOnClickListener(this);

//        mBtnNext = (TextView) view.findViewById(R.id.btn_next);
//        mBtnNext.setOnClickListener(this);
        mBtnNext = (Button) view.findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(this);
    }

    @Override
    protected void initRequest() {

//        boolean bAgree1 = TNPreference.getAgreement1(getActivity());
//        boolean bAgree2 = TNPreference.getAgreement2(getActivity());
//        boolean bAgree3 = TNPreference.getAgreement3(getActivity());
//
//        mChkBox_Agree1_0.setChecked(bAgree1);
//        mChkBox_Agree1_1.setChecked(!bAgree1);
//
//        mChkBox_Agree2_0.setChecked(bAgree2);
//        mChkBox_Agree2_1.setChecked(!bAgree2);
//
//        mChkBox_Agree3_0.setChecked(bAgree3);
//        mChkBox_Agree3_1.setChecked(!bAgree3);
//
//        if (bAgree1 && bAgree2 && bAgree3) {
//            mChkBox_AgreeAll.setChecked(true);
//            mBtnNext.setEnabled (true);
//        }

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

    private boolean onCheck() {
        if (mChkBox_Agree1_0.isChecked() == false) {
            showToast("회원이용약관에 동의를 하세요.");
            return false;
        }
        if (mChkBox_Agree2_0.isChecked() == false) {
            showToast("개인 정보 수집 및 이용 동의를 하세요.");
            return false;
        }

        if (mChkBox_Agree3_0.isChecked() == false) {
            showToast("위치 기반 서비스 이용 동의를 하세요.");
            return false;
        }

        return (mChkBox_Agree1_0.isChecked() && mChkBox_Agree2_0.isChecked() && mChkBox_Agree3_0.isChecked()) ;
    }

    private void checkAgreeAll() {
        if (mChkBox_Agree1_0.isChecked() && mChkBox_Agree2_0.isChecked() && mChkBox_Agree3_0.isChecked()) {
            mChkBox_AgreeAll.setChecked(true);
        }
    }

    private void checkNext() {
        mBtnNext.setEnabled (mChkBox_Agree1_0.isChecked() && mChkBox_Agree2_0.isChecked() && mChkBox_Agree3_0.isChecked());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_join_step1_yes_all:
                mChkBox_AgreeAll.setChecked(true);

                mChkBox_Agree1_0.setChecked(true);
                mChkBox_Agree1_1.setChecked(false);

                mChkBox_Agree2_0.setChecked(true);
                mChkBox_Agree2_1.setChecked(false);

                mChkBox_Agree3_0.setChecked(true);
                mChkBox_Agree3_1.setChecked(false);
                break;

            case R.id.check_join_step1_yes_0:
                mChkBox_Agree1_0.setChecked(true);
                mChkBox_Agree1_1.setChecked(false);

                checkAgreeAll();
                break;
            case R.id.check_join_step1_no_0:
                mChkBox_AgreeAll.setChecked(false);
                mChkBox_Agree1_0.setChecked(false);
                mChkBox_Agree1_1.setChecked(true);
                break;

            case R.id.btn_join_step1_viewall_0: {
                Bundle b = new Bundle();
                b.putInt(TNConstants.USER_JUNMUN_TYPE.KEY_TYPE, TNConstants.USER_JUNMUN_TYPE.TYPE_USE_AGREE);
                Intent mIntent = new Intent(getActivity(), ShowAgreeActivity.class);
                mIntent.putExtras(b);
                startActivity(mIntent);
            }
                break;


            case R.id.check_join_step1_yes_1:
                mChkBox_Agree2_0.setChecked(true);
                mChkBox_Agree2_1.setChecked(false);

                checkAgreeAll();
                break;
            case R.id.check_join_step1_no_1:
                mChkBox_AgreeAll.setChecked(false);
                mChkBox_Agree2_0.setChecked(false);
                mChkBox_Agree2_1.setChecked(true);
                break;

            case R.id.btn_join_step1_viewall_1:{
                Bundle b = new Bundle();
                b.putInt(TNConstants.USER_JUNMUN_TYPE.KEY_TYPE, TNConstants.USER_JUNMUN_TYPE.TYPE_PERSONAL_AGREE);
                Intent mIntent = new Intent(getActivity(), ShowAgreeActivity.class);
                mIntent.putExtras(b);
                startActivity(mIntent);
            }
                break;


            case R.id.check_join_step1_yes_2:
                mChkBox_Agree3_0.setChecked(true);
                mChkBox_Agree3_1.setChecked(false);

                checkAgreeAll();
                break;
            case R.id.check_join_step1_no_2:
                mChkBox_AgreeAll.setChecked(false);
                mChkBox_Agree3_0.setChecked(false);
                mChkBox_Agree3_1.setChecked(true);
                break;

            case R.id.btn_join_step1_viewall_2:{
                Bundle b = new Bundle();
                b.putInt(TNConstants.USER_JUNMUN_TYPE.KEY_TYPE, TNConstants.USER_JUNMUN_TYPE.TYPE_POSITION_AGREE);
                Intent mIntent = new Intent(getActivity(), ShowAgreeActivity.class);
                mIntent.putExtras(b);
                startActivity(mIntent);
            }
            break;

            case R.id.btn_next:
                if (onCheck()) {

                    TNPreference.setAgreement1(getActivity(), true);
                    TNPreference.setAgreement2(getActivity(), true);
                    TNPreference.setAgreement3(getActivity(), true);

                    replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new JoinStep2Fragment(), JoinStep1Fragment.FRAGMENT_TAG, R.anim.layout_leftin, R.anim.layout_leftout);
                }
                else {

                }
                break;
        }

        checkNext();
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
    public void onBack() {
        replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new LoginFragment(), LoginFragment.FRAGMENT_TAG, 0, 0);
    }

    @Override
    public void onResume() {

        if (mChkBox_Agree1_0.isChecked() && mChkBox_Agree2_0.isChecked() && mChkBox_Agree3_0.isChecked() && mChkBox_AgreeAll.isChecked()) {
            TNPreference.setAgreement1(getActivity(), true);
            TNPreference.setAgreement2(getActivity(), true);
            TNPreference.setAgreement3(getActivity(), true);
        }
        else {

        }

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//
//        TNPreference.setAgreement1(getActivity(), false);
//        TNPreference.setAgreement2(getActivity(), false);
//        TNPreference.setAgreement3(getActivity(), false);
    }
}
