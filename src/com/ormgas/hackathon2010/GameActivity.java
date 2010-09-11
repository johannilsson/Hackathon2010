package com.ormgas.hackathon2010;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.ormgas.hackathon2010.networking.ServerClient;
import com.ormgas.hackathon2010.networking.ServerClient.GameEvent;
import com.ormgas.hackathon2010.networking.ServerClient.GameEventListener;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.device.Graphics;

public class GameActivity extends RokonActivity implements GameEventListener
{
	public static final float sizeWidth = 800.0f;
	public static final float sizeHeight = 480.0f;
	
	public static SceneHandler sceneHandler;
	
    public void onCreate()
    {
    	Graphics.determine(this);

    	debugMode();
    	forceFullscreen();
    	forceLandscape();
    	setGameSize(sizeWidth, sizeHeight);
    	setDrawPriority(DrawPriority.PRIORITY_NORMAL);
    	setGraphicsPath("textures/");
    	createEngine();
    }
    
    public void onLoadComplete()
    {
        //final ServerClient client = new ServerClient();
        //client.start(this);
        //Log.d("Game", "moving on...");

        //registerReceiver(mUpdateUiReceiver, new IntentFilter(ServerClient.UPDATE_UI));
        
    	Sounds.load();
    	
    	sceneHandler = new SceneHandler(this);
    	
    	sceneHandler.AddScene(SceneHandler.SceneId.StartScene, new StartScene(sceneHandler));
    	sceneHandler.AddScene(SceneHandler.SceneId.GameScene, new GameScene(sceneHandler));
    	
    	sceneHandler.SetScene(SceneHandler.SceneId.GameScene);
    }

    private final BroadcastReceiver mUpdateUiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //GameEvent event = intent.getParcelableExtra(ServerClient.EVENT_EXTRA);
            String event = intent.getStringExtra(ServerClient.EVENT_EXTRA);
            
            
            Log.d("Game", "got event");
            // TODO: handle onEvent...
        }
    };

    @Override
    public void onGameEvent(GameEvent event) {
        // TODO Auto-generated method stub
        
    }
}