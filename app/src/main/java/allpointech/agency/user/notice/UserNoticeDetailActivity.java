package allpointech.agency.user.notice;

import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by Ace on 2016-12-15.
 */

public class UserNoticeDetailActivity extends BaseAppCompatActivity {

    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        UserNoticeDetailFragment fragment = new UserNoticeDetailFragment();
        if (getIntent() != null) {
            fragment.setArguments(getIntent().getExtras());
            //SLog.LogD("Ace : " + getIntent().getStringExtra("obj"));
        }
        return fragment;
    }



    @Override
    protected void initRequest() {

    }

    @Override
    protected void initDefaultSet() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
