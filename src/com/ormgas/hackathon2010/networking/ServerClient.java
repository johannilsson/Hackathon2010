package com.ormgas.hackathon2010.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


/**
 *
create player/bullet:
curl -d "x=0&y=0&heading=0&speed=0&hp=100" "http://vabba.nu:1337/players.json"
curl -d "x=0&y=0&heading=0&speed=0&hp=100" "http://vabba.nu:1337/bullets.json"
update player/bullet positions:
curl "http://vabba.nu:1337/update_player_position/1?x=3&y=5&heading=2&speed=3&hp=90"
curl "http://vabba.nu:1337/update_bullet_position/1?x=3&y=5&heading=2&speed=3&hp=90"
events:
curl "http://vabba.nu:1337/events"
delete player/bullet
http://vabba.nu:1337/delete_bullet/8
http://vabba.nu:1337/delete_player/8
 */
public class ServerClient {
    static final String TAG = "ServerClient";
    public static String UPDATE_UI = "com.ormgas.hackathon2010.action.UPDATE_UI";
    static final String BASE_URL = "http://vabba.nu:1337";
    public static final String EVENT_EXTRA = "com.ormgas.hackathon2010.EVENT_EXTRA";

    private GameEventListener mGameEventListener;

    public void createPlayer() {
        
    }
    
    public void updatePlayer() {
        
    }
    
    public void createBullet() {
        
    }
    
    public void updateBullet() {
        
    }
    
    public void sendEvent(final GameEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final HttpPost post = new HttpPost(BASE_URL + "/event/");
                final HttpParams params = new BasicHttpParams();
                params.setIntParameter("id", event.id);
                post.setParams(params);
                HttpResponse response;
                try {
                    response = HttpManager.execute(post);
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        return; // ignore errors?
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            
        }).start();
    }

    public void fetchEvents() {
        try {
            URI uri = new URI(BASE_URL + "/events");
            HttpGet method = new HttpGet(uri);
            final HttpParams getParams = new BasicHttpParams();
            method.setParams(getParams);
            final HttpResponse response = HttpManager.execute(method);
            Log.d(TAG, "got response: " + response.getStatusLine().getStatusCode());
            
            // TODO: parse response...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void listenForEvent() {
        new Thread(new Runnable() {
            public void run() {
                boolean listen = true;

                while (listen) {
                    Log.d(TAG, "listenForEvent");
                    try {
                        Thread.sleep(1000);

                        URI uri = new URI(BASE_URL + "/events");
                        HttpGet method = new HttpGet(uri);
                        final HttpParams getParams = new BasicHttpParams();
                        method.setParams(getParams);
                        final HttpResponse response = HttpManager.execute(method);
                        Log.d(TAG, "got response: " + response.getStatusLine().getStatusCode());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void broadcactEvent(final GameEvent event) {
        /*Intent mUpdateUiIntent = new Intent(UPDATE_UI);
        mUpdateUiIntent.putExtra(EVENT_EXTRA, "event");
        mContext.sendBroadcast(mUpdateUiIntent);*/
        mGameEventListener.onGameEvent(event);
    }
    
    public void start(GameEventListener listener) {
        mGameEventListener = listener;

        listenForEvent();
    }

    public void close() {

    }

    public interface GameEventListener {
        public void onGameEvent(GameEvent event);
    }
    
    public class GameEvent implements Parcelable {
        public int id;
        public int ownerId;
        public float x;
        public float y;
        public float heading;
        public float velocity;
        public int time;

        @Override
        public int describeContents() {
            // TODO Auto-generated method stub
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            // TODO Auto-generated method stub
            
        }
    }
    
    
}
