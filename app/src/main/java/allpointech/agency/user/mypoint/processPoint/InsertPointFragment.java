package allpointech.agency.user.mypoint.processPoint;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import allpointech.agency.network.http.resource.data.ResJuklib;
import allpointech.agency.network.http.resource.data.ResReqTelegram;
import allpointech.agency.network.http.resource.data.ResResponseMessage;
import allpointech.agency.utils.TNPreference;

/**
 * Created by jay on 2018. 6. 27..
 */

public class InsertPointFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = InsertPointFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView mTvInsertPointGuide;
    private TextView mTvFranchiseeName;
    private TextView mTvInsertPointType;
    private TextView mTvInsertPointResult;

    private TextView mTvInserPointGame;
    private TextView mTvInsertPointCheck;



    private String mJunmun;

    private boolean _bTest = false;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mJunmun = getArguments().getString("point_junmun");
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_insert_point;
    }

    private TextView setColorInPartitial(String pre_string, String string, String post_string, String color, TextView textView){
        textView.setText("");
        SpannableStringBuilder builder = new SpannableStringBuilder(pre_string + string + post_string);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), pre_string.length(), pre_string.length()+string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(builder);
        return textView;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle("포인트 적립");

        mTvFranchiseeName = (TextView) view.findViewById(R.id.tv_insert_point_franchisee);
        mTvInsertPointType = (TextView) view.findViewById(R.id.tv_insert_point_type);
        mTvInsertPointResult = (TextView) view.findViewById(R.id.tv_insert_point_result);

        mTvInserPointGame = (TextView) view.findViewById(R.id.tv_insert_point_game);

        mTvInsertPointCheck = (TextView) view.findViewById(R.id.tv_insert_point_check);

        if (_bTest) {
            mTvFranchiseeName.setText("더 카페");
            mTvInsertPointType.setText("터치올 포인트 [통합형]");
            String str = "현금 900원 적립 완료";
            SpannableStringBuilder ssb = new SpannableStringBuilder(str);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //mTvInsertPointResult.setText("현금 900원 적립 완료");
            mTvInsertPointResult.setText(ssb);

            String checkMsg = "사업자 명 : 더 카페";
            checkMsg += "\n사업자 번호: XXXXX";
            checkMsg += "\n판매일자: 2018-08-29 15:33";
            checkMsg += "\n결제수단: 현금 결제";
            checkMsg += "\n결제금액: 30,000원";
            checkMsg += "\n부가세: 3,000원";
            checkMsg += "\n승인번호: 0123456789";
            mTvInsertPointCheck.setText(checkMsg);
        }
    }

    private void requestInsertPoint() {
//        String pay_date = mJunmun.substring(0, 8);
//        String pay_time = mJunmun.substring(8, 14);
//        String bonus_type = mJunmun.substring(14, 15);
//        String device_serial = mJunmun.substring(15, 29);
//        String juklib_type = mJunmun.substring(15, 16);
//        String phone_number = mJunmun.substring(30, 41);
//        String birth_day = mJunmun.substring(41, 49);
//        String pay_money = mJunmun.substring(49, 59);
//        String pay_type = mJunmun.substring(59, 60);
//        String pay_serial = mJunmun.substring(60, 77);
//        String juklib_point = mJunmun.substring(77, 82);
//        String juklib_stemp = mJunmun.substring(82, 84);
//        String juklib_game = mJunmun.substring(84, 86);
//        String pay_result = mJunmun.substring(86, 87);
//
//        String date_time="0000-00-00 00:00:00";
//        String from = pay_date + pay_time;//"2013-04-08 10:10:10";
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        try {
//            Date to = transFormat.parse(from);
//            SimpleDateFormat transFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            date_time = transFormat1.format(to);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        //String to = transFormat.format(from);
//
//        ResJuklib res = new ResJuklib();
//        res.setToken(TNPreference.getMemToken(getActivity()));
////        res.setParameter(pay_date,
////                pay_time,
////                bonus_type,
////                device_serial,
//////                business_code,
////                TNPreference.getMemphoneNumber(getActivity()),
////                TNPreference.getMemBirthDay(getActivity()),
////                pay_money,
////                pay_type,
////                pay_serial,
////                juklib_point,
////                juklib_stemp,
////                juklib_game,
////                pay_result);
//
//        res.setParameter(TNPreference.getMemphoneNumber(getActivity()),
//                device_serial,
//                TNPreference.getMemberID(getActivity()),
//                pay_serial,
//                "1",
//                juklib_point,
//                pay_money,
//                pay_type,
//                date_time,
//                "0"
////                business_code,
////                TNPreference.getMemphoneNumber(getActivity()),
////                TNPreference.getMemBirthDay(getActivity()),
////                pay_money,
////                pay_type,
////                pay_serial,
////                juklib_point,
////                juklib_stemp,
////                juklib_game,
////                pay_result
//        );

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
        requestInsertPoint();
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

        if (o[0] instanceof ResReqTelegram) {
            ResReqTelegram res = (ResReqTelegram) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {

                JSONObject obj_message = JSONParser.getObject(obj, ResResponseMessage.KEY_RES.message);
                if (obj_message != null) {
                    JSONObject receipt = JSONParser.getObject(obj_message, ResResponseMessage.KEY_RES.receipt);
                    JSONObject mall = JSONParser.getObject(obj_message, ResResponseMessage.KEY_RES.mall);
                    String deuplicate = JSONParser.getString(obj_message, ResResponseMessage.KEY_RES.duplicate);
                    int rps = JSONParser.getInt(obj_message, ResResponseMessage.KEY_RES.rps, 0);
                    int point = JSONParser.getInt(obj_message, ResResponseMessage.KEY_RES.point, 0);
                    String telegram = JSONParser.getString(obj_message, ResResponseMessage.KEY_RES.telegram);

                    if (receipt != null) {
                        String approval = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.approval);
                        String type = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.type);
                        int amount = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.amount, 0);
                        int supply = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.supply, 0);
                        int vat = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.vat, 0);
                        String payType = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payType);
                        String payDate = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payDate);
                        String payTypeName = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payTypeName);

                        String checkMsg = "사업자 명 : " + JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.name);
                        checkMsg += ("\n판매일자: " + payDate);
                        checkMsg += ("\n결제수단: " + payTypeName);
                        checkMsg += ("\n결제금액: " + amount);
                        checkMsg += ("\n부가세: " + vat);
                        checkMsg += ("\n승인번호: " + approval);
                        mTvInsertPointCheck.setText(checkMsg);
                    }

                    if (mall != null) {
                        String name = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.name);
                        String earn = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.earn);
                        String rpsUse = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.rpsUse);
                        String ssn = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.ssn);
                        String address = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.address);
                        String owner = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.owner);
                        String leaflet = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.leaflet);

                        mTvFranchiseeName.setText(name);
                        mTvInsertPointType.setText("터치올 포인트 " + earn);
                    }
                    setColorInPartitial("현금 ", String.valueOf(point), "원 적립 완료", "#FF0000", mTvInsertPointResult);

                    mToolbar.setTitle("포인트 적립 승인 완료");
                }
            }
//            else {
//                mToolbar.setTitle("포인트 적립 승인 실패");
//            }
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


    public void insertPoint(String point_junmun) {
        mJunmun = point_junmun;
        requestInsertPoint();
    }

}
