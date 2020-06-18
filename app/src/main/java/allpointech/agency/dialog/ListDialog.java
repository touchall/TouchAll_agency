package allpointech.agency.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tuna.ui.fragment.BaseDialogFragment;
import com.tuna.utils.SHolder;

import java.util.ArrayList;

import allpointech.agency.R;


/**
 * Created by daze on 2016-07-26.
 */
public class ListDialog extends BaseDialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int ID_BTN_CANCEL = R.id.btn_cancel;

    private ListView mListView;
    private Button mBtnCancel;

    private ArrayList<String> mItemList = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void initBundleData() {
        if (getArguments() != null) {
            mItemList.addAll(getArguments().getStringArrayList("item_list"));
        }
    }

    @Override
    public Integer inflateLayout() {
        return R.layout.dialog_list;
    }

    @Override
    public void initLayout(View view) {
        mListView = (ListView) view.findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case ID_BTN_CANCEL:
                dismiss();
                break;
        }
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public Object getItem(int i) {
            return mItemList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            SHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getDialog().getContext()).inflate(R.layout.dialog_list_row, null);
                holder = new SHolder();
                holder.saveID(R.id.list_item, view);
                view.setTag(holder);
            } else {
                holder = (SHolder) view.getTag();
            }
            //=================================================================================================
            TextView item = (TextView) holder.findViewById(R.id.list_item);
            item.setText(mItemList.get(i));
            //=================================================================================================
            return view;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mListener != null) {
            mListener.onDialogResult(i);
        }
        dismiss();
    }

}
