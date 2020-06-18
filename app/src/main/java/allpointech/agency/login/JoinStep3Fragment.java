package allpointech.agency.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import allpointech.agency.R;
import allpointech.agency.network.gcm.GcmRegUtil;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResJoin;
import allpointech.agency.network.http.resource.data.ResStates;
import allpointech.agency.network.http.resource.data.ResSubStates;
import allpointech.agency.utils.TNPreference;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by daze on 2016-11-15.
 */

public class JoinStep3Fragment extends BaseFragment implements BaseAppCompatActivity.onKeyBackPressedListener, View.OnClickListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = JoinStep3Fragment.class.getSimpleName();

    private Toolbar mToolbar;

    private boolean _bTest = false;

    private TextInputEditText mEditEmail;
    private TextInputEditText mEditPassWord;
    private TextInputEditText mEditPassWord_1;
    //private Spinner mSpinerBanks;
    //private TextInputEditText mEditBankCount;

    private CheckBox mCheckBoxMale;
    private CheckBox mCheckBoxFemale;

    private Spinner mSpinerLiveState;
    private Spinner mSpinerLiveCity;

    private TextView mBtnJoin;

    //private ArrayList<JSONObject> mDataBanks = new ArrayList<>();
    //private ArrayList<JSONObject> mDataStates = new ArrayList<>();
    //private ArrayList<String> mDataStates = new ArrayList<>();

    ArrayList<String> spinnerStatesArray = new ArrayList<String>();
    ArrayList<String> spinnerCitysArray = new ArrayList<String>();

    private String mAddrState;
    private String mAddrCity;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        //GcmRegUtil.getRegId(getActivity());
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_join_step3;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mEditEmail = (TextInputEditText) view.findViewById(R.id.edit_email);
        mEditPassWord = (TextInputEditText) view.findViewById(R.id.edit_password);
        mEditPassWord_1 = (TextInputEditText) view.findViewById(R.id.edit_password_confirm);

        mCheckBoxMale = (CheckBox) view.findViewById(R.id.check_join_step3_male);
        mCheckBoxFemale = (CheckBox) view.findViewById(R.id.check_join_step3_female);

        mCheckBoxMale.setChecked(true);
        mCheckBoxFemale.setChecked(false);

        mCheckBoxMale.setOnClickListener(this);
        mCheckBoxFemale.setOnClickListener(this);


        mSpinerLiveState = (Spinner) view.findViewById(R.id.spinner_live_state);
        mSpinerLiveCity = (Spinner) view.findViewById(R.id.spinner_live_city);
        //mEditBankCount = (TextInputEditText) view.findViewById(R.id.edit_bank_account);

        mBtnJoin = (TextView) view.findViewById(R.id.btn_join);
        mBtnJoin.setOnClickListener(this);
    }

    //private void requestBanks() {
    private void requestAreas() {
        //통신
        //ResBankReq res = new ResBankReq();
        ResStates res = new ResStates();

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestSubcategory(String stateName) {
        ResSubStates res = new ResSubStates();
        res.setParameterQuestion(stateName);

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    protected void initRequest() {
        requestAreas();
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
            case R.id.action_ok:
                if (onCheck()) {
//                    requestJoin();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isValidPassword(String passwordhere, String confirmhere, List<String> errorList) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        errorList.clear();

        boolean flag=true;

        if (!passwordhere.equals(confirmhere)) {
            //errorList.add("password and confirm password does not match");
            errorList.add("비밀번호가 일치 하지 않습니다.");
            flag=false;
            return false;
        }
        if (passwordhere.length() < 8) {
            //errorList.add("Password lenght must have alleast 8 character !!");
            errorList.add("비밀번호가 적어도 8자 이상이어야 합니다.");
            flag=false;
            return false;
        }
        if (!specailCharPatten.matcher(passwordhere).find()) {
            //errorList.add("Password must have atleast one specail character !!");
            errorList.add("비밀번호가 적어도 하나의 특수문자를 포함 해야 합니다.");
            flag=false;
            return false;
        }
        if (!UpperCasePatten.matcher(passwordhere).find()) {
            //errorList.add("Password must have atleast one uppercase character !!");
            errorList.add("비밀번호가 적어도 하나의 대문자를 포함 해야 합니다.");
            flag=false;
            return false;
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            //errorList.add("Password must have atleast one lowercase character !!");
            errorList.add("비밀번호가 적어도 하나의 소문자를 포함 해야 합니다.");
            flag=false;
            return false;
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have atleast one digit character !!");
            flag=false;
        }

        return flag;

    }

    private boolean onCheck() {
        if (mEditEmail.getText().length() == 0) {
            //showToast(getString(R.string.msg_error_join_id));
            showToast("이메일을 입력 해주세요.");
            return false;
        }
//
//        String email = emailValidate.getText().toString().trim();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        emailValidate .addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//
//                if (email.matches(emailPattern) && s.length() > 0)
//                {
//                    Toast.makeText(getActivity(),"valid email address", Toast.LENGTH_SHORT).show();
//                    // or
//                    //textView.setText("valid email");
//                }
//                else
//                {
//                    Toast.makeText(getActivity(),"Invalid email address",Toast.LENGTH_SHORT).show();
//                    //or
//                    //textView.setText("invalid email");
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // other stuffs
//            }
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // other stuffs
//            }
//        });

        String email = mEditEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // onClick of button perform this simplest code.
        if (email.matches(emailPattern))
        {
            //Toast.makeText(getActivity(),"valid email address",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Toast.makeText(getActivity(),"Invalid email address", Toast.LENGTH_SHORT).show();
            showToast("이메일 형식이 올바르지 않습니다.");
            return false;
        }

        if (mEditPassWord.getText().length() == 0) {
            //showToast(getString(R.string.msg_error_join_pw_0));
            showToast("비밀번호를 입력 해주세요.");
            return false;
        }
        if (mEditPassWord_1.getText().length() == 0) {
            //showToast(getString(R.string.msg_error_join_pw_0));
            showToast("비밀번호 확인을 입력 해주세요.");
            return false;
        }

        //Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}");
        //Matcher mtch = p.matcher(mEditPassWord.getText());
        //
        //if(mtch.matches()){
        //    return true;
        //}
        //return false;

        List<String> errorMsgs = new ArrayList<String>();
        if (!isValidPassword(mEditPassWord.getText().toString(), mEditPassWord_1.getText().toString(), errorMsgs)) {
            //showToast(getString(R.string.msg_error_join_pw_1));
            showToast(errorMsgs.get(0));
            return false;
        }


        if (!mCheckBoxMale.isChecked() && !mCheckBoxFemale.isChecked()) {
            showToast("성별을 선택 해주세요.");
            return false;
        }


        if (mAddrState.length() <= 0) {
            showToast("시/도를 선택 해주세요.");
            return false;
        }

        if ((mAddrState != null && mAddrState.length() > 0)) {
            if (spinnerCitysArray.size() <= 0)
                mAddrCity = mAddrState;
            else if (spinnerCitysArray.size() == 1 && spinnerCitysArray.get(0).equals("구/군"))
                mAddrCity = mAddrState;
        }

        if (mAddrCity.length() <= 0) {
            showToast("구/군을 선택 해주세요.");
            return false;
        }

//        if (mCheckBoxCash.isChecked()) {
//            if (mEditBankCount.length() == 0) {
//                //showToast(getString(R.string.msg_error_join_country));
//                showToast("계좌 번호를 입력 해주세요.");
//                return false;
//            }
//        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

//            case R.id.check_join_step3_money_0:
//                mCheckBoxCash.setChecked(true);
//                mCheckBoxElecCash.setChecked(false);
//                mSpinerBanks.setEnabled(true);
//                mEditBankCount.setEnabled(true);
//                break;
//            case R.id.check_join_step3_money_1:
//                mCheckBoxCash.setChecked(false);
//                mCheckBoxElecCash.setChecked(true);
//                mSpinerBanks.setEnabled(false);
//                mEditBankCount.setEnabled(false);
//                break;

            case R.id.check_join_step3_male:
                mCheckBoxMale.setChecked(true);
                mCheckBoxFemale.setChecked(false);
                //mSpinerBanks.setEnabled(true);
                //mEditBankCount.setEnabled(true);
                break;
            case R.id.check_join_step3_female:
                mCheckBoxMale.setChecked(false);
                mCheckBoxFemale.setChecked(true);
                //mSpinerBanks.setEnabled(false);
                //mEditBankCount.setEnabled(false);
                break;

            case R.id.btn_join:
//                if (_bTest) {
//                    requestJoin();
//                }
//                else
                    {
                    if (onCheck()) {
                        requestJoin();
                    }
                }
                break;
        }
    }

    private void requestJoin() {
        ResJoin res = new ResJoin();
//        res.setParameter(
//                TNPreference.getMemName(getActivity()),
//                TNPreference.getMemBirthDay(getActivity()),
//                TNPreference.getMemphoneNumber(getActivity()),
//                mEditPassWord.getText().toString(),
//                mEditEmail.getText().toString(),
//                mCheckBoxMale.isEnabled() ? "Male" : "Female",
//                mAddrState,
//                mAddrCity,
//                GcmRegUtil.getRegId(getActivity()),
//                "A"
//        );

        final String androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        if (_bTest) {
            res.setParameter(
                    "01012341234",
                    "1234",
                    "홍길동",
                    "home@home.com",
                    "2001-01-01",
                    "서울 영등포구",
                    "M",
                    GcmRegUtil.getRegId(getActivity()),
                    androidId
            );
        }
        else {
            res.setParameter(
                    TNPreference.getMemphoneNumber(getActivity()),
                    mEditPassWord.getText().toString(),
                    TNPreference.getMemName(getActivity()),
                    mEditEmail.getText().toString(),
                    TNPreference.getMemBirthDay(getActivity()),
                    //mAddrState + " " + mAddrCity,
                    mAddrCity,
                    mCheckBoxMale.isChecked() ? "M" : "F",
                    GcmRegUtil.getRegId(getActivity()),
                    androidId
            );
        }

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResJoin) {
            ResJoin res = (ResJoin) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                showToast(getString(R.string.msg_success_join));
                //onBack();
                replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new LoginFragment(), LoginFragment.FRAGMENT_TAG, 0, 0);
            }
        }
        if (o[0] instanceof ResStates) {
            ResStates res = (ResStates) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResStates.KEY_RES.message);
                mSpinerLiveState.setAdapter(null);
                mSpinerLiveCity.setAdapter(null);

                mAddrState = "";
                mAddrCity = "";

                spinnerStatesArray.clear();
                spinnerCitysArray.clear();

                if (array != null && array.length() > 0) {
                    for (int i = 0 ; i < array.length() ; i++) {
                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //mDataStates.add(code_name);
                        spinnerStatesArray.add(code_name);
                    }

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinerLiveState,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            new HintAdapter<>(getActivity(), "시/도", spinnerStatesArray),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    String state_name = spinnerStatesArray.get(position);
                                    mAddrState = state_name;

                                    requestSubcategory(state_name);
                                }
                            });
                    hintSpinner.init();


                    spinnerCitysArray.clear();

                    //for (JSONObject obj : mDataStates)
