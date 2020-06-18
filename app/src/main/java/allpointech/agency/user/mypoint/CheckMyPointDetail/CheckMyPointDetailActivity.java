package allpointech.agency.user.mypoint.CheckMyPointDetail;

import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by Ace on 2016-12-15.
 */

public class CheckMyPointDetailActivity extends BaseAppCompatActivity {

    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        //return new CheckFranchiseeFragment();

        CheckMyPointDetailFragment fragment = new CheckMyPointDetailFragment();
        if (getIntent() != null) {
            fragment.setArguments(getIntent().getExtras());
            //SLog.LogD("Ace : " + getIntent().getStringExtra("obj"));
        }

//        chkeck_franchisee_fragment->mActivity = this;

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
