package com.rtrk.android.test.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.VideoView;

import com.rtrk.android.test.R;
import com.rtrk.android.test.sdk.BackendEmulator;

/**
 * Main activity
 *
 * @author ADD AUTHOR
 */
public class SplashScreenActivity extends Activity {

    // in ms
    public static final int kSPLASH_SCREEN_BOOT_TIME = 3 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Try to implement splash screen
        setContentView(R.layout.splashscreen);

        //avoid blocking UI thread
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                try {
                    Thread.sleep(kSPLASH_SCREEN_BOOT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(SplashScreenActivity.this, PlaybackActivity.class);
                startActivity(i);

                return null;
            }
        };

        task.execute();
    }
}
