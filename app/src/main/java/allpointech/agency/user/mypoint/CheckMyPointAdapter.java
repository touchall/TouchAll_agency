package allpointech.agency.user.mypoint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import allpointech.agency.R;
import allpointech.agency.network.http.json.JSONParser;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckMyPointAdapter extends RecyclerView.Adapter<CheckMyPointAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<JSONObject> mDataList = new ArrayList<>();

    int pointTotalTotal = 0;
    int pointTotalCanuse = 0;
//    int pointCanuse = 0;

    int stempTotalTotal = 0;
    int stempTotalCanuse = 0;
//    int stempCanuse = 0;

    int gameTotalTotal = 0;
    int gameTotalCanuse = 0;
//    int gameCanuse = 0;

    private OnMyPointClickListener mListener;

    public void setOnMyPointClickListener(OnMyPointClickListener listener) {
        mListener = listener;
    }

    protected interface OnMyPointClickListener {
        void onItemClick(int position);
    }

    public CheckMyPointAdapter(Context context) {
        mContext = context;
    }

    public CheckMyPointAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
//        //viewType이 0이면 헤더이므로 flag를 설정하고 헤더 아이템을 inflater를 이용해서 할당한다
//        if(viewType == 0) {
//            v = LayoutInflater.from(mContext).inflate(R.layout.row_user_check_mypoint_header, parent, false);
//        }
//        else if(viewType == 1) {
//            v = LayoutInflater.from(mContext).inflate(R.layout.row_user_check_mypoint_header_find, parent, false);
//        }
//        //헤더가 아닌 나머지 콘텐츠들
//        else
        {
            v = LayoutInflater.from(mContext).inflate(R.layout.row_user_mypoint, parent, false);
        }
        ViewHolder holder = new ViewHolder(v, viewType);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        JSONObject row_data = mDataList.get(position);

        String storeName = JSONParser.getString(row_data, "name");
        String payCost = JSONParser.getString(row_data, "amount");
        String pointType = JSONParser.getString(row_data, "typeName");
        String payDate = JSONParser.getString(row_data, "payDate");
        String pointKind = JSONParser.getString(row_data, "pointType");
        String storeType = JSONParser.getString(row_data, "earnName");
        String point = JSONParser.getString(row_data, "point");

        holder.mTvMyPointIndex.setText(String.valueOf(position));
        holder.mTvMyPointStoreName.setText(storeName);
        holder.mTvMyPointPayCost.setText(payCost);
        holder.mTvMyPointPointType.setText(pointType);
        holder.mTvMyPointDate.setText(payDate);
        holder.mTvMyPointPointKind.setText(pointKind);
        holder.mTvMyPointStroreType.setText(storeType);
        holder.mTvMyPointPoint.setText(point);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void addHeaderView() {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvMyPointIndex;
        private TextView mTvMyPointStoreName;
        private TextView mTvMyPointPayCost;
        private TextView mTvMyPointPointType;
        private TextView mTvMyPointDate;
        private TextView mTvMyPointPointKind;
        private TextView mTvMyPointStroreType;
        private TextView mTvMyPointPoint;

        public ViewHolder(View view, int viewType) {
            super(view);

            mTvMyPointIndex = (TextView) view.findViewById(R.id.tv_mypoint_list_index);
            mTvMyPointStoreName = (TextView) view.findViewById(R.id.tv_mypoint_list_storename);
            mTvMyPointPayCost = (TextView) view.findViewById(R.id.tv_mypoint_list_paycost);
            mTvMyPointPointType = (TextView) view.findViewById(R.id.tv_mypoint_list_pointtype);
            mTvMyPointDate = (TextView) view.findViewById(R.id.tv_mypoint_list_date);
            mTvMyPointPointKind = (TextView) view.findViewById(R.id.tv_mypoint_list_pointkind);
            mTvMyPointStroreType = (TextView) view.findViewById(R.id.tv_mypoint_list_storetype);
            mTvMyPointPoint = (TextView) view.findViewById(R.id.tv_mypoint_list_point);
        }
    }

    public void refreshData(ArrayList<JSONObject> data) {
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

}
