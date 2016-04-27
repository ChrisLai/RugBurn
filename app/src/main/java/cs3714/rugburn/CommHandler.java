package cs3714.rugburn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


/**
 * Created by chrislai on 4/25/16.
 */
public class CommHandler implements DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    static android.os.Handler UIHandler=null;
    public static final String UPDATE_WATCH ="update watch";
    GoogleApiClient mGoogleApiClient;
    MainActivity mainActivity;

    public CommHandler(MainActivity ma){
        mainActivity=ma;
        mGoogleApiClient = new GoogleApiClient.Builder(mainActivity)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

        //Communication thread use this to talk to UI thread
        UIHandler = new android.os.Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("RugBurnMobile", "Is connected");
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            Log.d("RugBurnMobile", "Receieved Data from Watch");
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                Log.d("RugBurnMobile", "Receieved Data from Watch");
                mainActivity.onWatchClick();
            }
        }
    }


}
