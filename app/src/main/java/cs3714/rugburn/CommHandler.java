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
                Log.d("RugBurnMobile", "Handling Message");
                int type = msg.what;
                switch (type) {
                    case 0:
                        Log.d("RugBurnMobile", "Inside Case 1 for Handling message");
                        mainActivity.onWatchClick();
                        break;
                    default:
                        return;
                }
                super.handleMessage(msg);
            }
        };
    }

    private class NumberSenderAsync extends AsyncTask<Integer,Void,Void>{
        @Override
        protected Void doInBackground(Integer... integers) {
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/" + UPDATE_WATCH);
            putDataMapReq.getDataMap().putInt(UPDATE_WATCH, integers[0]);
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            putDataReq.setUrgent();
            PendingResult<DataApi.DataItemResult> pendingResult =
                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
            return null;
        }
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
        Log.d("RugBurnMobile", "It has failed");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.d("RugBurnMobile", "onDataChanged Called");
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/" + UPDATE_WATCH) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    int buttonID = dataMap.getInt(UPDATE_WATCH);
                    //Create a message and send to the Handler
                    Message msg = UIHandler.obtainMessage(0, buttonID);
                    msg.sendToTarget();
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }


}
