package pankajpatel.testproject;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;


/**
 * Created by pankaj.patel on 27-Mar-2017.
 */

public class NetworkUtility {
     static volatile NetworkUtility defaultInstance;
    private Context context;
    private OnNetworkChangedListener listener;



    public interface OnNetworkChangedListener
{
    public void OnConnectionChanged(boolean is_connected);

}

private BroadcastReceiver nReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        //Internet Connected
        if(ninfo != null && listener != null)
        {
            listener.OnConnectionChanged(ninfo.isConnected());
        }
        // Internet Disconnected
        if(ninfo == null && listener != null)
        {
            listener.OnConnectionChanged(false);
        }
    }
};
    public static NetworkUtility getDefault() {
        if (defaultInstance == null) {
            synchronized (NetworkUtility.class) {
                if (defaultInstance == null) {
                    defaultInstance = new NetworkUtility();
                }
            }
        }
        return defaultInstance;
    }





    public  void onStart(final Context context) {
        this.context = context;

 // register network callback
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerNetworkCallback();
        }
        context.registerReceiver(nReceiver,new IntentFilter(NetworkReceiver.NETWORK_CONNECTIVITY_CHANGED));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerNetworkCallback() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback()
            {
                @Override
                public void onAvailable(Network network) {
                    context.sendBroadcast(new Intent(NetworkReceiver.NETWORK_CONNECTIVITY_CHANGED));
                    super.onAvailable(network);
                }

                @Override
                public void onLost(Network network) {
                    context.sendBroadcast(new Intent(NetworkReceiver.NETWORK_CONNECTIVITY_CHANGED));
                    super.onLost(network);
                }
            });
        }

    public void onResume(Context context) {
        onStart(context);
    }
    public void onPause(Context context) {
        onStop(context);
    }
    public void onStop(Context context) {
        this.context = context;
        context.unregisterReceiver(nReceiver);
    }

    public void setConnectionChangedListener(OnNetworkChangedListener listener) {
        this.listener = listener;

    }
}
