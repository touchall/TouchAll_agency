package allpointech.agency.user.setup;

import android.content.Intent;
import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by Ace on 2016-12-15.
 */

public class DeviceSetupResultActivity extends BaseAppCompatActivity {

    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {

        DeviceSetupResultFragment fragment = new DeviceSetupResultFragment();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
