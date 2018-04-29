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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rtrk.android.test.R;
import com.rtrk.android.test.sdk.BackendEmulator;
import com.rtrk.android.test.sdk.models.ChannelEntity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class PlaybackActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, AdapterView.OnItemClickListener {

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

    // Info banner UI
    private LinearLayout mChannelTitle;
    private TextView mChannelName;
    private TextView mChannelNumber;
    private ImageView mChannelIcon;

    // Channel List UI
    private LinearLayout mChannelListLayout;
    private ListView mChannelList;

    private Timer mTimer = null;


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

        mChannelListLayout = findViewById(R.id.channel_list);
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

                ChannelEntity nextChannel = backend.getChannel((backend.getActiveChannelIndex() + 1) % backend.getChannels().size());

                changeChannelMethod(nextChannel.getId());

                mChannelTitle.setVisibility(View.GONE);

                return true;
            case KeyEvent.KEYCODE_CHANNEL_DOWN:
            case KeyEvent.KEYCODE_DPAD_DOWN:


                int prevChannelIndex = backend.getActiveChannelIndex() - 1;
                if (prevChannelIndex < 0) {
                    prevChannelIndex = backend.getChannelsCount() + prevChannelIndex;
                }

                changeChannelMethod(prevChannelIndex);


                mChannelTitle.setVisibility(View.GONE);

                return true;

            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_BUTTON_A:

                mChannelName.setText(backend.getActiveChannel().getName());
                mChannelNumber.setText(backend.getActiveChannelIndex() + "");
                Picasso.get().load(backend.getActiveChannel().getLogo()).into(mChannelIcon);

                mChannelTitle.setVisibility(View.VISIBLE);

                if(mTimer != null) {
                    mTimer.cancel();
                    mTimer.purge();
                }

                mTimer = new Timer();

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        PlaybackActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mChannelTitle.setVisibility(View.GONE);
                            }
                        });
                    }
                };

                mTimer.schedule(timerTask, kINFO_BANNER_ACTIVE_TIME);

                return true;

            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_BUTTON_B:
                mChannelTitle.setVisibility(View.GONE);
                mChannelListLayout.setVisibility(View.GONE);

                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:

                mChannelListLayout.setVisibility(View.VISIBLE);
                mChannelTitle.setVisibility(View.GONE);

                ChannelEntity[] channels = new ChannelEntity[backend.getChannels().size()];
                for (int i = 0; i < channels.length; i++) {
                    channels[i] = backend.getChannels().get(i);
                }

                ChannelItem adapter = new ChannelItem(this, R.layout.channel_item, channels);

                mChannelList = (ListView)findViewById(R.id.list_of_all_channels);
                mChannelList.setAdapter(adapter);

                mChannelList.setOnItemClickListener(this);

                mChannelList.setSelection(backend.getActiveChannelIndex());
                mChannelList.requestFocus();

                return true;
            default:

                return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        changeChannelMethod(position);

        mChannelListLayout.setVisibility(View.GONE);
    }

    private void changeChannelMethod (int position){

        releaseMediaPlayer();

        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDisplay(mSurfaceHolder);

            backend.changeChannel(position);

            mMediaPlayer.setDataSource(backend.getActiveChannel().getUrl());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
