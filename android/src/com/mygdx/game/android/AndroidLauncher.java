package com.mygdx.game.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyImageViewer;

public class AndroidLauncher extends AndroidApplication implements MyImageViewer.CallbackInterface {

    private static final int SELECT_PHOTO = 100;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		MyImageViewer imageViewer = new MyImageViewer(this);
		initialize(imageViewer, config);
        //imageViewer.setStartActivityCallback(this);
	}

	@Override
	public String StartImageBrowser() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
//        Intent i = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
        return null;
	}
}
