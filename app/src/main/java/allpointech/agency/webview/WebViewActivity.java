package allpointech.agency.webview;

import android.os.Bundle;
import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by jay on 2019-02-25.
 */

public class WebViewActivity extends BaseAppCompatActivity {
    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        WebViewFragment fragment = new WebViewFragment();
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