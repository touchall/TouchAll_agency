package allpointech.agency.user.mypoint;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.tuna.ui.fragment.BaseDialogFragment;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import allpointech.agency.R;
import allpointech.agency.dialog.SpinnerDateDialog;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResCitys;
import allpointech.agency.network.http.resource.data.ResFindMyPoint;
import allpointech.agency.network.http.resource.data.ResReqPoint;
import allpointech.agency.network.http.resource.data.ResSector;
import allpointech.agency.network.http.resource.data.ResSubCitys;
import allpointech.agency.network.http.resource.data.ResSubSector;
import allpointech.agency.utils.TNPreference;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckMyPointListFragment extends BaseFragment implements TNHttpMultiPartTask.onHttpNetResultListener, CheckMyPointAdapter.OnMyPointClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public static final String FRAGMENT_TAG = CheckMyPointListFragment.class.getSimpleName();

    //private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private RecyclerViewHeader recyclerHeader;
    private CheckMyPointAdapter mAdapter;
    private ArrayList<JSONObject> mDataList = new ArrayList<>();
    private ArrayList<JSONObject> mResultDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    private TextView mEmptyView;

    int pointTotalTotal = 0;
    int pointTotalCanuse = 0;

    int stempTotalTotal = 0;
    int stempTotalCanuse = 0;

    int gameTotalTotal = 0;
    int gameTotalCanuse = 0;


    private TextView mTvHeaderPointTotal;
    private TextView mTvHeaderPointCanuse;
    private TextView mTvHeaderStempTotal;
    private TextView mTvHeaderStempCanuse;
    private TextView mTvHeaderGameTotal;
    private TextView mTvHeaderGameCanuse;




    private TextInputEditText mEditStartDate;
    private TextInputEditText mEditEndDate;
    private Spinner mSpinnerBounsType;
    private Spinner mSpinnerBounsKind;
    private TextInputEditText mEditStoreName;
    private Spinner mSpinnerCity;
    private Spinner mSpinnerState;
    private Spinner mSpinnerBusinessType;
    private Spinner mSpinnerBusinessDetail;
    private Button mBtnFind;

    ArrayList<String> mBonusTypes = new ArrayList<String>();
    ArrayList<String> mBonusKinds = new ArrayList<String>();

    ArrayList<String> mCitys = new ArrayList<String>();
    ArrayList<String> mStates = new ArrayList<String>();

    private ArrayList<JSONObject> mBusinessTypes = new ArrayList<>();
    private ArrayList<JSONObject> mBusinessDetails = new ArrayList<>();

    private SimpleDateFormat dateFormatter;


    private String mFindBonusType;
    private String mFindBonusKind;
    private String mFindCity;
    private String mFindState;
    private String mFindBusinessType;
    private String mFindBusinessDetail;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_check_mypoint_list;
    }

    @Override
    protected void initLayout(View view) {
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mEmptyView.setVisibility(View.GONE);
//        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
//        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CheckMyPointAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean scroll_last = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && scroll_last) {
//                    if (CURRENT_PAGE <= TOTAL_PAGE) {
//                        requestTranslateHistory();
//                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    scroll_last = (totalItemCount > 0) && (pastVisiblesItems + visibleItemCount >= totalItemCount);
                }

            }
        });
        mAdapter.setOnMyPointClickListener(this);


        mTvHeaderPointTotal = (TextView) view.findViewById(R.id.tv_header_mypoint_total);
        mTvHeaderPointCanuse = (TextView) view.findViewById(R.id.tv_header_mypoint_canuse);
        mTvHeaderStempTotal = (TextView) view.findViewById(R.id.tv_header_mystemp_total);
        mTvHeaderStempCanuse = (TextView) view.findViewById(R.id.tv_header_mystemp_canuse);
        mTvHeaderGameTotal = (TextView) view.findViewById(R.id.tv_header_mygame_total);
        mTvHeaderGameCanuse = (TextView) view.findViewById(R.id.tv_header_mygame_canuse);


        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        mEditStartDate = (TextInputEditText) view.findViewById(R.id.edit_header_find_startdate);
        //mEditStartDate.addTextChangedListener(mUserInfoTextWatcher);
        mEditStartDate.setOnClickListener(this);
        mEditEndDate = (TextInputEditText) view.findViewById(R.id.edit_header_find_enddate);
        mEditEndDate.setOnClickListener(this);
        mSpinnerBounsType = (Spinner) view.findViewById(R.id.spinner_header_bonus_type);
        mSpinnerBounsKind = (Spinner) view.findViewById(R.id.spinner_header_bonus_kind);
        mEditStoreName = (TextInputEditText) view.findViewById(R.id.edit_header_find_storename);
        //mEditStoreName.addTextChangedListener(mUserInfoTextWatcher);
        mSpinnerCity = (Spinner) view.findViewById(R.id.spinner_header_area_city);
        mSpinnerState = (Spinner) view.findViewById(R.id.spinner_header_area_state);
        mSpinnerBusinessType = (Spinner) view.findViewById(R.id.spinner_header_business_type);
        mSpinnerBusinessDetail = (Spinner) view.findViewById(R.id.spinner_header_business_detail);
        mBtnFind = (Button) view.findViewById(R.id.btn_header_find_store);
        mBtnFind.setOnClickListener(this);

        recyclerHeader = (RecyclerViewHeader) view.findViewById(R.id.header);
        recyclerHeader.attachTo(mRecyclerView);
    }

    @Override
    protected void initRequest() {
        onRefresh();
        requestCitys();
        requestSector();

        // 포인트 종류, 유형 추가.
        mBonusTypes.add(0, "포인트");
        mBonusTypes.add(1, "스탬프");
        mBonusTypes.add(2, "가위바위보");

//        mBonusKinds.add(0, "통합형");
//        mBonusKinds.add(1, "그룹형");
//        mBonusKinds.add(2, "단독형");
        mBonusKinds.add(0, "거스름돈");
        mBonusKinds.add(1, "결제(적립)");
        mBonusKinds.add(2, "보너스사용");
        mBonusKinds.add(3, "결제취소(적립취소)");
        mBonusKinds.add(4, "보너스사용 취소");
        mBonusKinds.add(5, "출금");

        HintSpinner<String> hintSpinner = new HintSpinner<>(
                mSpinnerBounsType,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                new HintAdapter<>(getActivity(), "보너스 종류", mBonusTypes),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        if (itemAtPosition.equals("보너스 종류") == false) {
                            if (position == 0)
                                mFindBonusType = "P";
                            else if (position == 1)
                                mFindBonusType = "S";
                            else if (position == 2)
                                mFindBonusType = "R";
                            //mFindBonusType = mBonusTypes.get(position);
                        }
                    }
                });
        hintSpinner.init();

        HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                mSpinnerBounsKind,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<>(getActivity(), "유형종류", mBonusKinds),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                        if (itemAtPosition.equals("유형종류") == false) {
                            //mFindBonusKind = mBonusKinds.get(position);
                            mFindBonusKind = String.valueOf(position);
                        }
                    }
                });
        hintSpinner1.init();
    }

    @Override
    public void onRefresh() {

        pointTotalTotal = 0;
        pointTotalCanuse = 0;

        stempTotalTotal = 0;
        stempTotalCanuse = 0;

        gameTotalTotal = 0;
        gameTotalCanuse = 0;

        mDataList.clear();
        mResultDataList.clear();
//        mDataList.add(0, new JSONObject());
//        mDataList.add(1, new JSONObject());
        mAdapter.refreshData(mDataList);
//        CURRENT_PAGE = 1;
//        TOTAL_PAGE = 1;
        requestCheckMyPointList();
    }

    private void requestCheckMyPointList() {
        ResReqPoint res = new ResReqPoint();
        res.setToken(TNPreference.getMemToken(getActivity()));

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }


    private void requestCitys() {
        ResCitys res = new ResCitys();

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestStates(String selCity) {
        ResSubCitys res = new ResSubCitys();
        res.setParameterQuestion(selCity);

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestSector() {
        ResSector res = new ResSector();

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestSubSector(String selSector) {
        ResSubSector res = new ResSubSector();
        res.setParameterQuestion(selSector);

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestFindMyPoint() {

        ResFindMyPoint res = new ResFindMyPoint();
        res.setToken(TNPreference.getMemToken(getActivity()));

        if (mEditStartDate.getText().length() > 0 && mEditEndDate.getText().length() > 0) {
            res.setParameterStartDate(mEditStartDate.getText().toString());
            res.setParameterEndDate(mEditEndDate.getText().toString());
        }

        if (mFindBonusType != null && mFindBonusType.length() > 0)
            res.setParameterEarn(mFindBonusType);

        if (mFindBonusKind != null && mFindBonusKind.length() > 0)
            res.setParameterKind(mFindBonusKind);

        if (mEditStoreName.getText().length() > 0)
            res.setParameterName(mEditStoreName.getText().toString());

        if (mFindCity != null && mFindCity.length() > 0) {
            String findArea = mFindCity;
            if (mFindState != null && mFindState.length() > 0)
                findArea += (" " + mFindState);
            res.setParameterArea(findArea);
        }

        if (mFindBusinessDetail != null && mFindBusinessDetail.length() > 0) {
            res.setParameterSectors(mFindBusinessDetail);
        }


        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);

    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResReqPoint) {
            ResReqPoint res = (ResReqPoint) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONObject obj_message = JSONParser.getObject(obj, ResReqPoint.KEY_RES.message);
                if (obj_message != null) {
                    JSONArray array_occur = JSONParser.getArray(obj_message, "occur");
                    JSONArray array_available = JSONParser.getArray(obj_message, "available");

                    for (int i = 0; i < array_occur.length(); i++) {

                        JSONObject obj_row = JSONParser.getArrayItem(array_occur, i);
                        if (obj_row != null) {
                            String kind = JSONParser.getString(obj_row, "kind");
                            String type = JSONParser.getString(obj_row, "earn");
                            JSONArray array_points = JSONParser.getArray(obj_row, "points");
                            if (array_points != null && array_points.length() > 0) {
                                for (int idx = 0; idx < array_points.length(); idx++) {
                                    JSONObject obj_point = JSONParser.getArrayItem(array_points, idx);
                                    if (obj_point != null) {
                                        String point_kind = JSONParser.getString(obj_point, "kind");
                                        String point_type = JSONParser.getString(obj_point, "earn");
                                        String point_point = JSONParser.getString(obj_point, "point");

                                        if (point_kind.equals("P"))
                                            pointTotalTotal += Integer.valueOf(point_point);
                                        else if (point_kind.equals("S"))
                                            stempTotalTotal += Integer.valueOf(point_point);
                                        else if (point_kind.equals("R"))
                                            gameTotalTotal += Integer.valueOf(point_point);
                                    }
                                }
                            }
                        }

                    }

                    for (int i = 0; i < array_available.length(); i++) {

                        JSONObject obj_row = JSONParser.getArrayItem(array_available, i);
                        if (obj_row != null) {
                            String kind = JSONParser.getString(obj_row, "kind");
                            String type = JSONParser.getString(obj_row, "earn");
                            JSONArray array_points = JSONParser.getArray(obj_row, "points");
                            if (array_points != null && array_points.length() > 0) {
                                for (int idx = 0; idx < array_points.length(); idx++) {
                                    JSONObject obj_point = JSONParser.getArrayItem(array_points, idx);
                                    if (obj_point != null) {
                                        String point_kind = JSONParser.getString(obj_point, "kind");
                                        String point_type = JSONParser.getString(obj_point, "earn");
                                        String point_point = JSONParser.getString(obj_point, "point");

                                        if (point_kind.equals("P"))
                                            pointTotalCanuse += Integer.valueOf(point_point);
                                        else if (point_kind.equals("S"))
                                            stempTotalCanuse += Integer.valueOf(point_point);
                                        else if (point_kind.equals("R"))
                                            gameTotalCanuse += Integer.valueOf(point_point);
                                    }
                                }
                            }
                        }
                    }


                    mTvHeaderPointTotal.setText(String.valueOf(pointTotalTotal));
                    mTvHeaderPointCanuse.setText(String.valueOf(pointTotalCanuse));
                    mTvHeaderStempTotal.setText(String.valueOf(stempTotalTotal));
                    mTvHeaderStempCanuse.setText(String.valueOf(stempTotalCanuse));
                    mTvHeaderGameTotal.setText(String.valueOf(gameTotalTotal));
                    mTvHeaderGameCanuse.setText(String.valueOf(gameTotalCanuse));

                    mDataList.clear();
                    //CURRENT_PAGE = CURRENT_PAGE + 1;
//                    mDataList.add(0, headerJson);
//                    for (int i = 0 ; i < 20 ; i++)
//                        mDataList.add(i, new JSONObject());
                    mAdapter.refreshData(mDataList);
                    //mSwipeRefresh.setRefreshing(false);

                }
            }
        }

        if (o[0] instanceof ResCitys) {
            ResCitys res = (ResCitys) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResCitys.KEY_RES.message);

                mCitys.clear();
                mStates.clear();

                if (array != null && array.length() > 0) {
                    for (int i = 0 ; i < array.length() ; i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //mDataStates.add(code_name);
                        mCitys.add(code_name);
                    }

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerCity,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "시/도", mCitys),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    String city_name = mCitys.get(position);
                                    mFindCity = city_name;
                                    requestStates(city_name);
                                }
                            });
                    hintSpinner.init();
                }

                mStates.add("구/군");
                HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                        mSpinnerState,
                        // Default layout - You don't need to pass in any layout id, just your hint text and
                        // your list data
                        new HintAdapter<>(getActivity(), "구/군", mStates),
                        new HintSpinner.Callback<String>() {
                            @Override
                            public void onItemSelected(int position, String itemAtPosition) {
                                // Here you handle the on item selected event (this skips the hint selected event)
                            }
                        });
                hintSpinner1.init();
            }
        }

        if (o[0] instanceof ResSubCitys) {
            ResSubCitys res = (ResSubCitys) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResSubCitys.KEY_RES.message);

                mStates.clear();

                if (array != null && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //mDataStates.add(code_name);
                        mStates.add(code_name);
                    }

                    if (mStates.size() == 0)
                        mStates.add("구/군");
                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerState,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "구/군", mStates),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    if (itemAtPosition.equals("구/군") == false)
                                        mFindState = mStates.get(position);
                                }
                            });
                    hintSpinner.init();
                }
            }
        }



        if (o[0] instanceof ResSector) {
            ResSector res = (ResSector) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResSector.KEY_RES.message);

                mBusinessTypes.clear();
                mBusinessDetails.clear();

                if (array != null && array.length() > 0) {
                    ArrayList<String> spinnerArray = new ArrayList<>();
                    ArrayList<String> spinnerArray1 = new ArrayList<>();
                    for (int i = 0 ; i < array.length() ; i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONObject obj_row = JSONParser.getArrayItem(array, i);
                        mBusinessTypes.add(obj_row);
                        spinnerArray.add(JSONParser.getString(obj_row, "name"));
                    }

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerBusinessType,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "업종", spinnerArray),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    String business_code = JSONParser.getString(mBusinessTypes.get(position), "code");
                                    requestSubSector(business_code);
                                }
                            });
                    hintSpinner.init();

                    spinnerArray1.add("업종상세");
                    HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                            mSpinnerBusinessDetail,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            new HintAdapter<>(getActivity(), "업종상세", spinnerArray1),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {
                                    // Here you handle the on item selected event (this skips the hint selected event)
                                }
                            });
                    hintSpinner1.init();
                }
            }
        }

        if (o[0] instanceof ResSubSector) {
            ResSubSector res = (ResSubSector) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResSubSector.KEY_RES.message);

                mBusinessDetails.clear();

                if (array != null && array.length() > 0) {
                    ArrayList<String> spinnerArray = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //mDataStates.add(code_name);
                        JSONObject obj_row = JSONParser.getArrayItem(array, i);
                        mBusinessDetails.add(obj_row);
                        spinnerArray.add(JSONParser.getString(obj_row, "name"));
                    }

                    if (spinnerArray.size() == 0)
                        spinnerArray.add("업종상세");
                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerBusinessDetail,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "업종상세", spinnerArray),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    if (itemAtPosition.equals("업종상세") == false)
                                        mFindBusinessDetail = JSONParser.getString(mBusinessDetails.get(position), "code");
                                }
                            });
                    hintSpinner.init();
                }
            }
        }

        if (o[0] instanceof ResFindMyPoint) {
            ResFindMyPoint res = (ResFindMyPoint) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
               JSONArray array = JSONParser.getArray(obj, ResFindMyPoint.KEY_RES.message);

                mDataList.clear();
                for (int i = 0 ; i < array.length() ; i++) {
                    mResultDataList.add(i, JSONParser.getArrayItem(array, i));
                    if (i < 20)
                        mDataList.add(i, JSONParser.getArrayItem(array, i));
                }
                mAdapter.refreshData(mDataList);
                //mSwipeRefresh.setRefreshing(false);
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
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.edit_header_find_startdate:
            {
                // Create the new DatePickerDialog instance.
                Bundle b = new Bundle();
                //b.putString("dialog_msg", getString(R.string.msg_success_guide_ask));
                b.putString("dialog_msg", "시작일을 선택 하세요.");
                SpinnerDateDialog date_dialog = new SpinnerDateDialog();
                date_dialog.setArguments(b);
                date_dialog.setOnResultListener(new BaseDialogFragment.OnResultListener() {
                    @Override
                    public void onDialogResult(Object... objects) {
                        if (objects != null && objects[0] instanceof Boolean) {
                            boolean isOk = (boolean) objects[0];
                            if (isOk) {
                                int year = (int)objects[1];
                                int month = (int)objects[2];
                                int day = (int)objects[3];

                                String birth_day = String.format("%04d-%02d-%02d", year, month, day);
                                Date newDate = null;
                                try {
                                    newDate = dateFormatter.parse(birth_day);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                mEditStartDate.setText(dateFormatter.format(newDate.getTime()));
                            }
                        }
                    }
                });
                date_dialog.show(getFragmentManager(), "dialog");
            }
            break;
            case R.id.edit_header_find_enddate:
            {
                // Create the new DatePickerDialog instance.
                Bundle b = new Bundle();
                //b.putString("dialog_msg", getString(R.string.msg_success_guide_ask));
                b.putString("dialog_msg", "종료일을 선택 하세요.");
                SpinnerDateDialog date_dialog = new SpinnerDateDialog();
                date_dialog.setArguments(b);
                date_dialog.setOnResultListener(new BaseDialogFragment.OnResultListener() {
                    @Override
                    public void onDialogResult(Object... objects) {
                        if (objects != null && objects[0] instanceof Boolean) {
                            boolean isOk = (boolean) objects[0];
                            if (isOk) {
                                int year = (int)objects[1];
                                int month = (int)objects[2];
                                int day = (int)objects[3];

                                String birth_day = String.format("%04d-%02d-%02d", year, month, day);
                                Date newDate = null;
                                try {
                                    newDate = dateFormatter.parse(birth_day);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                mEditEndDate.setText(dateFormatter.format(newDate.getTime()));
                            }
                        }
                    }
                });
                date_dialog.show(getFragmentManager(), "dialog");
            }
            break;
        case R.id.btn_header_find_store: {
            requestFindMyPoint();
        }
            break;

        }
    }

    public void clearList() {
        mDataList.clear();
        //CURRENT_PAGE = CURRENT_PAGE + 1;
//        mDataList.add(0, new JSONObject());
//        mDataList.add(1, new JSONObject());
        mAdapter.refreshData(mDataList);
        //mSwipeRefresh.setRefreshing(false);
//        if (mDataList.size() > 1) {
//            mRecyclerView.setVisibility(View.VISIBLE);
//            mEmptyView.setVisibility(View.GONE);
//        } else {
//            mRecyclerView.setVisibility(View.GONE);
//            mEmptyView.setVisibility(View.VISIBLE);
//        }
    }

    public void updateList(JSONArray array) {
        mDataList.clear();
//        mDataList.add(0, new JSONObject());
//        mDataList.add(1, new JSONObject());
        for (int i = 0; i < array.length(); i++) {
            mDataList.add(JSONParser.getArrayItem(array, i+2));
        }
        //CURRENT_PAGE = CURRENT_PAGE + 1;
        mAdapter.refreshData(mDataList);
        //mSwipeRefresh.setRefreshing(false);
        if (mDataList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

}
