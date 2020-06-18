package allpointech.agency.login;

import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by jay on 2018. 6. 24..
 */

public class ShowAgreeActivity extends BaseAppCompatActivity {
    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        ShowAgreeFragment fragment = new ShowAgreeFragment();
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
}