package org.tbyte.txmail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dgondos on 6/05/15.
 */
public class SyncService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Hitting alarm", Toast.LENGTH_SHORT).show();
        Log.d("txmail sync", "alarm het");
    }
}
