package allpointech.agency.user.mypoint.processPoint;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.tuna.ui.activity.BaseAppCompatActivity;
import com.tuna.ui.fragment.BaseFragment;

import java.util.List;

import allpointech.agency.utils.NFCUtils;

/**
 * Created by Ace on 2016-12-15.
 */

public class InsertPointActivity extends BaseAppCompatActivity {

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

        InsertPointFragment fragment = new InsertPointFragment();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent in = getIntent();
        Uri data = in.getData();
        if (data != null) {
            String test = "";
            String test1 = "";
            String test2 = "";
            String test3 = "";
        }

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

        //Toast.makeText(this, "Message : " + msgs.get(0), Toast.LENGTH_LONG).show();

        String[] fields = msgs.get(0).split("\\?");
        if (fields.length > 1) {
            String[] junmuns = msgs.get(0).split("\\=");
            if (junmuns.length > 1 && junmuns[0].contains("point")) {
                String junmun = junmuns[1];

//                byte[] bytes = hexStringToByteArray(junmun);
//
//                Aria aria = new Aria("KopYecatainUTa%4el82ro#$llcL&HAI");
//                String decryt_junmun = aria.Decrypt(bytes);
//
                InsertPointFragment fragment = (InsertPointFragment)getFindFragment(InsertPointFragment.FRAGMENT_TAG);
                if (fragment != null) {
                    fragment.insertPoint(junmun);
                }

            }
        }
        else {
            String[] junmuns = msgs.get(0).split("\\=");
            if (junmuns.length > 1) {
                if (junmuns[0].contains("device_serial")) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("junmun",msgs.get(0).toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
//            case REQUEST_INAPP:
//                if (getFindFragment(UserPaymentFragment.FRAGMENT_TAG) != null) {
//                    UserPaymentFragment fragment = (UserPaymentFragment) getFindFragment(UserPaymentFragment.FRAGMENT_TAG);
//                    fragment.onInappResult(requestCode, resultCode, data);
//                }
//                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
