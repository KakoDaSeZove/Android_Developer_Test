package com.rtrk.android.test.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rtrk.android.test.R;
import com.rtrk.android.test.sdk.BackendEmulator;
import com.rtrk.android.test.sdk.models.ChannelEntity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlaybackActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    public static final String LOG_TAG = "PlaybackActivity";
    // in ms
    public static final int kINFO_BANNER_ACTIVE_TIME = 10 * 1000;


    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder = null;

    /**
     * Backend instance
     */
    private BackendEmulator backend;

    private LinearLayout mChannelTitle;
    private TextView mChannelName;
    private TextView mChannelNumber;
    private ImageView mChannelIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get backend reference
        backend = BackendEmulator.getInstance(getApplicationContext());
        setContentView(R.layout.activity_playback);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mChannelName = findViewById(R.id.channel_name);
        mChannelNumber = findViewById(R.id.channel_number);
        mChannelTitle = findViewById(R.id.channel_title);
        mChannelIcon = findViewById(R.id.channel_icon);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDisplay(holder);
        try {
            mMediaPlayer.setDataSource(backend.getActiveChannel().getUrl());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();

        Log.i(LOG_TAG, "Current channel: " + backend.getActiveChannel().getId());

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_CHANNEL_UP:
            case KeyEvent.KEYCODE_DPAD_UP:
                releaseMediaPlayer();

                try {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDisplay(mSurfaceHolder);

                    ChannelEntity nextChannel = backend.getChannel((backend.getActiveChannelIndex() + 1) % backend.getChannels().size());
                    backend.changeChannel(nextChannel);

                    mMediaPlayer.setDataSource(backend.getActiveChannel().getUrl());
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mMediaPlayer.setOnPreparedListener(this);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mChannelTitle.setVisibility(View.GONE);

                return true;
            case KeyEvent.KEYCODE_CHANNEL_DOWN:
            case KeyEvent.KEYCODE_DPAD_DOWN:

                releaseMediaPlayer();

                try {
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDisplay(mSurfaceHolder);

                    int prevChannelIndex = backend.getActiveChannelIndex() - 1;
                    if (prevChannelIndex < 0) {
                        prevChannelIndex = backend.getChannelsCount() + prevChannelIndex;
                    }

                    ChannelEntity prevChannel = backend.getChannel(prevChannelIndex);
                    backend.changeChannel(prevChannel);

                    mMediaPlayer.setDataSource(backend.getActiveChannel().getUrl());
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mMediaPlayer.setOnPreparedListener(this);
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                mChannelTitle.setVisibility(View.GONE);

                return true;

            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:

                mChannelName.setText(backend.getActiveChannel().getName());
                mChannelNumber.setText(backend.getActiveChannelIndex() + "");
                Picasso.get().load(backend.getActiveChannel().getLogo()).into(mChannelIcon);

                mChannelTitle.setVisibility(View.VISIBLE);

                AsyncTask task = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {

                        try {
                            Thread.sleep(kINFO_BANNER_ACTIVE_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        PlaybackActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mChannelTitle.setVisibility(View.GONE);
                            }
                        });

                        return null;
                    }
                };

                task.execute();


                return true;

            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_BUTTON_B:
                mChannelTitle.setVisibility(View.GONE);

                return true;
            default:

                return false;
        }

    }
}
