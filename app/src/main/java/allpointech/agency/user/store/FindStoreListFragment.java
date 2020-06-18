package allpointech.agency.user.store;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResBusinessDetail;
import allpointech.agency.network.http.resource.data.ResBusinessType;
import allpointech.agency.network.http.resource.data.ResCitys;
import allpointech.agency.network.http.resource.data.ResFindStore;
import allpointech.agency.network.http.resource.data.ResSubCitys;
import allpointech.agency.utils.TNPreference;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by jay on 2018-07-10.
 */

public class FindStoreListFragment extends BaseFragment implements BaseAppCompatActivity.onKeyBackPressedListener, TNHttpMultiPartTask.onHttpNetResultListener, FindStoreAdapter.OnFindStoreClickListener, View.OnClickListener {
    public static final String FRAGMENT_TAG = FindStoreListFragment.class.getSimpleName();

    private TextInputEditText mEdStoreId;
    private TextInputEditText mEdStoreName;

    private Spinner mSpinnerCity;
    private Spinner mSpinnerState;
    private Spinner mSpinnerBonusType;
    private Spinner mSpinnerBusinessType;
    private Spinner mSpinnerBusinessDetail;

    private TextView mBtnFindStore;

    ArrayList<String> citys = new ArrayList<String>();
    ArrayList<String> states = new ArrayList<String>();
    ArrayList<String> bonusTypes = new ArrayList<String>();
    private ArrayList<JSONObject> businessTypes = new ArrayList<>();
    private ArrayList<JSONObject> businessDetails = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerViewHeader recyclerHeader;
    private FindStoreAdapter mAdapter;
    private ArrayList<JSONObject> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    private TextView mEmptyView;


    private String mFindCity;
    private String mFindState;
    private String mFindBonusType;
    private String mFindBusinessType;

    @Override
    protected void BundleData(Bundle bundle) {
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_find_store_list;
    }

