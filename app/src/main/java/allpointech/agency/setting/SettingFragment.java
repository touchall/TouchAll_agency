package allpointech.agency.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import allpointech.agency.R;

/**
 * Created by Ace on 2016-12-26.
 */

public class SettingFragment extends BaseFragment {
    public static final String FRAGMENT_TAG = SettingFragment.class.getSimpleName();

    private Toolbar mToolbar;

    @Override
    protected void BundleData(Bundle bundle) {
        if (getArguments() != null) {
        }
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initLayout(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((BaseAppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        replaceAnimationTagFragment(R.id.setting_contents, new SettingMainFragment(), SettingMainFragment.FRAGMENT_TAG, 0, 0);
    }

    @Override
    protected void initRequest() {

    }
}
