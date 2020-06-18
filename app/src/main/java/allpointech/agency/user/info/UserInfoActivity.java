package allpointech.agency.user.info;

import android.os.Bundle;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

/**
 * Created by daze on 2016-11-15.
 */

public class UserInfoActivity extends BaseAppCompatActivity {

    public static String userMobile;
    public static String userPassword;
    public static String userName;
    public static String userEmail;
    public static String userBirth;
    public static String userArea;
    public static String userGender;
    public static String userBank;
    public static String userAccount;
    public static String userHolder;
//    public static String userFcm;
//    public static String userDeviceId;

    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        return new UserInfoFragment1();
    }

    @Override
    protected void initRequest() {

    }

    @Override
    protected void initDefaultSet() {

    }
}
