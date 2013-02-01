package com.ucr.scottytalk;

import android.os.Build;
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
 
 /** Called when the activity is first created. */
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
		// TODO Auto-generated catch block
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
	// TODO Auto-generated method stub
	camera.stopPreview();
	camera.release();
	camera = null;
	cameraview = false;
	}
	}