//                    for (String sub_category : mDataStates)
//                    {
//                        String code_name = JSONParser.getString(obj, ResStates.KEY_RES.ROW.code_name);
//                        String sub_catatory = JSONParser.getString(obj, ResStates.KEY_RES.ROW.code_sub_catagory);
//
//                        if (sub_catatory.contains(state_name)) {
//                            spinnerCitysArray.add(code_name);
//                        }
//                    }

                    spinnerCitysArray.add("구/군");
                    //if (spinnerCitysArray.size() > 0)
                    {

                        HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                                mSpinerLiveCity,
                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                // your list data
                                //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                                new HintAdapter<>(getActivity(), "구/군", spinnerCitysArray),
                                new HintSpinner.Callback<String>() {
                                    @Override
                                    public void onItemSelected(int position, String itemAtPosition) {
                                        if (itemAtPosition.equals("구/군") == false) {
                                            String city_name = spinnerCitysArray.get(position);
                                            mAddrCity = city_name;
                                        }
                                    }
                                });
                        hintSpinner1.init();

                    }
                }
            }
        }

        if (o[0] instanceof ResSubStates) {
            ResSubStates res = (ResSubStates) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResStates.KEY_RES.message);
                mSpinerLiveCity.setAdapter(null);
                mAddrCity = "";

                spinnerCitysArray.clear();

                if (array != null && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        mDataStates.add(code_name);
                        spinnerCitysArray.add(code_name);
                    }


                    //spinnerCitysArray.add("구/군");
                    //if (spinnerCitysArray.size() > 0)
                }

                {
                    if (spinnerCitysArray.size() == 0)
                        spinnerCitysArray.add("구/군");
                    HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                            mSpinerLiveCity,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "구/군", spinnerCitysArray),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {
                                    if (itemAtPosition.equals("구/군") == false) {
                                        String city_name = spinnerCitysArray.get(position);
                                        mAddrCity = city_name;
                                    }
                                }
                            });
                    hintSpinner1.init();

                }
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
        //replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new LoginFragment(), LoginFragment.FRAGMENT_TAG, 0, 0);
        replaceAnimationTagFragment(((BaseAppCompatActivity) getActivity()).getMainViewId(), new JoinStep2Fragment(), JoinStep2Fragment.FRAGMENT_TAG, R.anim.layout_rightin, R.anim.layout_rightout);
    }


}
