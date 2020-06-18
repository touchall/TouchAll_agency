package allpointech.agency.network.http.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import allpointech.agency.R;


public class HttpProgressDialog extends Dialog {

    private Context mContext;
    private TextView mTitleView;

    public HttpProgressDialog(Context context) {
        super(context, R.style.AppTheme_Light_Dialog);
        mContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(createDialogLayout());
        setCancelable(false);
    }

    private View createDialogLayout() {
        FrameLayout rootView = new FrameLayout(mContext);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        rootView.setBackgroundResource(android.R.color.transparent);

        LinearLayout lLayout = new LinearLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        lLayout.setLayoutParams(params);
        lLayout.setOrientation(LinearLayout.VERTICAL);
// progressbar		
        ProgressBar pBar = new ProgressBar(mContext);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params2.gravity = Gravity.CENTER;
        pBar.setLayoutParams(params2);
        lLayout.addView(pBar);
// 통신중 TEXT		
//		TextView tv = new TextView(mContext);
//		params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		params2.gravity = Gravity.CENTER;
//		tv.setLayoutParams(params2);
//		tv.setTextColor(0xFF666666);
//		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
//		tv.setPadding(0, 4, 0, 0);
//		tv.setText("통신중(test)");
//		lLayout.addView(tv);
// frame anim 
//		ImageView frame_logo = new ImageView(mContext);
//		frame_logo.setBackgroundResource(R.anim.logo_frame);	
//		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();	
//		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, dm), 
//				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, dm));
//		frame_logo.setLayoutParams(params2);
//		
//		AnimationDrawable frameAnimation = (AnimationDrawable) frame_logo.getBackground();
//		frameAnimation.start();
//		lLayout.addView(frame_logo);


        rootView.addView(lLayout);

//		mTitleView = tv;

        return rootView;
    }

    public void setText(String title) {
        mTitleView.setText(title);
    }

    @Override
    public void show() {
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getAttributes().height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setWindowAnimations(android.R.style.Animation_Dialog);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        super.show();
    }

}
