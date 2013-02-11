package com.ucr.scottytalk;
/*
import android.os.Build;
import com.opentok.*;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import java.io.IOException;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VideoChat extends Activity implements SurfaceHolder.Callback{
 
Camera camera;
SurfaceView surfaceView;
SurfaceHolder surfaceHolder;
boolean cameraview = false;
LayoutInflater inflater = null;
 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.activity_video_chat);
	     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
 
	     getWindow().setFormat(PixelFormat.UNKNOWN);
	     surfaceView = (SurfaceView)findViewById(R.id.cameraview);
	     surfaceHolder = surfaceView.getHolder();
	     surfaceHolder.addCallback(this);
	     surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
 
	     inflater = LayoutInflater.from(getBaseContext()); 
	 	}
 
 	@Override
 	public void surfaceChanged(SurfaceHolder holder, int format, int width,
 	int height) {
		if (camera != null){
		try {
		camera.setPreviewDisplay(surfaceHolder);
		camera.startPreview();
		cameraview = true;
		} catch (IOException e) {
		e.printStackTrace();
				}
			}
	}
 
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		int cameraCount = Camera.getNumberOfCameras() -1;
		camera = Camera.open(cameraCount);
	}
 
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	camera.stopPreview();
	camera.release();
	camera = null;
	cameraview = false;
	}
	}

*/



import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.opentok.Publisher;
import com.opentok.Session;
import com.opentok.Stream;
import com.opentok.Subscriber;




public class VideoChat extends Activity implements Publisher.Listener, Session.Listener, Callback {
	ExecutorService executor;
	SurfaceView publisherView;
	SurfaceView subscriberView;
	Camera camera;
	Publisher publisher;
	Subscriber subscriber;
	private Session session;
	private WakeLock wakeLock;
	boolean cameraview;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_chat);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		publisherView = (SurfaceView)findViewById(R.id.publisherview);
		subscriberView = (SurfaceView)findViewById(R.id.subscriberview);
		
		// Although this call is deprecated, Camera preview still seems to require it :-\
		publisherView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// SurfaceHolders are not initially available, so we'll wait to create the publisher
		publisherView.getHolder().addCallback(this);

		// A simple executor will allow us to perform tasks asynchronously.
		executor = Executors.newCachedThreadPool();

		// Disable screen dimming
		PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
						"Full Wake Lock");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onStop() {
		super.onStop();

		if (wakeLock.isHeld()) {
			wakeLock.release();
		}
		// Release the camera when the application is being destroyed, lest we can't acquire it again later.
		if (null != camera) camera.release();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!wakeLock.isHeld()) {
			wakeLock.acquire();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (wakeLock.isHeld()) {
			wakeLock.release();
		}
	}


	 //* Invoked when Our Publisher's rendering surface comes available.

	 
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		if (publisher == null) {
			if (camera != null){
				try {
					camera.setPreviewDisplay(publisherView.getHolder());
					camera.startPreview();
					cameraview = true;
					} 
				catch (IOException e) {
					e.printStackTrace();
				}
				try {
					String APIKey = "22725422";
					String sessionID = "2_MX4yMjcyNTQyMn5-RnJpIEZlYiAwOCAwMTo0Njo0MCBQU1QgMjAxM34wLjcxNDI0NDR-";
					String token = 	"T1==cGFydG5lcl9pZD0gMjI3MjU0MjImc2lnPTM3NjI2NjdiZGNkNTkzMzUyZjNlYjAzNzdlNDcwZmExYmU5ZWJhZWY6c2Vzc2lvbl9pZD0yX01YNHlNamN5TlRReU1uNS1SbkpwSUVabFlpQXdPQ0F3TVRvME5qbzBNQ0JRVTFRZ01qQXhNMzR3TGpjeE5ESTBORFItJmNyZWF0ZV90aW1lPTEzNjAzMTY4NTAmZXhwaXJlX3RpbWU9MTM2MDQwMzI1MCZyb2xlPXB1Ymxpc2hlciZjb25uZWN0aW9uX2RhdGE9Jm5vbmNlPTQwODk1Nw==";
					session = Session.newInstance(getApplicationContext(), 
							sessionID,token, APIKey, VideoChat.this);
					session.connect();

				} catch (Throwable t) {
					t.printStackTrace();
				}
				
			}
			
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		int cameraCount = Camera.getNumberOfCameras() -1;
		camera = Camera.open(cameraCount);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		camera.stopPreview();
		camera.release();
		camera = null;
		//cameraview = false;

	}

	@Override
	public void onSessionConnected() {
		
		publisher = session.createPublisher(camera, publisherView.getHolder());
		publisher.connect();
	}

	@Override
	public void onSessionDidReceiveStream(final Stream stream) {		
		if (publisher.getStreamId().equals(stream.getStreamId())) {
			subscriber = session.createSubscriber(subscriberView, stream);
			subscriber.connect();
		}
		
	}

	@Override
	public void onPublisherStreamingStarted() {
		Log.i("hello-world", "publisher is streaming!");
	}

	@Override
	public void onPublisherFailed() {
		Log.e("hello-world", "publisher failed!");
	}

	@Override
	public void onSessionDidDropStream(Stream stream) {
		Log.i("hello-world", String.format("stream %d dropped", stream.toString()));
	}

	@Override
	public void onSessionError(Exception cause) {
		Log.e("hello-world", "session failed! "+cause.toString());	
	}

	@Override
	public void onSessionDisconnected() {
		Log.i("hello-world", "session disconnected");	
	}

	@Override
	public void onPublisherDisconnected() {
		Log.i("hello-world", "publisher disconnected");	

	}

}