    @Override
    protected void initLayout(View view) {
        mEdStoreId = (TextInputEditText) view.findViewById(R.id.edit_store_serial);
        mEdStoreName = (TextInputEditText) view.findViewById(R.id.edit_store_name);

        mSpinnerCity = (Spinner) view.findViewById(R.id.spinner_find_store_city);
        mSpinnerState = (Spinner) view.findViewById(R.id.spinner_find_store_state);

        mSpinnerBonusType = (Spinner) view.findViewById(R.id.spinner_find_store_bounus_type);

        mSpinnerBusinessType = (Spinner) view.findViewById(R.id.spinner_find_store_business_type);
        mSpinnerBusinessDetail = (Spinner) view.findViewById(R.id.spinner_find_store_business_detail);

        mBtnFindStore = (TextView) view.findViewById(R.id.btn_find_store);
        mBtnFindStore.setOnClickListener(this);







        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mEmptyView.setVisibility(View.GONE);
//        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
//        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FindStoreAdapter(getActivity());
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
        mAdapter.setOnFindStoreClickListener(this);

        recyclerHeader = (RecyclerViewHeader) view.findViewById(R.id.find_store_header);
        recyclerHeader.attachTo(mRecyclerView);
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

    private void requestBusinessType() {
        ResBusinessType res = new ResBusinessType();
        //res.setParameterQuestion(selCity);

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestBusinessDetail(String businessCode) {
        ResBusinessDetail res = new ResBusinessDetail();
        res.setParameterQuestion(businessCode);

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    private void requestFindStore() {
        ResFindStore res = new ResFindStore();
        res.setToken(TNPreference.getMemToken(getActivity()));

        if (mEdStoreId.getText().length() > 0)
            res.setParameterSerial("KR" + mEdStoreId.getText().toString());

        if (mEdStoreName.getText().length() > 0)
            res.setParameterName(mEdStoreName.getText().toString());

        if (mFindCity != null && mFindCity.length() > 0) {
            String findArea = mFindCity;
            if (mFindState != null && mFindState.length() > 0) {
                findArea += (" " + mFindState);
            }
            res.setParameterArea(findArea);
        }

        if (mFindBonusType != null && mFindBonusType.length() > 0)
            res.setParameterEarn(mFindBonusType);

        if (mFindBusinessType != null && mFindBusinessType.length() > 0)
            res.setParameterSectors(mFindBusinessType);

        res.setParameterPageSize("20");
        res.setParameterOffset("0");

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    protected void initRequest() {
        requestCitys();
        requestBusinessType();

//        bonusTypes.add("통합형");
//        bonusTypes.add("그룹형");
//        bonusTypes.add("단독형");
        bonusTypes.add("보너스 종류 전체");
        bonusTypes.add("통합,호환형");
        bonusTypes.add("그룹형");
        bonusTypes.add("단독형");

        HintSpinner<String> hintSpinner = new HintSpinner<>(
                mSpinnerBonusType,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                new HintAdapter<>(getActivity(), "보너스 종류", bonusTypes),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                        if (itemAtPosition.equals("보너스 종류") == false) {
                            //mFindBonusType = bonusTypes.get(position);
                            if (position == 0)
                                mFindBonusType = "";
                            else if (position == 1)
                                mFindBonusType = "T";
                            else if (position == 2)
                                mFindBonusType = "G";
                            else if (position == 3)
                                mFindBonusType = "S";
                        }
                    }
                });
        hintSpinner.init();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResCitys) {
            ResCitys res = (ResCitys) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResCitys.KEY_RES.message);

                citys.clear();
                states.clear();

                citys.add("시/도 전체");
                if (array != null && array.length() > 0) {
                    for (int i = 0 ; i < array.length() ; i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //mDataStates.add(code_name);
                        citys.add(code_name);
                    }

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerCity,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "시/도", citys),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    if (itemAtPosition.equals("시/도 전체")) {
                                        mFindCity = "";
                                        mFindState = "";
                                        states.clear();
                                        states.add("구/군");
                                        HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                                                mSpinnerState,
                                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                                // your list data
                                                new HintAdapter<>(getActivity(), "구/군", states),
                                                new HintSpinner.Callback<String>() {
                                                    @Override
                                                    public void onItemSelected(int position, String itemAtPosition) {
                                                        // Here you handle the on item selected event (this skips the hint selected event)
                                                    }
                                                });
                                        hintSpinner1.init();
                                        //requestCitys();
                                    }
                                    else {
                                        String city_name = citys.get(position);
                                        mFindCity = city_name;
                                        mFindState = "";
                                        requestStates(city_name);
                                    }
                                }
                            });
                    hintSpinner.init();
                }

                states.add("구/군");
                HintSpinner<String> hintSpinner1 = new HintSpinner<>(
                        mSpinnerState,
                        // Default layout - You don't need to pass in any layout id, just your hint text and
                        // your list data
                        new HintAdapter<>(getActivity(), "구/군", states),
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

                states.clear();

                if (array != null && array.length() > 0) {

                    states.add("구/군 전체");
                    for (int i = 0; i < array.length(); i++) {

                        String code_name = null;
                        try {
                            code_name = array.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        states.add(code_name);
                    }

                    if (states.size() == 0)
                        states.add("구/군");

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerState,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            //new HintAdapter<>(getActivity(), R.string.bank_prompt, spinnerArray),
                            new HintAdapter<>(getActivity(), "구/군", states),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    if (itemAtPosition.equals("구/군")) {
                                        mFindState = "";
                                    }
                                    else  if (itemAtPosition.equals("구/군 전체")) {
                                        mFindState = "";
                                    }
                                    else {
                                        mFindState = states.get(position);
                                    }
                                }
                            });
                    hintSpinner.init();
                }
            }
        }

        if (o[0] instanceof ResBusinessType) {
            ResBusinessType res = (ResBusinessType) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResCitys.KEY_RES.message);

                businessTypes.clear();
                businessDetails.clear();

                if (array != null && array.length() > 0) {
                    ArrayList<String> spinnerArray = new ArrayList<>();
                    ArrayList<String> spinnerArray1 = new ArrayList<>();

                    spinnerArray.add("업종 전체");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj_row = JSONParser.getArrayItem(array, i);
                        businessTypes.add(obj_row);
                        spinnerArray.add(JSONParser.getString(obj_row, "name"));
                    }

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerBusinessType,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            new HintAdapter<>(getActivity(), "업종", spinnerArray),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    if (itemAtPosition.equals("업종 전체")) {
                                        mFindBusinessType = "";
                                        ArrayList<String> spinnerArray2 = new ArrayList<>();
                                        spinnerArray2.add("업종상세");
                                        HintSpinner<String> hintSpinner2 = new HintSpinner<>(
                                                mSpinnerBusinessDetail,
                                                // Default layout - You don't need to pass in any layout id, just your hint text and
                                                // your list data
                                                new HintAdapter<>(getActivity(), "업종상세", spinnerArray2),
                                                new HintSpinner.Callback<String>() {
                                                    @Override
                                                    public void onItemSelected(int position, String itemAtPosition) {
                                                    }
                                                });
                                        hintSpinner2.init();
                                    }
                                    else {
                                        String business_code = JSONParser.getString(businessTypes.get(position-1), "code");
                                        requestBusinessDetail(business_code);
                                    }
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
                                }
                            });
                    hintSpinner1.init();
                }
            }
        }

        if (o[0] instanceof ResBusinessDetail) {
            ResBusinessDetail res = (ResBusinessDetail) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResBusinessDetail.KEY_RES.message);

                businessDetails.clear();

                if (array != null && array.length() > 0) {

                    ArrayList<String> spinnerArray = new ArrayList<>();
                    spinnerArray.add("업종상세 전체");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj_row = JSONParser.getArrayItem(array, i);
                        businessDetails.add(obj_row);
                        spinnerArray.add(JSONParser.getString(obj_row, "name"));
                    }

                    if (spinnerArray.size() == 0)
                        spinnerArray.add("업종상세");

                    HintSpinner<String> hintSpinner = new HintSpinner<>(
                            mSpinnerBusinessDetail,
                            // Default layout - You don't need to pass in any layout id, just your hint text and
                            // your list data
                            new HintAdapter<>(getActivity(), "업종상세", spinnerArray),
                            new HintSpinner.Callback<String>() {
                                @Override
                                public void onItemSelected(int position, String itemAtPosition) {

                                    if (itemAtPosition.equals("업종상세")) {
                                        mFindBusinessType = "";
                                    }
                                    else if (itemAtPosition.equals("업종상세 전체")) {
                                        mFindBusinessType = "";
                                    }
                                    else {
                                        JSONObject obj_row = businessDetails.get(position-1);
                                        mFindBusinessType = JSONParser.getString(obj_row, "code");
                                    }
                                }
                            });
                    hintSpinner.init();
                }
            }
        }

        if (o[0] instanceof ResFindStore) {
            ResFindStore res = (ResFindStore) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                JSONArray array = JSONParser.getArray(obj, ResBusinessDetail.KEY_RES.message);
                if (array == null || array.length() <= 0) {
                    mDataList.clear();
                    mAdapter.refreshData(mDataList);
                    showToast("검색 결과가 없습니다.");
                }
                else {
                    mDataList.clear();
                    for (int i = 0 ; i < array.length() ; i++) {
                        mDataList.add(i, JSONParser.getArrayItem(array, i));
                        //mDataList.add(2*i, JSONParser.getArrayItem(array, i));
                    }
                    mAdapter.refreshData(mDataList);
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
            case R.id.btn_find_store:
//                keywordNumber = mEdStoreId.getText().toString();
                requestFindStore();
                break;
        }
    }

    @Override
    public void onBack() {
        //replaceAnimationTagFragment(R.id.contents, new UserMainViewFragment(), UserMainViewFragment.FRAGMENT_TAG, 0, 0);
    }

    @Override
    public void onItemClick(int position) {

    }
}