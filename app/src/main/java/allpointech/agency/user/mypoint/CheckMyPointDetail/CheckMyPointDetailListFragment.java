package allpointech.agency.user.mypoint.CheckMyPointDetail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.tuna.ui.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import allpointech.agency.R;
import allpointech.agency.network.http.TNHttpMultiPartTask;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.network.http.resource.BaseHttpResource;
import allpointech.agency.network.http.resource.data.ResReqMyPointDetail;
import allpointech.agency.utils.TNConstants;
import allpointech.agency.utils.TNPreference;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckMyPointDetailListFragment extends BaseFragment implements TNHttpMultiPartTask.onHttpNetResultListener, CheckMyPointDetailAdapter.OnDetailClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String FRAGMENT_TAG = CheckMyPointDetailListFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private CheckMyPointDetailAdapter mAdapter;
    private ArrayList<JSONObject> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    private TextView mEmptyView;

    private int mDetailType = TNConstants.MYPOINT_DETAIL_TYPE.TYPE_TOTAL_POINT;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_user_check_mypoint_detail_list;
    }

    @Override
    protected void initLayout(View view) {
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CheckMyPointDetailAdapter(getActivity());
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
        mAdapter.setOnDetailClickListener(this);
    }

    @Override
    protected void initRequest() {
        requestCheckMyPointDetails();
    }

    @Override
    public void onRefresh() {
        mDataList.clear();
        mAdapter.refreshData(mDataList);
//        CURRENT_PAGE = 1;
//        TOTAL_PAGE = 1;
        requestCheckMyPointDetails();
    }

    private void requestCheckMyPointDetails() {

        String detailType = String.valueOf(mDetailType);

        ResReqMyPointDetail res = new ResReqMyPointDetail();
        res.setParameter(
                TNPreference.getMemphoneNumber(getActivity()),
                detailType);

        TNHttpMultiPartTask task = new TNHttpMultiPartTask(getActivity(), getFragmentManager());
        task.setOnHttpResultListener(this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, res);
    }

    @Override
    public void onHttpNetSuccessEvent(BaseHttpResource[] o) {
        if (o[0] instanceof ResReqMyPointDetail) {
            ResReqMyPointDetail res = (ResReqMyPointDetail) o[0];
            JSONObject obj = res.getParseData();
            if (JSONParser.isSuccess(obj)) {
                //TOTAL_PAGE = JSONParser.getInt(obj, ResUserHistory.KEY_RES.total_page, 0);
                mDataList.clear();
                JSONArray array = JSONParser.getArray(obj, ResReqMyPointDetail.KEY_RES.myPointByType);
                for (int i = 0; i < array.length(); i++) {
                    mDataList.add(JSONParser.getArrayItem(array, i));
                }
                //CURRENT_PAGE = CURRENT_PAGE + 1;
                mAdapter.refreshData(mDataList);
                mSwipeRefresh.setRefreshing(false);
                if (mDataList.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
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
    public void onDetailClick(int position) {

    }

    public void setDetailType(int detailType) {
        mDetailType = detailType;
    }
}
