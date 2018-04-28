package com.rtrk.android.test.ui;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rtrk.android.test.R;
import com.rtrk.android.test.sdk.BackendEmulator;
import com.rtrk.android.test.sdk.models.ChannelEntity;

import java.io.IOException;

public class PlaybackActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    public static final String LOG_TAG = "PlaybackActivity";

    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder = null;

    /**
     * Backend instance
     */
    private BackendEmulator backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get backend reference
        backend = BackendEmulator.getInstance();
        setContentView(R.layout.activity_playback);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

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

                return true;
            default:

                return false;
        }

    }
}
