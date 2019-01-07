package org.charitygo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ServiceCompat;
import android.widget.Toast;

import org.charitygo.StepService;

public class NotifyBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String dismiss = intent.getStringExtra("dismiss");

        if(dismiss.equals("dismiss")) {
            // send back to your class
            Intent newIntent = new Intent(context, StepService.class);

            context.stopService(newIntent);
        }
    }
}
