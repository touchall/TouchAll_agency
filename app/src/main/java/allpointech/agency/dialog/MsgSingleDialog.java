package allpointech.agency.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tuna.ui.fragment.BaseDialogFragment;

import allpointech.agency.R;


/**
 * Created by daze on 2016-05-11.
 */
public class MsgSingleDialog extends BaseDialogFragment implements View.OnClickListener {
    private String mTitle;
    private String mContents;

    private TextView mTvTitle;
    private TextView mTvMessage;
    private Button mBtnOK;

    @Override
    public void initBundleData() {
        setCancelable(false);

        if (getArguments() != null) {
            mTitle = getArguments().getString("dialog_title");
            mContents = getArguments().getString("dialog_msg");
        }
    }

    @Override
    public Integer inflateLayout() {
        return R.layout.dialog_msg_single;
    }

    @Override
    public void initLayout(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.title);
        if (mTitle != null) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(mTitle);
        }

        mTvMessage = (TextView) view.findViewById(R.id.contents);
        mTvMessage.setText(mContents);

        mBtnOK = (Button) view.findViewById(R.id.btn_ok);
        mBtnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (mListener != null) {
                    mListener.onDialogResult(true);
                }
                dismiss();
                break;
        }
    }
}
