package pankajpatel.testproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtility.getDefault().setConnectionChangedListener(new NetworkUtility.OnNetworkChangedListener() {
            @Override
            public void OnConnectionChanged(boolean is_connected) {
                Toast.makeText(MainActivity.this,"Network Connectivity Changed:- "+(is_connected?"Connected":"Disconnected"),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
     NetworkUtility.getDefault().onStop(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        NetworkUtility.getDefault().onStart(this);
        super.onStart();
    }
}
