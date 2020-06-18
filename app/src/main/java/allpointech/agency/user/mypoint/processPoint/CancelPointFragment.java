package allpointech.agency.user.mypoint.processPoint;

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
import allpointech.agency.network.http.resource.data.ResJuklib;
import allpointech.agency.network.http.resource.data.ResReqTelegram;
import allpointech.agency.utils.TNPreference;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CancelPointFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener {
    public static final String FRAGMENT_TAG = CancelPointFragment.class.getSimpleName();

    private Toolbar mToolbar;

    private TextView mTvCancelPointGuide;
    private TextView mTvCancelPointResult;

    private String mJunmun;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
        if (getArguments() != null) {
            mJunmun = getArguments().getString("cancel_point");
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_cancel_point;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mTvCancelPointGuide = (TextView) view.findViewById(R.id.tv_cancel_point_franchisee);
        mTvCancelPointResult = (TextView) view.findViewById(R.id.tv_cancel_point_result);
    }

    private void requestCancelPoint() {
//        String pay_date = mJunmun.substring(0, 8);
//        String pay_time = mJunmun.substring(8, 14);
//        String bonus_type = mJunmun.substring(14, 15);
//        String device_serial = mJunmun.substring(15, 29);
//        String cancel_type = mJunmun.substring(29, 30);
//        String phone_number = mJunmun.substring(30, 41);
//        String birth_day = mJunmun.substring(41, 49);
//        String pay_money = mJunmun.substring(49, 59);
//        String pay_type = mJunmun.substring(59, 60);
//        String pay_serial = mJunmun.substring(60, 77);
//        String cancel_point = mJunmun.substring(77, 82);
//        String cancel_stemp = mJunmun.substring(82, 84);
//        String cancel_game = mJunmun.substring(84, 86);
//        String pay_result = mJunmun.substring(86, 87);
//
//
////        ResCancelPoint res = new ResCancelPoint();
////        //res.setParameter(TNPreference.getMemIndex(getActivity()));
////        res.setParameter(pay_date,
////                pay_time,
////                bonus_type,
////                device_serial,
////                cancel_type,
////                TNPreference.getMemphoneNumber(getActivity()),
////                TNPreference.getMemBirthDay(getActivity()),
////                pay_money,
////                pay_type,
////                pay_serial,
////                cancel_point,
////                cancel_stemp,
////                cancel_game,
////                pay_result);
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
//
//        ResJuklib res = new ResJuklib();
//        res.setToken(TNPreference.getMemToken(getActivity()));
//        res.setParameter(TNPreference.getMemphoneNumber(getActivity()),
//                device_serial,
//                TNPreference.getMemberID(getActivity()),
//                pay_serial,
//                bonus_type,
//                cancel_point,
//                pay_money,
//                pay_type,
//                date_time,
//                "0"
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
        requestCancelPoint();
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

//        if (o[0] instanceof ResJuklib) {
//            ResJuklib res = (ResJuklib) o[0];
//            JSONObject obj = res.getParseData();
//            if (JSONParser.isSuccess(obj)) {
//                JSONObject obj_franchisee = JSONParser.getObject(obj, ResCancelPoint.KEY_RES.franchisee);
//                JSONObject obj_cancelPoint = JSONParser.getObject(obj, ResCancelPoint.KEY_RES.cancel_point);
//                String franchisee_type = JSONParser.getString(obj_franchisee, ResCancelPoint.KEY_RES.KEY_FRANCHISEE.untype);
//                String franchisee_name = JSONParser.getString(obj_franchisee, ResCancelPoint.KEY_RES.KEY_FRANCHISEE.unname);
//                String insert_point = JSONParser.getString(obj_cancelPoint, ResCancelPoint.KEY_RES.KEY_INSERTPOINT.mbocpt);
//                String insert_game = JSONParser.getString(obj_cancelPoint, ResCancelPoint.KEY_RES.KEY_INSERTPOINT.mbocrps);
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
//
//                //mTvFranchiseeName.setText(franchisee_name + "에서 제공한\n터치올 포인트(" + franchisee_type +")");
//                mTvCancelPointGuide.setText("감사합니다.   " +  franchisee_name + "\n에서 제공한 터치올 포인트(" + franchisee_type +")");
//                mTvCancelPointResult.setText("포인트   " + insert_point + " 포인트 취소 완료\n");
//            }
//        }

//        if (o[0] instanceof ResJuklib) {
//            ResJuklib res = (ResJuklib) o[0];
//            JSONObject obj = res.getParseData();
//            if (JSONParser.isSuccess(obj)) {
//
//                JSONObject obj_message = JSONParser.getObject(obj, ResJuklib.KEY_RES.message);
//                if (obj_message != null) {
//                    JSONObject receipt = JSONParser.getObject(obj_message, ResJuklib.KEY_RES.receipt);
//                    JSONObject mall = JSONParser.getObject(obj_message, ResJuklib.KEY_RES.mall);
//                    int rps = JSONParser.getInt(obj_message, ResJuklib.KEY_RES.rps, 0);
//                    int point = JSONParser.getInt(obj_message, ResJuklib.KEY_RES.point, 0);
//
//                    if (receipt != null) {
//                        String approval = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.approval);
//                        String type = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.type);
//                        int amount = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.amount, 0);
//                        int supply = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.supply, 0);
//                        int vat = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.vat, 0);
//                        String payType = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payType);
//                        String payDate = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payDate);
//                        String payTypeName = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payTypeName);
//
////                        String checkMsg = "사업자 명 : " + JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.name);
//////               checkMsg += "\n사업자 번호: XXXXX";
////                        checkMsg += ("\n판매일자: " + payDate);
////                        checkMsg += ("\n결제수단: " + payTypeName);
////                        checkMsg += ("\n결제금액: " + amount);
////                        checkMsg += ("\n부가세: " + vat);
////                        checkMsg += ("\n승인번호: " + approval);
////                        mTvInsertPointCheck.setText(checkMsg);
//                    }
//
//                    if (mall != null) {
//                        String name = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.name);
//                        String earn = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.earn);
//                        String rpsUse = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.rpsUse);
//                        String ssn = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.ssn);
//                        String address = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.address);
//                        String owner = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.owner);
//                        String leaflet = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.leaflet);
//
//                        //mTvFranchiseeName.setText(name);
//                        //mTvInsertPointType.setText("터치올 포인트 " + earn);
//
//                        mTvCancelPointGuide.setText("감사합니다.   " +  name + "\n에서 제공한 터치올 포인트(" + earn +")");
//                        mTvCancelPointResult.setText(point + " 포인트 취소 완료\n");
//                    }
//
////
////
////                    String str = "현금 900원 적립 완료";
////                    SpannableStringBuilder ssb = new SpannableStringBuilder(str);
////                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                    //mTvInsertPointResult.setText("현금 900원 적립 완료");
////                    mTvInsertPointResult.setText(ssb);
////                    setColorInPartitial("현금 ", String.valueOf(point), "원 적립 완료", "#FF0000", mTvInsertPointResult);
//
//                }
//            }
//        }


        if (o[0] instanceof ResReqTelegram) {
            ResReqTelegram res = (ResReqTelegram) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {

                JSONObject obj_message = JSONParser.getObject(obj, ResJuklib.KEY_RES.message);
                if (obj_message != null) {
                    JSONObject receipt = JSONParser.getObject(obj_message, ResJuklib.KEY_RES.receipt);
                    JSONObject mall = JSONParser.getObject(obj_message, ResJuklib.KEY_RES.mall);
                    int rps = JSONParser.getInt(obj_message, ResJuklib.KEY_RES.rps, 0);
                    int point = JSONParser.getInt(obj_message, ResJuklib.KEY_RES.point, 0);

                    if (receipt != null) {
                        String approval = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.approval);
                        String type = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.type);
                        int amount = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.amount, 0);
                        int supply = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.supply, 0);
                        int vat = JSONParser.getInt(receipt, ResJuklib.KEY_RES.RECEIPT_RES.vat, 0);
                        String payType = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payType);
                        String payDate = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payDate);
                        String payTypeName = JSONParser.getString(receipt, ResJuklib.KEY_RES.RECEIPT_RES.payTypeName);

