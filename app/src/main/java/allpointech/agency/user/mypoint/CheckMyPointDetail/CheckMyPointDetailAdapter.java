package allpointech.agency.user.mypoint.CheckMyPointDetail;

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
import allpointech.agency.network.http.resource.data.ResReqMyPointDetail;

/**
 * Created by jay on 2018. 6. 27..
 */

public class CheckMyPointDetailAdapter extends RecyclerView.Adapter<CheckMyPointDetailAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<JSONObject> mDataList = new ArrayList<>();

    private OnDetailClickListener mListener;

    public void setOnDetailClickListener(OnDetailClickListener listener) {
        mListener = listener;
    }

    protected interface OnDetailClickListener {
        void onDetailClick(int position);
    }

    public CheckMyPointDetailAdapter(Context context) {
        mContext = context;
    }

    public CheckMyPointDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_user_mypoint_detail, parent, false);
        ViewHolder holder = new ViewHolder(v, viewType);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        JSONObject row_data = mDataList.get(position);
        String mbuspt = JSONParser.getString(row_data, ResReqMyPointDetail.KEY_RES.ROW.mbuspt);
        String mbocpt = JSONParser.getString(row_data, ResReqMyPointDetail.KEY_RES.ROW.mbocpt);
        String mbpaydate = JSONParser.getString(row_data, ResReqMyPointDetail.KEY_RES.ROW.mbpaydate);

        //bbs_subject = bbs_subject.replace(" ", "\u00A0");

        holder.mTvDetailStore.setText(mbpaydate);
        holder.mTvDetailValidPoint.setText(mbuspt+"P");
        holder.mTvDetailUsePoint.setText(mbocpt+"P");

        holder.mBtnDetailUsePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = mDataList.get(position);
//                String idx = JSONParser.getString(data, ResUserHistory.KEY_RES.ROW.translate_answer_idx);
//                if (idx != null && !"".equals(idx)) {
//                    if (mListener != null) {
//                        //mListener.onDetailClick(holder.mBtnReAsk, holder.mReAskComponent, position);
//                    }
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDetailStore;
        private TextView mTvDetailValidPoint;
        private TextView mTvDetailUsePoint;
        private TextView mBtnDetailUsePoint;

        public ViewHolder(View view, int viewType) {
            super(view);
            mTvDetailStore = (TextView) view.findViewById(R.id.tv_mypoint_detail_store);
            mTvDetailValidPoint = (TextView) view.findViewById(R.id.tv_mypoint_detail_valid_point);
            mTvDetailUsePoint = (TextView) view.findViewById(R.id.tv_mypoint_detail_use_point);
            mBtnDetailUsePoint = (TextView) view.findViewById(R.id.btn_mypoint_detail_use_point);
        }
    }

    public void refreshData(ArrayList<JSONObject> data) {
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
    }
}
