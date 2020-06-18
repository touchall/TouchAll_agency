package allpointech.agency.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuna.ui.fragment.BaseDialogFragment;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import allpointech.agency.R;


/**
 * Created by daze on 2016-05-11.
 */
public class ProgressDialog extends BaseDialogFragment {

    private String mMessage;
    private int mColor;

    private LinearLayout mLayout;
    private CircularProgressBar mProgress;
    private TextView mTextView;

    private boolean isTransparent = false;

    public void setTransparent(boolean isEnable) {
        isTransparent = isEnable;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setLayout(dp2px(getDialog().getContext(), 308), ViewGroup.LayoutParams.WRAP_CONTENT);
        if (isTransparent) {
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
        }
        else
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void initBundleData() {
//        setCancelable(false);

        if (getArguments() != null) {
            mMessage = getArguments().getString("dialog_msg");
            mColor = getArguments().getInt("dialog_color", R.color.primary);
        }
    }

    @Override
    public Integer inflateLayout() {
        return R.layout.dialog_progress;
    }

    @Override
    public void initLayout(View view) {
        mLayout = (LinearLayout) view.findViewById(R.id.layout);

        mProgress = (CircularProgressBar) view.findViewById(R.id.progress);
        if (isTransparent)
            mProgress.setVisibility(View.GONE);

        mTextView = (TextView) view.findViewById(R.id.contents);
        if (mMessage != null) {
            mLayout.setBackgroundResource(R.drawable.draw_rect_round_5_white_fill);
            mTextView.setText(mMessage);
            mTextView.setVisibility(View.VISIBLE);
        }
    }

}
