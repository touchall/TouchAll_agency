package allpointech.agency.user.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import allpointech.agency.R;
import allpointech.agency.dialog.ProgressDialog;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.TNHttpMultiPartTaskEx;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResCode;
import allpointech.agency.network.http.resource.data.ResInfo;
import allpointech.agency.network.http.resource.data.ResJuklib;
import allpointech.agency.network.http.resource.data.ResReqPoint;
import allpointech.agency.setting.SettingActivity;
import allpointech.agency.user.UserMainFragment;
import allpointech.agency.utils.TNConstants;
import allpointech.agency.utils.TNPreference;

/**
 * Created by jay on 2018. 6. 19..
 */

public class UserMainViewFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener, TNHttpMultiPartTaskEx.onHttpNetResultListener, TNHttpMultiPartTask.onHttpNetFailResultListener {
    public static final String FRAGMENT_TAG = UserMainViewFragment.class.getSimpleName();

    private Toolbar mToolbar;

//    private ViewPagerIndicator mIndicator;
//    private ViewPager mAdvertisementPager;
//    private UserMainViewFragment.AdvertisementAdapter mAdapter;
//    private ArrayList<JSONObject> mDataList = new ArrayList<>();

//    private ImageView mBtnHelp;
//    private ImageView mBtnFindStore;
//    private ImageView mBtnCheckMyPoint;
//    private ImageView mBtnUseMyPoint;
//    private ImageView mBtnNotice;
//    private ImageView mBtnEvent;

    private TextView mTvHello;

    private ToggleButton mBtnTotalPoint;
    private ToggleButton mBtnCanusePoint;

    private TextView mTvPoint;
    private TextView mTvStemp;
    private TextView mTvGame;

    private TextView mBtnIntro;
    private TextView mBtnFindStore;
    private TextView mBtnChecUsekMyPoint;
    private TextView mBtnMyPage;
    private TextView mBtnEvent;
    private TextView mBtnCustomer;

    private TextView mBtnEmulation;

    int pointTotalTotal = 0;
    int pointTotalCanuse = 0;
//    int pointCanuse = 0;

    int stempTotalTotal = 0;
    int stempTotalCanuse = 0;
//    int stempCanuse = 0;

    int gameTotalTotal = 0;
    int gameTotalCanuse = 0;
//    int gameCanuse = 0;

    int emulationCount = 100;
    private ProgressDialog mProgress;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_main_view;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

//        mIndicator = (ViewPagerIndicator) view.findViewById(R.id.pager_indicator);
//        mAdvertisementPager = (ViewPager) view.findViewById(R.id.view_pager_advertise);
//        mAdapter = new UserMainViewFragment.AdvertisementAdapter(getFragmentManager());
//        mAdvertisementPager.setAdapter(mAdapter);

//        mBtnHelp = (ImageView) view.findViewById(R.id.btn_help);
//        mBtnHelp.setOnClickListener(this);
//        mBtnFindStore = (ImageView) view.findViewById(R.id.btn_find_store);
//        mBtnFindStore.setOnClickListener(this);
//        mBtnCheckMyPoint = (ImageView) view.findViewById(R.id.btn_check_mypoint);
//        mBtnCheckMyPoint.setOnClickListener(this);
//        mBtnUseMyPoint = (ImageView) view.findViewById(R.id.btn_use_mypoint);
//        mBtnUseMyPoint.setOnClickListener(this);
//        mBtnNotice = (ImageView) view.findViewById(R.id.btn_notice);
//        mBtnNotice.setOnClickListener(this);
//        mBtnEvent = (ImageView) view.findViewById(R.id.btn_event);
//        mBtnEvent.setOnClickListener(this);

        mTvHello = (TextView) view.findViewById(R.id.text_main_hello);

        mBtnTotalPoint = (ToggleButton) view.findViewById(R.id.button_total_point);
        mBtnTotalPoint.setOnClickListener(this);
        mBtnCanusePoint = (ToggleButton) view.findViewById(R.id.button_canuse_point);
        mBtnCanusePoint.setOnClickListener(this);

        mBtnTotalPoint.setChecked(true);
        mBtnCanusePoint.setChecked(false);

        mTvPoint = (TextView) view.findViewById(R.id.text_user_point);
        mTvStemp = (TextView) view.findViewById(R.id.text_user_stemp);
        mTvGame = (TextView) view.findViewById(R.id.text_user_game);
        mTvGame.setOnClickListener(this);

