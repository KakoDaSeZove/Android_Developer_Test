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

    /**
     * Backend instance
     */
    private BackendEmulator backend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Get backend reference
        backend = BackendEmulator.getInstance();

        //TODO DO STUFF

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


        //Try to play some video
        //Try to play actual channel
        //Try to implement info banner for current channel
        //Try to implement next/previous channel
        //Try to implement channel list
        //Try to implement time out on info banner
        //Try to implement favorite list
        //Try to change application icon
        //Try to remember last watched channel
        //Try to remember favorite list

    }
}
