package allpointech.agency.user.notice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import allpointech.agency.R;

/**
 * Created by jay on 2018. 6. 27..
 */

public class UserNoticeAdapter extends RecyclerView.Adapter<UserNoticeAdapter.ViewHolder> {
    private Context mContext;
    //private FragmentManager mFragmentManager;
    private ArrayList<JSONObject> mDataList = new ArrayList<>();

    private OnNoticeClickListener mListener;

    public void setOnNoticeClickListener(OnNoticeClickListener listener) {
        mListener = listener;
    }

    protected interface OnNoticeClickListener {
        void onNoticeClick(View v);

//        void onReTranslate(View v1, View v2, int position);
    }

    public UserNoticeAdapter(Context context) {
        mContext = context;
    }

    public UserNoticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_user_notice, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNoticeClick(v);
            }
        });
        ViewHolder holder = new ViewHolder(v, viewType);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        JSONObject row_data = mDataList.get(position);
//        String bbs_subject = JSONParser.getString(row_data, ResNotice.KEY_RES.ROW.bbs_subject);
//        String bbs_sdate = JSONParser.getString(row_data, ResNotice.KEY_RES.ROW.bbs_sdate);
//
//        //bbs_subject = bbs_subject.replace(" ", "\u00A0");
//
//        holder.mTvNoticeMsg.setText(bbs_subject);
//        holder.mTvNoticeDate.setText(bbs_sdate);

//        holder.mBtnReAsk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JSONObject data = mDataList.get(position);
//                String idx = JSONParser.getString(data, ResUserHistory.KEY_RES.ROW.translate_answer_idx);
//                if (idx != null && !"".equals(idx)) {
//                    if (mListener != null) {
//                        mListener.onReTranslate(holder.mBtnReAsk, holder.mReAskComponent, position);
//                    }
//                }
//            }
//        });
//        if (position == 0) {
//            holder.line_top.setVisibility(View.VISIBLE);
//        } else {
//            holder.line_top.setVisibility(View.GONE);
//        }
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
        private TextView mTvNoticeMsg;
        private TextView mTvNoticeDate;

        public ViewHolder(View view, int viewType) {
            super(view);
            mTvNoticeMsg = (TextView) view.findViewById(R.id.tv_notice_msg);
            mTvNoticeDate = (TextView) view.findViewById(R.id.tv_notice_date);
        }
    }

    public void refreshData(ArrayList<JSONObject> data) {
        mDataList.clear();
        mDataList.addAll(data);
        notifyDataSetChanged();
    }
}
