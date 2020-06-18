package allpointech.agency.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tuna.ui.fragment.BaseDialogFragment;

import allpointech.agency.R;


/**
 * Created by daze on 2016-05-11.
 */
public class MsgDialog extends BaseDialogFragment implements View.OnClickListener {
    protected OnOptionListener mOptListener;

    public void setOnResultListener(OnOptionListener listener) {
        this.mOptListener = listener;
    }

    public interface OnOptionListener {
        void onDialogOption();
    }

    private String mTitle;
    private String mContents;
    private String mOk;
    private String mCancel;
    private String mOpt;

    private TextView mTvTitle;
    private TextView mTvMessage;
    private Button mBtnOK;
    private Button mBtnCancel;
    private Button mBtnOpt;

    @Override
    public void initBundleData() {
        setCancelable(false);

        if (getArguments() != null) {
            mTitle = getArguments().getString("dialog_title");
            mContents = getArguments().getString("dialog_msg");
            mOk = getArguments().getString("dialog_ok");
            mCancel = getArguments().getString("dialog_cancel");
            mOpt = getArguments().getString("dialog_opt");
        }
    }

    @Override
    public Integer inflateLayout() {
        return R.layout.dialog_msg;
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
        if (mOk != null) {
            mBtnOK.setText(mOk);
        }
        mBtnCancel = (Button) view.findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(this);
        if (mCancel != null) {
            mBtnCancel.setText(mCancel);
        }
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
            case R.id.btn_cancel:
                if (mListener != null) {
                    mListener.onDialogResult(false);
                }
                dismiss();
                break;
        }
    }
}
