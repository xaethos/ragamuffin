package net.xaethos.ragamuffin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import net.xaethos.ragamuffin.httpd.AndroidHTTPD;
import net.xaethos.ragamuffin.httpd.NanoHTTPDPooled;

import java.io.IOException;
import java.util.Properties;


public class HttpService extends Service implements AndroidHTTPD.RequestHandler {

    private static final String TAG = "Ragamuffin";

    private AndroidHTTPD mHttpDaemon;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            if (mHttpDaemon == null) {
                Log.d(TAG, "New HTTP server instance");
                mHttpDaemon = new AndroidHTTPD(this, 36778, null, this);
            }
            Log.d(TAG, "Start HTTP server");
            mHttpDaemon.stop();
            mHttpDaemon.startServer();
        } catch (IOException e) {
            Log.e(TAG, "Error starting HTTP server", e);
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "going down");
        mHttpDaemon.stop();
        super.onDestroy();
    }

    @Override
    public NanoHTTPDPooled.Response onRequestReceived(String uri, String method, Properties header, Properties parms, Properties files) {
        return new NanoHTTPDPooled.Response("200", "text/plain", "It works!");
    }
}
