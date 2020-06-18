package allpointech.agency.user;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;
import com.tuna.utils.SLog;

import java.util.List;

import allpointech.agency.utils.NFCUtils;

/**
 * Created by daze on 2016-11-15.
 */

public class UserMainActivity extends BaseAppCompatActivity {

    /*
    *  NFC
    */

    private NfcAdapter _nfcAdapter;
    private PendingIntent _pendingIntent;
    private IntentFilter[] _readIntentFilters;
    private IntentFilter[] _writeIntentFilters;
    private Intent _intent;
    //
    private final String _MIME_TYPE = "text/plain";
    //private final String _MIME_TYPE = "*/*";
    private String mNdefMessgae = "";

    private boolean _bTest = false;

    private Activity mActivity;

    private void nfc_init()
    {
        _nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mActivity = this;

        if (_nfcAdapter == null)
        {
            Toast.makeText(this, "This device does not support NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        _pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try
        {
            ndefDetected.addDataType(_MIME_TYPE);
        } catch (IntentFilter.MalformedMimeTypeException e)
        {
            Log.e(this.toString(), e.getMessage());
        }

        _readIntentFilters = new IntentFilter[] { ndefDetected };

        if (_nfcAdapter.isEnabled()) {
           // _enableNdef();
        }
        else {
            Toast.makeText(this, "Please NFC turn on.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initIntentData(Bundle bundle) {
        setResult(RESULT_OK);
    }

    @Override
    protected BaseFragment initFragment() {
        UserMainFragment fragment = new UserMainFragment();
        if (getIntent() != null) {
            fragment.setArguments(getIntent().getExtras());
            SLog.LogD("Ace : " + getIntent().getStringExtra("obj"));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (_bTest == false)
            nfc_init();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if (_bTest == false) {
            _intent = intent;

            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
                _readMessage();
            }

            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //여기에 딜레이 후 시작할 작업들을 입력
                        _readMessage();
                    }
                }, 100);// 0.5초 정도 딜레이를 준 후 시작
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (_bTest == false) {
            mActivity = this;
            _enableNdef();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (_bTest == false)
            _disableNdef(false);
    }

    private void _readMessage()
    {
        List<String> msgs = NFCUtils.getStringsFromNfcIntent(_intent);

        //Toast.makeText(this, "Message : " + msgs.get(0), Toast.LENGTH_LONG).show();

        UserMainFragment mainFragment = (UserMainFragment) getFindFragment(UserMainFragment.FRAGMENT_TAG);
        if (mainFragment != null) {
            mainFragment.processNFC(msgs.get(0));
        }
    }


    private NdefMessage _getNdefMessage(String ndef_message)
    {
        //EditText messageTextField = (EditText) findViewById(R.id.message_text_field);
        String stringMessage = " " + ndef_message;//messageTextField.getText().toString();

        NdefMessage message = NFCUtils.getNewMessage(_MIME_TYPE, stringMessage.getBytes());

        return message;
    }
//
//    public void _enableNdefExchangeMode(String ndef_message)
//    {
//        _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _readIntentFilters, null);
//    }
//
//    public void _enableTagWriteMode()
//    {
//        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
//
//        _writeIntentFilters = new IntentFilter[] { tagDetected };
//        _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _writeIntentFilters, null);
//    }

    public void _setNdefMessage(String ndef_message) {
        mNdefMessgae = ndef_message;
    }

    public void _enableNdef() {
//        //new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run() {
//
//            }
//        },100);

        //여기에 딜레이 후 시작할 작업들을 입력
        if (mNdefMessgae != null && mNdefMessgae.length() > 0)
            _nfcAdapter.setNdefPushMessage(_getNdefMessage(mNdefMessgae), mActivity);
        _nfcAdapter.enableForegroundDispatch(mActivity, _pendingIntent, _readIntentFilters, null);

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

        _writeIntentFilters = new IntentFilter[] { tagDetected };
        _nfcAdapter.enableForegroundDispatch(mActivity, _pendingIntent, _writeIntentFilters, null);
    }

    public void _disableNdef(boolean bEnableOn) {
        boolean bNoewOn = bEnableOn;
        _nfcAdapter.disableForegroundDispatch(mActivity);
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                //여기에 딜레이 후 시작할 작업들을 입력
//                _nfcAdapter.disableForegroundDispatch(mActivity);
//                //if (bNoewOn)
//                //    _enableNdef();
//            }
//        }, 100);// 0.5초 정도 딜레이를 준 후 시작
    }
}
