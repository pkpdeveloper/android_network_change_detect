package pankajpatel.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {

    public static final String NETWORK_CONNECTIVITY_CHANGED = "network_connectivity_changed";

    @Override
    public void onReceive(Context context, Intent intent) {
        // send custom broadcastg
        context.sendBroadcast(new Intent(NetworkReceiver.NETWORK_CONNECTIVITY_CHANGED));
    }
}
