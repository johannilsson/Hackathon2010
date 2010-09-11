package com.ormgas.hackathon2010;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ormgas.hackathon2010.networking.GameEventFetcher;
import com.ormgas.hackathon2010.networking.ServerClient;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.device.Graphics;

public class GameActivity extends RokonActivity
{
	private final static String TAG = GameActivity.class.getSimpleName();
	public static final float sizeWidth = 800.0f;
	public static final float sizeHeight = 480.0f;
	
	public static SceneHandler sceneHandler;
	private ServerClient client;
	
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
    
    public void onDestroy() {
    	super.onDestroy();
    }
    
    public void onLoadComplete()
    {
    	Sounds.load();
    	
    	sceneHandler = new SceneHandler(this);
    	
    	sceneHandler.AddScene(SceneHandler.SceneId.StartScene, new StartScene(sceneHandler));
    	sceneHandler.AddScene(SceneHandler.SceneId.GameScene, new GameScene(sceneHandler));
    	
    	sceneHandler.SetScene(SceneHandler.SceneId.StartScene);
    	
    	/*
        final ServerClient client = new ServerClient();
        GameEventFetcher.initInstance(mHandler, client);
        GameEventFetcher fetcher = GameEventFetcher.getInstance();
        fetcher.start();
        */

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("GAMEEEEEEEEEEEE", "got event!???");
        }
    };
}