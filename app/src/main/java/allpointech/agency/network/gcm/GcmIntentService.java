package allpointech.agency.network.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tuna.utils.SLog;

import org.json.JSONException;
import org.json.JSONObject;

import allpointech.agency.intro.IntroActivity;
import allpointech.agency.network.http.json.JSONParser;
import allpointech.agency.utils.TNPreference;


public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        SLog.LogV("messageType " + messageType);

        Bundle data = intent.getExtras();
        if (!data.isEmpty()) {
            /* Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
			 * any message types you're not interested in, or that you don't recognize.
			 */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                SLog.LogW("Send error: " + data.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                SLog.LogW("Deleted messages on server: " + data.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                SLog.LogD("## Gcm Service:" + getBaseContext().getPackageName() + ", " + data.toString());
            }
            String str = data.getString("data");
            String msg = "";
            if (str != null && !"".equalsIgnoreCase(str)) {
                try {
                    JSONObject obj = new JSONObject(str);
                    msg = JSONParser.getString(obj, "msg");
                    if (JSONParser.getInt(obj, "index", 0) == 0) {
                        TNPreference.setNoticeDate(this, 0);
                    }
                    SLog.LogD("Ace : " + str);
                    TNNotification.makeNotification(this, null, msg, IntroActivity.class, data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}