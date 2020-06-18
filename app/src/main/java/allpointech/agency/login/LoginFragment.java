package allpointech.agency.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.gcm.GcmRegUtil;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResLogin;
//import allpointech.franchisee.tutorial.TutorialActivity;
import allpointech.agency.utils.TNConstants;
import allpointech.agency.utils.TNPreference;

/**
 * Created by daze on 2016-11-15.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener, TNHttpMultiPartTask.onHttpNetResultListener, BaseAppCompatActivity.onKeyBackPressedListener {
    public static final String FRAGMENT_TAG = LoginFragment.class.getSimpleName();

    private boolean _bTest = false;

    private TextInputEditText mEdPhoneNumber;
    private TextInputEditText mEdPassword;

    private TextView mBtnLogin;
    private TextView mBtnResetPw;
//    private TextView mBtnFind;

    @Override
    protected void BundleData(Bundle bundle) {
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        GcmRegUtil.getRegId(getActivity());
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initLayout(View view) {
        mEdPhoneNumber = (TextInputEditText) view.findViewById(R.id.edit_phonenumber);
        mEdPassword = (TextInputEditText) view.findViewById(R.id.edit_password);

        mBtnLogin = (TextView) view.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        mBtnLogin = (TextView) view.findViewById(R.id.btn_reset_password);
        mBtnLogin.setOnClickListener(this);

//        mBtnFind = (TextView) view.findViewById(R.id.btn_find);
//        mBtnFind.setOnClickListener(this);
//
//        mBtnJoin = (TextView) view.findViewById(R.id.btn_join);
//        mBtnJoin.setOnClickListener(this);

        String myNumber = null;
        TelephonyManager mgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        try{
            myNumber = mgr.getLine1Number();
            myNumber = myNumber.replace("+82", "0");

        }catch(Exception e){

        }

        if (myNumber != null && myNumber.length() > 0)
            mEdPhoneNumber.setText(myNumber);
    }

    @Override
    protected void initRequest() {

        TNPreference.setAgreement1(getActivity(), false);
        TNPreference.setAgreement2(getActivity(), false);
        TNPreference.setAgreement3(getActivity(), false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (onCheck()) {
                    requestLogin();
                }
                break;
            case R.id.btn_reset_password:
//                replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new FindPasswordFragment(), FindPasswordFragment.FRAGMENT_TAG, 0, 0);
                break;
//            case R.id.btn_join:
//                replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new JoinStep1Fragment(), JoinStep1Fragment.FRAGMENT_TAG, 0, 0);
//                break;
        }
    }

    private boolean onCheck() {
        if (mEdPhoneNumber.getText().length() == 0) {
            showToast(getString(R.string.msg_error_login_id));
            return false;
        }
        if (mEdPassword.getText().length() == 0) {
            showToast(getString(R.string.msg_error_login_pw));
            return false;
        }
        return true;
    }

    private void moveMain() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private void requestLogin() {

        if (_bTest) {
            //TNPreference.setUserInfo(getActivity(), obj_member);
            //TNPreference.setMemPw(getActivity(), mEdPassword.getText().toString());

//            TNPreference.setMemberID(getActivity(), "mb12341234");
//            TNPreference.setMemPhoneNumber(getActivity(), "01094433244");
//            TNPreference.setMemberType(getActivity(), member_type);
//            TNPreference.setMemName(getActivity(), member_name);
//            //TNPreference.setMemName(getActivity(), member_name);
//            TNPreference.setMemBirthDay(getActivity(), member_birthday);
//            TNPreference.setMemSex(getActivity(), member_sex);
//            TNPreference.setMemEMail(getActivity(), member_email1 + "@" + member_email2);
//            TNPreference.setMemPointType(getActivity(), member_pointtype);
//            TNPreference.setMemBankCode(getActivity(), member_bank_code);
//            TNPreference.setMemBankAccount(getActivity(), member_bank_account);
//            //TNPreference.setMemBankAccount(activity, member_bank_account);
            moveMain();
        }
        else {

            final String androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

            ResLogin res = new ResLogin();
            res.setParameter(
                    mEdPhoneNumber.getText().toString(),
                    mEdPassword.getText().toString(),
                    //"U",
                    "M", // 가맹점
                    GcmRegUtil.getRegId(getActivity()),
                    androidId
            );

            TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
            task.setOnHttpResultListener(this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
        }
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResLogin) {
            ResLogin res = (ResLogin) o[0];
            JSONObject object = res.getParseData();
            if (JSONParser.isSuccess(object)) {
                String obj_token = JSONParser.getString(object, ResLogin.KEY_RES.message);
                if (obj_token != null) {
                    //TNPreference.setUserInfo(getActivity(), obj_member);
                    TNPreference.setMemPhoneNumber(getActivity(), mEdPhoneNumber.getText().toString());
                    TNPreference.setMemPw(getActivity(), mEdPassword.getText().toString());
                    TNPreference.setMemToken(getActivity(), obj_token.toString());
                    moveMain();
                }
//                JSONObject obj_member = JSONParser.getObject(object, ResLogin.KEY_RES.message);
//                if (obj_member != null) {
//                    TNPreference.setUserInfo(getActivity(), obj_member);
//                    TNPreference.setMemPw(getActivity(), mEdPassword.getText().toString());
//                    moveMain();
//                }{"result":true,"message":"5b7a7abf04c036f5ad1f8a97a47e32ab"}
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TNConstants.REQUEST_CODE.REFRESH:
                if (resultCode == Activity.RESULT_OK) {
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
