package allpointech.agency.network.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.tuna.utils.SLog;


public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	
    @Override
    public void onReceive(Context context, Intent intent) {
    	Bundle data = intent.getExtras();
		SLog.LogD("## Gcm Receiver:" + context.getPackageName() + ", " + data.toString());
    	
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, intent.setComponent(comp));
        setResultCode(Activity.RESULT_OK);
    }
    
}
