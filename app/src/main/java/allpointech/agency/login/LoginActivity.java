package allpointech.agency.login;

import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by daze on 2016-11-15.
 */

public class LoginActivity extends BaseAppCompatActivity {
    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        return new LoginFragment();
    }

    @Override
    protected void initRequest() {

    }

    @Override
    protected void initDefaultSet() {

    }
}