//                        String checkMsg = "사업자 명 : " + JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.name);
////               checkMsg += "\n사업자 번호: XXXXX";
//                        checkMsg += ("\n판매일자: " + payDate);
//                        checkMsg += ("\n결제수단: " + payTypeName);
//                        checkMsg += ("\n결제금액: " + amount);
//                        checkMsg += ("\n부가세: " + vat);
//                        checkMsg += ("\n승인번호: " + approval);
//                        mTvInsertPointCheck.setText(checkMsg);
                    }

                    if (mall != null) {
                        String name = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.name);
                        String earn = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.earn);
                        String rpsUse = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.rpsUse);
                        String ssn = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.ssn);
                        String address = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.address);
                        String owner = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.owner);
                        String leaflet = JSONParser.getString(mall, ResJuklib.KEY_RES.MALL_RES.leaflet);

                        //mTvFranchiseeName.setText(name);
                        //mTvInsertPointType.setText("터치올 포인트 " + earn);

                        mTvCancelPointGuide.setText("감사합니다.   " +  name + "\n에서 제공한 터치올 포인트(" + earn +")");
                        mTvCancelPointResult.setText(point + " 포인트 취소 완료\n");
                    }

//
//
//                    String str = "현금 900원 적립 완료";
//                    SpannableStringBuilder ssb = new SpannableStringBuilder(str);
//                    ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    //mTvInsertPointResult.setText("현금 900원 적립 완료");
//                    mTvInsertPointResult.setText(ssb);
//                    setColorInPartitial("현금 ", String.valueOf(point), "원 적립 완료", "#FF0000", mTvInsertPointResult);

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

    }

    @Override
    public void onBack() {
        //replaceAnimationTagFragment(R.id.contents, new UserMainViewFragment(), UserMainViewFragment.FRAGMENT_TAG, 0, 0);

        getActivity().finish();
    }

    public void cancelPoint(String cancel_junmun) {
        mJunmun = cancel_junmun;
        requestCancelPoint();
    }
}
