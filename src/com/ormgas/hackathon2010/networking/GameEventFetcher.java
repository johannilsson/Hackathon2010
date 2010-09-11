package com.ormgas.hackathon2010.networking;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

public class GameEventFetcher {
    private static final String TAG = "GameEventFetcher";
    private static int WHAT_EVENT = 1;
    private static GameEventFetcher sInstance; 
    final HandlerThread handlerThread;
    final Handler runnableHandler;
    private final Handler mHandler;

    private final Runnable mFetchMessages = new Runnable() {
        public void run() {
            fetchEvents();
        }
    };
    private ServerClient mClient;
    
    public GameEventFetcher(Handler handler, ServerClient client) {
        if (handler != null) {
            mHandler = handler;
        } else {
            mHandler = new Handler();
        }

        mClient = client;
        
        handlerThread = new HandlerThread("runnables");
        handlerThread.start();
        runnableHandler = new Handler(handlerThread.getLooper());
    }

    public static GameEventFetcher getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("not initialized..");
        }
        return sInstance;
    }

    public static void initInstance(Handler handler, ServerClient client) {
        sInstance = new GameEventFetcher(handler, client);
    }

    public void start() {
        runnableHandler.post(mFetchMessages);
    }

    public void stop() {
        runnableHandler.removeCallbacks(mFetchMessages);
    }
    
    public void fetchEvents() {
        mClient.fetchEvents();

        mHandler.obtainMessage(WHAT_EVENT, "event").sendToTarget();

        runnableHandler.postDelayed(mFetchMessages, 1000);
    }
}
