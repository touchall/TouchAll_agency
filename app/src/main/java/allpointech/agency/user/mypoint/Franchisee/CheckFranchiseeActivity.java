package allpointech.agency.user.mypoint.Franchisee;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import java.util.List;

import allpointech.agency.utils.NFCUtils;

/**
 * Created by Ace on 2016-12-15.
 */

public class CheckFranchiseeActivity extends BaseAppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback {

    private OnNfcCompleteListener mListener;

    public void setOnNfcCompleteListener(OnNfcCompleteListener listener) {
        mListener = listener;
    }

    protected interface OnNfcCompleteListener {
        void onComplete();
    }

    private static final int MESSAGE_SENT = 1;

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

    private Activity mActivity;

    private boolean _bTest = false;

    private void nfc_init()
    {
        _nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mActivity = this;

        if (_nfcAdapter == null)
        {
            Toast.makeText(this, "This device does not support NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (_nfcAdapter.isEnabled())
        {
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

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    //여기에 딜레이 후 시작할 작업들을 입력
                    _nfcAdapter.enableForegroundDispatch(mActivity, _pendingIntent, _readIntentFilters, null);

                    IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

                    _writeIntentFilters = new IntentFilter[] { tagDetected };
                    _nfcAdapter.enableForegroundDispatch(mActivity, _pendingIntent, _writeIntentFilters, null);
                }
            }, 100);// 0.5초 정도 딜레이를 준 후 시작
        }
    }

    @Override
    protected void initIntentData(Bundle bundle) {

    }

    @Override
    protected BaseFragment initFragment() {
        //return new CheckFranchiseeFragment();

        CheckUseMyPointFragment checkUserMyPointfragment = new CheckUseMyPointFragment();
        if (getIntent() != null) {
            checkUserMyPointfragment.setArguments(getIntent().getExtras());
        }

        return checkUserMyPointfragment;
//
//        CheckFranchiseeFragment chkeck_franchisee_fragment = new CheckFranchiseeFragment();
//        if (getIntent() != null) {
//            chkeck_franchisee_fragment.setArguments(getIntent().getExtras());
//            //SLog.LogD("Ace : " + getIntent().getStringExtra("obj"));
//        }
//
////        chkeck_franchisee_fragment->mActivity = this;
//
//        return chkeck_franchisee_fragment;
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
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(R.string.title_alert_dialog)
//                    .setPositiveButton(R.string.write_button_label,
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id)
//                                {
//                                    _writeMessage();
//                                }
//                            })
//                    .setNegativeButton(R.string.read_button_label,
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id)
//                                {
//                                    _readMessage();
//                                }
//                            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //여기에 딜레이 후 시작할 작업들을 입력
                        _readMessage();
                    }
                }, 100);// 0.5초 정도 딜레이를 준 후 시작

//            Thread t = new Thread(new Runnable(){
//                @Override public void run() {
//                    // UI 작업 수행 X
//                    mHandler.post(new Runnable(){
//                        @Override public void run() {
//                            // UI 작업 수행 O
//                            _readMessage();
//                        }
//                    });
//                }
//            });
//            t.start();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        _enableNdefExchangeMode("");
//        _enableTagWriteMode();

        if (_bTest == false) {
            _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _readIntentFilters, null);

            IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

            _writeIntentFilters = new IntentFilter[]{tagDetected};
            _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _writeIntentFilters, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (_bTest == false)
            _nfcAdapter.disableForegroundDispatch(this);
    }

    private void _readMessage()
    {
        List<String> msgs = NFCUtils.getStringsFromNfcIntent(_intent);

        String[] fields = msgs.get(0).split("\\?");
        if (fields.length > 1) {
            String[] junmuns = msgs.get(0).split("\\=");
            if (junmuns.length > 1 && junmuns[0].contains("point")) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("junmun",msgs.get(0).toString());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        }
        else {
            String[] junmuns = msgs.get(0).split("\\=");
            if (junmuns.length > 1) {
                if (junmuns[0].contains("device_serial")) {
                    String junmun = junmuns[1];
                    CheckUseMyPointFragment fragment = (CheckUseMyPointFragment)getFindFragment(CheckUseMyPointFragment.FRAGMENT_TAG);
                    if (fragment != null) {
                        fragment.gotoFranchisee(junmun);
                    }
                }
                else if (junmuns[0].contains("cancel_point")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("junmun",msgs.get(0).toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                else if (junmuns[0].contains("device_setting")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("junmun",msgs.get(0).toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        }
    }


    private NdefMessage _getNdefMessage(String ndef_message)
    {
        //EditText messageTextField = (EditText) findViewById(R.id.message_text_field);
        String stringMessage = " " + ndef_message;//messageTextField.getText().toString();

        NdefMessage message = NFCUtils.getNewMessage(_MIME_TYPE, stringMessage.getBytes());

        return message;
    }

    public void _enableNdefExchangeMode(String ndef_message)
    {
        // Register callback to set NDEF message
        //_nfcAdapter.setNdefPushMessageCallback(this, this);
        // Register callback to listen for message-sent success
        _nfcAdapter.setOnNdefPushCompleteCallback(this, this);

        _nfcAdapter.setNdefPushMessage(_getNdefMessage(ndef_message), this);
        _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _readIntentFilters, null);
    }

    public void _enableTagWriteMode()
    {
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

        _writeIntentFilters = new IntentFilter[] { tagDetected };
        _nfcAdapter.enableForegroundDispatch(this, _pendingIntent, _writeIntentFilters, null);
    }

    public void _disableNdef() {
        //if (_nfcAdapter != null) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //여기에 딜레이 후 시작할 작업들을 입력
                _nfcAdapter.disableForegroundDispatch(mActivity);
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        //_nfcAdapter.setOnNdefPushCompleteCallback(null, null);
                        setOnNfcCompleteListener(null);

                        _nfcAdapter.setNdefPushMessage(null, mActivity);
                        _nfcAdapter.enableForegroundDispatch(mActivity, _pendingIntent, _readIntentFilters, null);

                        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

                        _writeIntentFilters = new IntentFilter[]{tagDetected};
                        _nfcAdapter.enableForegroundDispatch(mActivity, _pendingIntent, _writeIntentFilters, null);
                    }
                },100);
            }
        }, 100);// 0.5초 정도 딜레이를 준 후 시작

        //}
    }

    /**
     * Implementation for the OnNdefPushCompleteCallback interface
     */
    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }


    /** This handler receives a message from onNdefPushComplete */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    //Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                    if (mListener != null)
                        mListener.onComplete();
                    break;
            }
        }
    };

//
//    @Override
//    public NdefMessage createNdefMessage(NfcEvent event) {
//        return null;
//    }
}