        mBtnIntro = (TextView) view.findViewById(R.id.btn_intro);
        mBtnIntro.setOnClickListener(this);
        mBtnFindStore = (TextView) view.findViewById(R.id.btn_find_store);
        mBtnFindStore.setOnClickListener(this);
        mBtnChecUsekMyPoint = (TextView) view.findViewById(R.id.btn_check_use_mypoint);
        mBtnChecUsekMyPoint.setOnClickListener(this);
        mBtnMyPage = (TextView) view.findViewById(R.id.btn_mypage);
        mBtnMyPage.setOnClickListener(this);
        mBtnEvent = (TextView) view.findViewById(R.id.btn_event);
        mBtnEvent.setOnClickListener(this);
        mBtnCustomer = (TextView) view.findViewById(R.id.btn_cusomer);
        mBtnCustomer.setOnClickListener(this);

        mBtnEmulation = (TextView) view.findViewById(R.id.tv_copyright);
        mBtnEmulation.setOnClickListener(this);
    }


    private void requestMain() {
        ResInfo res = new ResInfo();
        res.setToken(TNPreference.getMemToken(getActivity()));

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.setOnHttpFailResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestMyPoint() {
        ResReqPoint res = new ResReqPoint();
        res.setToken(TNPreference.getMemToken(getActivity()));

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.setOnHttpFailResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestCode() {
        ResCode res = new ResCode();
        //res.setToken(TNPreference.getMemToken(getActivity()));

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.setOnHttpFailResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    public String getUID() {

        String serial_id;

        final String androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        serial_id = androidId;
        serial_id += (System.currentTimeMillis());
        serial_id = serial_id.replaceAll("[^0-9]", "");

//        serial_id = "235623434";

        if (serial_id.length() > 17) {
            //serial_id = serial_id.substring(0, 17);
        }
        else
        {
            int DIGITS = 17;
            StringBuilder sb = new StringBuilder(DIGITS);
            for(int i = 0;i < DIGITS;i++) {
                //sb.append((char) (Math.random() * 10 + '0'));
                if (i >= DIGITS - serial_id.length()) {
                    //    sb.append(serial_id.charAt(i-serial_id.length()));
                    break;
                }
                else
                    sb.append('0');
            }
            serial_id = sb.toString() + serial_id;
        }

        return serial_id;
//
//        if (serial_id.length() > 17)
//            return serial_id.substring(0, 17);
//        else {
//            int DIGITS = 17;
//            StringBuilder sb = new StringBuilder(DIGITS);
//            for(int i = 0;i < DIGITS;i++) {
//                //sb.append((char) (Math.random() * 10 + '0'));
//
//            }
//            return sb.toString();
//        }
    }

    private void requestEmulation() {

        //for (int i = 0 ; i < 100 ; i++)
        {
            String pay_money = "0000030000";//mJunmun.substring(49, 59);
            String pay_type = "0";//mJunmun.substring(59, 60);
            String pay_serial = getUID();//mJunmun.substring(60, 77);
            String juklib_point = "00900";//mJunmun.substring(77, 82);

            // 시스템으로부터 현재시간(ms) 가져오기
            long now = System.currentTimeMillis();
            // Data 객체에 시간을 저장한다.
            Date date = new Date(now);
            // 각자 사용할 포맷을 정하고 문자열로 만든다.
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date_time = sdfNow.format(date);


            ResJuklib res = new ResJuklib();
            res.setToken(TNPreference.getMemToken(getActivity()));

            res.setParameter(TNPreference.getMemphoneNumber(getActivity()),
                    "KR201808100003",
                    TNPreference.getMemberID(getActivity()),
                    pay_serial,
                    "1",
                    juklib_point,
                    pay_money,
                    pay_type,
                    date_time,
                    "0"
            );

            TNHttpMultiPartTaskEx task = new TNHttpMultiPartTaskEx(getActivity(), getFragmentManager());
            task.setOnHttpResultListener(this);
            task.setDialogEnabled(true);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
        }
    }

    @Override
    protected void initRequest() {
        requestMain();
        requestMyPoint();
        requestCode();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.user_setting, menu);
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

            case R.id.action_setting:
            {
                Intent i = new Intent(getActivity(), SettingActivity.class);
//                i.putExtra(TNConstants.UserSettingType.TYPE, 5);
                //startActivityForResult(i, TNConstants.REQUEST_CODE.SECESSION);
                startActivity(i);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResInfo) {
            ResInfo res = (ResInfo) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONObject obj_info = JSONParser.getObject(obj, ResInfo.KEY_RES.message);
                if (obj_info != null) {
                    String id = JSONParser.getString(obj_info, "id");
                    TNPreference.setMemberID(getActivity(), id);
                    String name = JSONParser.getString(obj_info, "name");
                    TNPreference.setMemName(getActivity(), name);
                    String mobile = JSONParser.getString(obj_info, "mobile");
                    TNPreference.setMemPhoneNumber(getActivity(), mobile);
                    String email = JSONParser.getString(obj_info, "email");
                    TNPreference.setMemEMail(getActivity(), email);
                    String area = JSONParser.getString(obj_info, "area");
                    TNPreference.setMemAddr(getActivity(), area);
                    String gender = JSONParser.getString(obj_info, "gender");
                    TNPreference.setMemSex(getActivity(), gender);
                    int level = JSONParser.getInt(obj_info, "level", 0);
                    TNPreference.setMemGrade(getActivity(), String.valueOf(level));
                    String duplicate = JSONParser.getString(obj_info, "duplicate");
                    //TNPreference.setMemName(getActivity(), name);
                    String  certification = JSONParser.getString(obj_info, "certification");
                    //TNPreference.setMemName(getActivity(), name);
                    String status = JSONParser.getString(obj_info, "status");
                    //TNPreference.setMemName(getActivity(), name);
                    Long createdAt = JSONParser.getLong(obj_info, "createdAt", 0L);
                    //TNPreference.setMemName(getActivity(), name);
                    String genderName = JSONParser.getString(obj_info, "genderName");
                    TNPreference.setMemGenderName(getActivity(), genderName);
                    String statusName = JSONParser.getString(obj_info, "statusName");
                    //TNPreference.setMemName(getActivity(), name);
                    String birth = JSONParser.getString(obj_info, "birth");
                    TNPreference.setMemBirthDay(getActivity(), birth);

                    String szHello = String.valueOf(level) + "학년 " + name + "님 안녕하세요";
                    mTvHello.setText(szHello);
                }
            }
        }

        if (o[0] instanceof ResReqPoint) {
            ResReqPoint res = (ResReqPoint) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {

                JSONObject obj_message = JSONParser.getObject(obj, "message");
                if (obj_message != null) {
                    JSONArray array_total = JSONParser.getArray(obj_message, "total");   // 발생 포인트,
                    for (int i = 0; i < array_total.length(); i++) {
                        JSONObject obj_row = JSONParser.getArrayItem(array_total, i);
                        if (obj_row != null) {
                            String total = JSONParser.getString(obj_row, "total");
                            String kind = JSONParser.getString(obj_row, "kind");
                            String available = JSONParser.getString(obj_row, "available");

                            if (kind.equals("P")) {
                                if (total != null && total.length() > 0)
                                    pointTotalTotal = Integer.valueOf(total);
                                if (available != null && available.length() > 0)
                                    pointTotalCanuse = Integer.valueOf(available);
                            }
                            else if (kind.equals("S")) {
                                if (total != null && total.length() > 0)
                                    stempTotalTotal = Integer.valueOf(total);
                                if (available != null && available.length() > 0)
                                    stempTotalCanuse = Integer.valueOf(available);
                            }
                            else if (kind.equals("R")) {
                                if (total != null && total.length() > 0)
                                    gameTotalTotal = Integer.valueOf(total);
                                if (available != null && available.length() > 0)
                                    gameTotalCanuse = Integer.valueOf(available);
                            }

                            if (mBtnTotalPoint.isChecked()) {
                                mTvPoint.setText("포인트 " + String.valueOf(pointTotalTotal) + "P");
                                mTvStemp.setText("스탬프 " + String.valueOf(stempTotalTotal) + "개");
                                mTvGame.setText("경품승점 " + String.valueOf(gameTotalTotal) + "점");
                            }
                            else {
                                mTvPoint.setText("포인트 " + String.valueOf(pointTotalCanuse) + "P");
                                mTvStemp.setText("스탬프 " + String.valueOf(stempTotalCanuse) + "개");
                                mTvGame.setText("경품승점 " + String.valueOf(gameTotalCanuse) + "점");
                            }
                        }
                    }

//                    JSONArray array_occur = JSONParser.getArray(obj_message, "occur");   // 발생 포인트,
//                    JSONArray array_available = JSONParser.getArray(obj_message, "available");  // 가용 포인트
//                    for (int i = 0; i < array_occur.length(); i++) {
//
//                        JSONObject obj_row = JSONParser.getArrayItem(array_occur, i);
//                        if (obj_row != null) {
//                            String kind = JSONParser.getString(obj_row, "kind");
//                            String type = JSONParser.getString(obj_row, "earn");
//                            JSONArray array_points = JSONParser.getArray(obj_row, "points");
//                            if (array_points != null && array_points.length() > 0) {
//                                for (int idx = 0; idx < array_points.length(); idx++) {
//                                    JSONObject obj_point = JSONParser.getArrayItem(array_points, idx);
//                                    if (obj_point != null) {
//                                        String point_kind = JSONParser.getString(obj_point, "kind");
//                                        String point_type = JSONParser.getString(obj_point, "earn");
//                                        String point_point = JSONParser.getString(obj_point, "point");
//
//                                        if (point_kind.equals("P"))
//                                            pointTotalTotal += Integer.valueOf(point_point);
//                                        else if (point_kind.equals("S"))
//                                            stempTotalTotal += Integer.valueOf(point_point);
//                                        else if (point_kind.equals("R"))
//                                            gameTotalTotal += Integer.valueOf(point_point);
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//
//                    for (int i = 0; i < array_available.length(); i++) {
//
//                        JSONObject obj_row = JSONParser.getArrayItem(array_available, i);
//                        if (obj_row != null) {
//                            String kind = JSONParser.getString(obj_row, "kind");
//                            String type = JSONParser.getString(obj_row, "earn");
//                            JSONArray array_points = JSONParser.getArray(obj_row, "points");
//                            if (array_points != null && array_points.length() > 0) {
//                                for (int idx = 0; idx < array_points.length(); idx++) {
//                                    JSONObject obj_point = JSONParser.getArrayItem(array_points, idx);
//                                    if (obj_point != null) {
//                                        String point_kind = JSONParser.getString(obj_point, "kind");
//                                        String point_type = JSONParser.getString(obj_point, "earn");
//                                        String point_point = JSONParser.getString(obj_point, "point");
//
//                                        if (point_kind.equals("P"))
//                                            pointTotalCanuse += Integer.valueOf(point_point);
//                                        else if (point_kind.equals("S"))
//                                            stempTotalCanuse += Integer.valueOf(point_point);
//                                        else if (point_kind.equals("R"))
//                                            gameTotalCanuse += Integer.valueOf(point_point);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//
//                if (mBtnTotalPoint.isChecked()) {
//                    //mTvPoint.setText("포인트 " + String.valueOf(pointTotalTotal) + "P");
//                    mTvStemp.setText("스탬프 " + String.valueOf(stempTotalTotal) + "개");
//                    mTvGame.setText("경품승점 " + String.valueOf(gameTotalTotal) + "점");
//                }
//                else {
//                    //mTvPoint.setText("포인트 " + String.valueOf(pointTotalCanuse) + "P");
//                    mTvStemp.setText("스탬프 " + String.valueOf(stempTotalCanuse) + "개");
//                    mTvGame.setText("경품승점 " + String.valueOf(gameTotalCanuse) + "점");
                }
            }
        }

        if (o[0] instanceof ResCode) {
            ResCode res = (ResCode) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {

                JSONObject obj_message = JSONParser.getObject(obj, "message");
                if (obj_message != null) {

                }
            }
        }

        if (o[0] instanceof ResJuklib) {
//            ResJuklib res = (ResJuklib) o[0];
//            JSONObject obj = res.getParseData();
//            if (JSONParser.isSuccess(obj)) {
//
//                JSONObject obj_message = JSONParser.getObject(obj, "message");
//                if (obj_message != null) {
//
//                }
//            }

            emulationCount--;
            if (emulationCount > 0) {
                requestEmulation();
            }
            else {
                try {
                    mProgress.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requestMyPoint();
            }
        }
    }

    @Override
    public void onHttpDebugEvent(String debug_msg) {

    }

    @Override
    public void onHttpNetFailEvent(int errorCode, String errorMsg) {
//
//        emulationCount--;
//        if (emulationCount > 0)
//            requestEmulation();
//        else {
//            try {
//                mProgress.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            requestMyPoint();
//        }

    }

    @Override
    public void onHttpNetFailResultListener(BaseHttpResource resource) {

        if (resource instanceof ResJuklib) {
//            ResJuklib res = (ResJuklib) o[0];
//            JSONObject obj = res.getParseData();
//            if (JSONParser.isSuccess(obj)) {
//
//                JSONObject obj_message = JSONParser.getObject(obj, "message");
//                if (obj_message != null) {
//
//                }
//            }

            emulationCount--;
            if (emulationCount > 0) {
                requestEmulation();
            }
            else {
                try {
                    mProgress.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requestMyPoint();
            }
        }

    }



    @Override
    public void onClick(View view) {

        UserMainFragment mainFragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);

        switch (view.getId()) {
            case R.id.button_total_point: {
                mBtnTotalPoint.setChecked(true);
                mBtnCanusePoint.setChecked(false);

                if (mBtnTotalPoint.isChecked()) {
                    mTvPoint.setText("포인트 " + String.valueOf(pointTotalTotal) + "P");
                    mTvStemp.setText("스탬프 " + String.valueOf(stempTotalTotal) + "개");
                    mTvGame.setText("경품승점 " + String.valueOf(gameTotalTotal) + "점");
                }
                else {
                    mTvPoint.setText("포인트 " + String.valueOf(pointTotalCanuse) + "P");
                    mTvStemp.setText("스탬프 " + String.valueOf(stempTotalCanuse) + "개");
                    mTvGame.setText("경품승점 " + String.valueOf(gameTotalCanuse) + "점");
                }
            }
                break;
            case R.id.button_canuse_point: {
                mBtnTotalPoint.setChecked(false);
                mBtnCanusePoint.setChecked(false);

                if (mBtnTotalPoint.isChecked()) {
                    mTvPoint.setText("포인트 " + String.valueOf(pointTotalTotal) + "P");
                    mTvStemp.setText("스탬프 " + String.valueOf(stempTotalTotal) + "개");
                    mTvGame.setText("경품승점 " + String.valueOf(gameTotalTotal) + "점");
                }
                else {
                    mTvPoint.setText("포인트 " + String.valueOf(pointTotalCanuse) + "P");
                    mTvStemp.setText("스탬프 " + String.valueOf(stempTotalCanuse) + "개");
                    mTvGame.setText("경품승점 " + String.valueOf(gameTotalCanuse) + "점");
                }
            }
                break;
            case R.id.btn_intro:
                if (mainFragment != null) {
                    mainFragment.onGoTouchallIntroduce(TNConstants.WEBVIEW.TYPE_INTRO_TOUCHALL);
                }
                break;
            case R.id.btn_find_store:
                if (mainFragment != null) {
                    mainFragment.onGoFindStore();
                }
                break;
            case R.id.btn_check_use_mypoint:
                if (mainFragment != null) {
                    mainFragment.onGoFindStore();
                }
                break;
            case R.id.btn_mypage:
                if (mainFragment != null) {
                    mainFragment.onGoMyBonus();
                }
                break;
            case R.id.btn_event:
                if (mainFragment != null) {
                    mainFragment.onGoTouchallIntroduce(TNConstants.WEBVIEW.TYPE_EVENT);
                }
                break;
            case R.id.btn_cusomer:
                if (mainFragment != null) {
                    mainFragment.onGoTouchallIntroduce(TNConstants.WEBVIEW.TYPE_NOTICE);
                }
                break;

            case R.id.text_user_game:
            {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub firstWork(getApplicationContext());
//                        requestEmulation();
//                        }
//                }).start();

                emulationCount = 100;


        try {
            mProgress = new ProgressDialog();
            //if (isDialogEnable) {
                try {
                    mProgress.show(getFragmentManager(), "dialog_progress");
//                    if (isTransparent)
//                        mProgress.setTransparent(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
           // }
        } catch (Exception e) {
            e.printStackTrace();
        }
                requestEmulation();
            }
                break;
        }
    }

    @Override
    public void onBack() {
        UserMainFragment fragment = (UserMainFragment) getFragmentManager().findFragmentByTag(UserMainFragment.FRAGMENT_TAG);
        if (fragment != null) {
            fragment.onBack();
        }
    }

    public void onReflash() {
        requestMain();
        requestMyPoint();
        requestCode();
    }
}
