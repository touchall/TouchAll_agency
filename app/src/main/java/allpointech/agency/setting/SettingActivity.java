package allpointech.agency.setting;

import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by Ace on 2016-12-26.
 */

public class SettingActivity extends BaseAppCompatActivity {
    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        SettingFragment fragment = new SettingFragment();
        if (getIntent() != null) {
            fragment.setArguments(getIntent().getExtras());
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
