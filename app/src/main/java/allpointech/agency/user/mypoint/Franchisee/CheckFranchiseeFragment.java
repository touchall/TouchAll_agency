package allpointech.agency.user.mypoint.Franchisee;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;
import com.tuna.utils.SLog;

import org.json.JSONArray;
import org.json.JSONObject;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResReqTelegram;
import allpointech.agency.utils.TNPreference;
import allpointech.agency.utils.aria.Aria;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckFranchiseeFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = CheckFranchiseeFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView mTvFranchiseeName;

    private TextView mTvPointTotalTotal;
    private TextView mTvPointTotalCanuse;
    private TextView mTvPointCanuse;
    private TextView mBtnPointUse;

    private TextView mTvStempTotalTotal;
    private TextView mTvStempTotalCanuse;
    private TextView mTvStempCanuse;
    private TextView mBtnStempUse;

    private TextView mTvGameTotalTotal;
    private TextView mTvGameTotalCanuse;
    private TextView mTvGameCanuse;
    private TextView mBtnGameUse;

    private String mJunmun;
//    private String mDeviceSerial;

    public CheckFranchiseeActivity mActivity;

    private JSONObject obj_message;

    private String mStoreName;
    private String mStoreType;

    private String mTelegram;

    int pointTotalTotal = 0;
    int pointTotalCanuse = 0;
    int pointCanuse = 0;

    int stempTotalTotal = 0;
    int stempTotalCanuse = 0;
    int stempCanuse = 0;

    int gameTotalTotal = 0;
    int gameTotalCanuse = 0;
    int gameCanuse = 0;

    private boolean _bTest = false;

    private final int EVENT_NFC_ON = 1;
    private final int EVENT_NFC_OFF = 2;

    private Handler mNFCHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case EVENT_NFC_ON:
                    if (mTelegram != null && mTelegram.length() > 0)
                    {
                        mActivity._enableNdefExchangeMode("user_point_info=" + mTelegram);
                        mActivity._enableTagWriteMode();
//                        new Handler().postDelayed(new Runnable()
//                        {
//                            @Override
//                            public void run()
//                            {
//                            //여기에 딜레이 후 시작할 작업들을 입력
//                            mActivity._disableNdef();
//                            new Handler().postDelayed(new Runnable()
//                            {
//                                @Override
//                                public void run() {
//                                    mActivity._enableNdefExchangeMode("user_point_info=" + mTelegram);
//                                    mActivity._enableTagWriteMode();
//                                }
//                            },300);
//                            }
//                        }, 100);// 0.5초 정도 딜레이를 준 후 시작
                    }
                    break;
                case EVENT_NFC_OFF:
