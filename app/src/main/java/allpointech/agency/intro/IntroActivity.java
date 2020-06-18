package allpointech.agency.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;
import com.tuna.utils.SLog;

/**
 * Created by daze on 2016-11-14.
 */

public class IntroActivity extends BaseAppCompatActivity {

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SLog.LogD("Ace : new intent");
//        if (AppInfo.mMemberIdx != null) {
//            AppInfo.setPendingNotificationsCount(0);
//        }
        SLog.LogD("Ace : " + intent.getStringExtra("obj"));
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(intent.getExtras());
        ft.replace(getMainViewId(), fragment, IntroFragment.FRAGMENT_TAG);
        ft.commit();
        fm.executePendingTransactions();
    }


    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        IntroFragment fragment = new IntroFragment();
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                fragment.setArguments(getIntent().getExtras());
            }
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
