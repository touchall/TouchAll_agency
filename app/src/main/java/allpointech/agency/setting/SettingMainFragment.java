package allpointech.agency.setting;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import java.io.File;

import allpointech.agency.R;
import allpointech.agency.utils.TNPreference;

/**
 * Created by Ace on 2016-12-26.
 */

public class SettingMainFragment extends BaseFragment implements View.OnClickListener, BaseAppCompatActivity.onKeyBackPressedListener {
    public static final String FRAGMENT_TAG = SettingMainFragment.class.getSimpleName();
    private ActionBar mActionBar;

    private TextView mBtnUpdateApk;
    private ImageView mSwitchAutoLogin;
    private TextView mBtnBluetoothSetting;
    private TextView mBtnNFCSetting;
    private ImageView mSwitchLocation;
    private ImageView mSwitchPush;
    //private LinearLayout mBtnLogout;
    private LinearLayout mFWUpgradeLogout;

    private NfcAdapter nfcAdapter;

    @Override
    protected void BundleData(Bundle bundle) {
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            //mMemberType = getArguments().getString(TNConstants.MemberType.TYPE);
        }
        ((BaseAppCompatActivity) getActivity()).setOnKeyBackPressedListener(this);
    }

    @Override
    protected int inflateLayout() {
        return R.layout.fragment_setting_main;
    }

    @Override
    protected void initLayout(View view) {
        mActionBar = ((BaseAppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            //mActionBar.setTitle(getString(R.string.toolbar_mypage_modify));
            mActionBar.setTitle("설정");
        }

        mBtnUpdateApk = (TextView) view.findViewById(R.id.btn_update_apk);
        mBtnUpdateApk.setOnClickListener(this);
        mSwitchAutoLogin = (ImageView) view.findViewById(R.id.switch_auto_login);
        mSwitchAutoLogin.setOnClickListener(this);
        mBtnBluetoothSetting = (TextView) view.findViewById(R.id.btn_bluetooth_setting);
        mBtnBluetoothSetting.setOnClickListener(this);
        mBtnNFCSetting = (TextView) view.findViewById(R.id.btn_nfc_setting);
        mBtnNFCSetting.setOnClickListener(this);
        mSwitchLocation = (ImageView) view.findViewById(R.id.switch_location_on);
        mSwitchLocation.setOnClickListener(this);
        mSwitchPush = (ImageView) view.findViewById(R.id.switch_push_on);
        mSwitchPush.setOnClickListener(this);
//        mBtnLogout = (LinearLayout) view.findViewById(R.id.btn_logout);
//        mBtnLogout.setOnClickListener(this);
        mFWUpgradeLogout = (LinearLayout) view.findViewById(R.id.btn_firmware_update);
        mFWUpgradeLogout.setOnClickListener(this);

        mSwitchAutoLogin.setSelected(TNPreference.getAutoLogin(getActivity()));
        mSwitchLocation.setSelected(true);
        mSwitchPush.setSelected(true);
    }

    @Override
    protected void initRequest() {
//        if (TNConstants.MemberType.USER.equalsIgnoreCase(TNPreference.getMemberType(getActivity()))) {
//            mBtnModifyCountry.setVisibility(View.VISIBLE);
//        } else if (TNConstants.MemberType.INTERPRETER.equalsIgnoreCase(TNPreference.getMemberType(getActivity()))) {
//            mBtnModifyCountry.setVisibility(View.GONE);
//        }
    }


    public void sendFile(View view) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());

        // Check whether NFC is enabled on device
        if(!nfcAdapter.isEnabled()){
            // NFC is disabled, show the settings UI
            // to enable NFC
            Toast.makeText(getActivity(), "Please enable NFC.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        // Check whether Android Beam feature is enabled on device
        else if(!nfcAdapter.isNdefPushEnabled()) {
            // Android Beam is disabled, show the settings UI
            // to enable Android Beam
            Toast.makeText(getActivity(), "Please enable Android Beam.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        else {
            // NFC and Android Beam both are enabled

            // File to be transferred
            // For the sake of this tutorial I've placed an image
            // named 'wallpaper.png' in the 'Pictures' directory
            String fileName = "wallpaper.png";

            // Retrieve the path to the user's public pictures directory
            File fileDirectory = Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);

            // Create a new file using the specified directory and name
            File fileToTransfer = new File(fileDirectory, fileName);
            fileToTransfer.setReadable(true, false);

            nfcAdapter.setBeamPushUris(
                    new Uri[]{Uri.fromFile(fileToTransfer)}, getActivity());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_apk:
                break;
            case R.id.switch_auto_login:
                mSwitchAutoLogin.setSelected(true);
                TNPreference.setAutoLogin(getActivity(), mSwitchAutoLogin.isSelected());
                //mSwitchAutoLogin.setSelected(!mSwitchAutoLogin.isSelected());
                //TNPreference.setAutoLogin(getActivity(), mSwitchAutoLogin.isSelected());
                break;
            case R.id.btn_bluetooth_setting:
            {
                startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
            }
                break;
            case R.id.btn_nfc_setting:
            {
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                } else {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
            }
                break;
            case R.id.switch_location_on:
                //mSwitchPush.setSelected(!mSwitchPush.isSelected());
                mSwitchLocation.setSelected(true);
                break;
            case R.id.switch_push_on:
                //mSwitchPush.setSelected(!mSwitchPush.isSelected());
                mSwitchPush.setSelected(true);
                break;
            /*
            case R.id.btn_logout:
            {

                MsgDialog dialog = new MsgDialog();
                Bundle b = new Bundle();
                b.putString("dialog_msg", getResources().getString(R.string.msg_logout));
                dialog.setArguments(b);
                dialog.setOnResultListener(new BaseDialogFragment.OnResultListener() {
                    @Override
                    public void onDialogResult(Object... objects) {
                        boolean isOk = (boolean) objects[0];
                        if (isOk) {
                            Intent i = new Intent(getActivity(), IntroActivity.class);
                            TNPreference.clearUserInfo(getActivity());
                            getActivity().finish();
                            startActivity(i);
                        }
                    }
                });
                dialog.show(getFragmentManager(), "dialog");
            }
                break;
                */
            case R.id.btn_firmware_update:
            {
                sendFile(view);
            }
                break;
        }
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