//                    mActivity._disableNdef();
                    break;
            }
        }
    };


    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mJunmun = getArguments().getString("device_serial");
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_check_franchisee_point;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mTvFranchiseeName = (TextView) view.findViewById(R.id.text_franchisee_name);

        mTvPointTotalTotal = (TextView) view.findViewById(R.id.text_point_total_total);
        mTvPointTotalCanuse = (TextView) view.findViewById(R.id.text_point_total_canuse);
        mTvPointCanuse = (TextView) view.findViewById(R.id.text_point_canuse);
        mBtnPointUse = (TextView) view.findViewById(R.id.btn_point_use);
        mBtnPointUse.setOnClickListener(this);

        mTvStempTotalTotal = (TextView) view.findViewById(R.id.text_stemp_total_total);
        mTvStempTotalCanuse = (TextView) view.findViewById(R.id.text_stemp_total_canuse);
        mTvStempCanuse = (TextView) view.findViewById(R.id.text_stemp_canuse);
        mBtnStempUse = (TextView) view.findViewById(R.id.btn_stemp_use);
        mBtnStempUse.setOnClickListener(this);

        mTvGameTotalTotal = (TextView) view.findViewById(R.id.text_game_total_total);
        mTvGameTotalCanuse = (TextView) view.findViewById(R.id.text_game_total_canuse);
        mTvGameCanuse = (TextView) view.findViewById(R.id.text_game_canuse);
        mBtnGameUse = (TextView) view.findViewById(R.id.btn_game_use);
        mBtnGameUse.setOnClickListener(this);
    }

    private void requestFranchiseePoint(String device_serial) {

//        ResReqFranchiseePoint res = new ResReqFranchiseePoint();
//        res.setToken(TNPreference.getMemToken(getActivity()));
//        res.setParameterSerial(device_serial);
        ResReqTelegram res = new ResReqTelegram();
        res.setToken(TNPreference.getMemToken(getActivity()));
        res.setParameterMobile(TNPreference.getMemphoneNumber(getActivity()));
        res.setParameterTelegram(device_serial);
        res.setParameterRps("N");

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);

    }

    @Override
    protected void initRequest() {
        mActivity = (CheckFranchiseeActivity)getActivity();
        requestFranchiseePoint(mJunmun);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s).replaceAll(" ", "0");
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {

        if (o[0] instanceof ResReqTelegram) {
            ResReqTelegram res = (ResReqTelegram) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {

                obj_message = JSONParser.getObject(obj, "message");
                if (obj_message != null) {
                    //mDeviceSerial = JSONParser.getString(obj_message, "serial");

                    mStoreName = JSONParser.getString(obj_message, "storeName");
                    mTvFranchiseeName.setText(mStoreName);

                    mStoreType = JSONParser.getString(obj_message, "earn");
                    mTelegram = JSONParser.getString(obj_message, "telegram");

                    JSONArray array_total = JSONParser.getArray(obj_message, "total");
                    JSONArray array_mall = JSONParser.getArray(obj_message, "mall");

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
                        }
                    }

                    for (int i = 0; i < array_mall.length(); i++) {
                        JSONObject obj_row = JSONParser.getArrayItem(array_mall, i);
                        if (obj_row != null) {
                            String kind = JSONParser.getString(obj_row, "kind");
                            String point = JSONParser.getString(obj_row, "point");

                            if (kind.equals("P")) {
                                pointCanuse = Integer.valueOf(point);
                            }
                            else if (kind.equals("S")) {
                                stempCanuse = Integer.valueOf(point);
                            }
                            else if (kind.equals("R")) {
                                gameCanuse = Integer.valueOf(point);
                            }
                        }
                    }
                }

                mTvPointTotalTotal.setText(String.valueOf(pointTotalTotal) + "P");
                mTvPointTotalCanuse.setText(String.valueOf(pointTotalCanuse) + "P");
                if (_bTest)
                    pointCanuse = 5000;
                mTvPointCanuse.setText(String.valueOf(pointCanuse) + "P");

                mTvStempTotalTotal.setText(String.valueOf(stempTotalTotal) + "개");
                mTvStempTotalCanuse.setText(String.valueOf(stempTotalCanuse) + "개");
                mTvStempCanuse.setText(String.valueOf(stempCanuse) + "개");

                mTvGameTotalTotal.setText(String.valueOf(gameTotalTotal) + "점");
                mTvGameTotalCanuse.setText(String.valueOf(gameTotalCanuse) + "점");
                mTvGameCanuse.setText(String.valueOf(gameCanuse) + "점");

                if (mTelegram != null && mTelegram.length() > 0) {
                    SLog.LogD("check franchisee telegram -> " + mTelegram);
                    Message msg = mNFCHandler.obtainMessage();
                    msg.what = EVENT_NFC_ON;
                    mNFCHandler.handleMessage(msg);
                }

                if (_bTest)
                {
                    byte[] bytes = hexStringToByteArray(mTelegram);

                    Aria aria = new Aria("KopYecatainUTa%4el82ro#$llcL&HAI");
                    //String decryt_junmun = aria.Decrypt(aria_junmun);
                    String decryt_junmun = aria.Decrypt(bytes);

                    SLog.LogD("ARIA dec message -> " + decryt_junmun);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_point_use: {
                if (pointCanuse == 0) {
                    showToast("가용 포인트가 부족합니다.");
                    break;
                }

                Message msg = mNFCHandler.obtainMessage();
                msg.what = EVENT_NFC_OFF;
                mNFCHandler.handleMessage(msg);

                CheckUseMyPointFragment mainFragment = (CheckUseMyPointFragment) getFragmentManager().findFragmentByTag(CheckUseMyPointFragment.FRAGMENT_TAG);
                if (mainFragment != null)
                    mainFragment.gotoUse(1, mStoreType, obj_message);
            }
                break;
            case R.id.btn_stemp_use: {
                if (stempCanuse == 0) {
                    showToast("가용 스탬프가 부족합니다.");
                    break;
                }

                Message msg = mNFCHandler.obtainMessage();
                msg.what = EVENT_NFC_OFF;
                mNFCHandler.handleMessage(msg);

                CheckUseMyPointFragment mainFragment = (CheckUseMyPointFragment) getFragmentManager().findFragmentByTag(CheckUseMyPointFragment.FRAGMENT_TAG);
                if (mainFragment != null)
                    mainFragment.gotoUse(2, mStoreType, obj_message);
            }
                break;
            case R.id.btn_game_use: {
                if (gameCanuse == 0) {
                    showToast("가용 게임이 부족합니다.");
                    break;
                }

                Message msg = mNFCHandler.obtainMessage();
                msg.what = EVENT_NFC_OFF;
                mNFCHandler.handleMessage(msg);

                CheckUseMyPointFragment mainFragment = (CheckUseMyPointFragment) getFragmentManager().findFragmentByTag(CheckUseMyPointFragment.FRAGMENT_TAG);
                if (mainFragment != null)
                    mainFragment.gotoUse(3, mStoreType, obj_message);
            }
                break;
        }
    }

    @Override
    public void onBack() {
        //replaceAnimationTagFragment(R.id.contents, new UserMainViewFragment(), UserMainViewFragment.FRAGMENT_TAG, 0, 0);
        Intent returnIntent = new Intent();
        //returnIntent.putExtra("junmun",msgs.get(0).toString());
        getActivity().setResult(Activity.RESULT_OK,returnIntent);
        getActivity().finish();
    }

//    public void checkFranchisee(String deviceSerial) {
//        mDeviceSerial = deviceSerial;
//        initRequest();
//    }
}
