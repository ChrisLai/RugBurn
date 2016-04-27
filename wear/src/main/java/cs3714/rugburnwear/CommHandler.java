package cs3714.rugburnwear;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
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
        GoogleApiClient.OnConnectionFailedListener{

    static android.os.Handler UIHandler=null;

    public static final String UPDATE_WATCH ="update watch";
    GoogleApiClient mGoogleApiClient;
    MainActivity mainActivity;

    public CommHandler(MainActivity ma) {
        mainActivity = ma;
        mGoogleApiClient = new GoogleApiClient.Builder(mainActivity)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

        //This takes data from the watch and render it on the android wear GUI
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
        Log.d("RugBurnWear", "Is connected");
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            Log.d("RubBurnWear", "Checking");
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                String[] buttonID = dataMap.getStringArray(UPDATE_WATCH);
                //Create a message and send to the Handler
                Message msg = UIHandler.obtainMessage(0, buttonID);
                msg.sendToTarget();
                Log.d("RubBurnWear", "Stuff was sent");
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Connection Failed:", "" + connectionResult);
    }

    private class SenderAsync extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... string) {
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/" + UPDATE_WATCH);
            putDataMapReq.getDataMap().putStringArray(UPDATE_WATCH, string);
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            PendingResult<DataApi.DataItemResult> pendingResult =
                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
            Log.d("RubBurnWear", "Do in Background");
            return null;
        }
    }

    //This send a string array to the android wear
    public void alertPhone(){
        String[] dataToBeSent = {"Yo Man"};
        new SenderAsync().execute(dataToBeSent);
    }
}
