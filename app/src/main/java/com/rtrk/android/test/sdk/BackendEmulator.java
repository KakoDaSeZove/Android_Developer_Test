package com.rtrk.android.test.sdk;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.rtrk.android.test.db.Channel;
import com.rtrk.android.test.db.DatabaseHelper;
import com.rtrk.android.test.sdk.models.ChannelEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Backend emulator
 *
 * @author Veljko Ilkic
 */
public class BackendEmulator {

    /**
     * Singleton instance
     */
    private static BackendEmulator instance;

    /**
     * Singleton constructor
     */
    private BackendEmulator(Context context) {
        mContext = context;

        setup();
    }

    private Context mContext;

    /**
     * Get singleton instance
     *
     * @return singleton instance
     */
    public static BackendEmulator getInstance(Context context) {
        if(instance == null){
            instance = new BackendEmulator(context);
        }
        return instance;
    }

    /**
     * Channels
     */
    private ArrayList<ChannelEntity> channels = new ArrayList<>();

    /**
     * Channel logos
     */
    private String[] logos = {
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/RTv2.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/BLOOMBERG.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/artev2.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/CNN.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/daserste.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/BBCNEWS.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/TV5Mondev3.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/TV5Mondev3.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/travelchannel.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/travelchannel.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/kika.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/kika.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/TVEHD.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/insight.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/CBEEBIES.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/bbconev2.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/BBCTWO.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/BBCTHREE.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/BBCFOUR.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/insight.png",
            "http://ottdemocdn.abertistelecom.com/content/logos/ChannelLogos/CBBC.png"
    };

    /**
     * Channel video urls
     */
    private String[] videoUrl = {
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo3)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo1)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo6)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo8)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo20)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo21)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo23)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo23)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo6)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo6)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo24)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo24)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo22)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo24)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo8)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo3)/index.m3u8",
            "http://abrademos.abertistelecom.com/Content/HLS/Live/Channel(abrademo1)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo8)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo25)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo6)/index.m3u8",
            "http://abrademos-cdn.cellnextelecom.net/Content/HLS/Live/Channel(abrademo2)/index.m3u8",
    };


    /**
     * Setup
     */
    private void setup() {

        //Load channels
        for (int i = 0; i < videoUrl.length; i++) {

            int id = i;
            String name = "Channel " + i;
            String logo = logos[i];
            String url = videoUrl[i];
            channels.add(new ChannelEntity(id, name, logo, url));

        }
        DatabaseHelper helper = new DatabaseHelper(mContext);
        Dao<Channel, Integer> channelDao = null;
        try {
            channelDao = helper.getmChannelDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //checking if there is something in db

        List<Channel> channelsDB = null;
        try {
            channelsDB = channelDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (channelsDB == null || channelsDB.size() == 0) {
            Channel lastWatchedChannel = new Channel();
            lastWatchedChannel.setmLastWatchedChannel(0);
            try {
                channelDao.create(lastWatchedChannel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get channel
     *
     * @param index index
     * @return channel item
     */
    public ChannelEntity getChannel(int index) {
        return channels.get(index);
    }

    /**
     * Get channels
     *
     * @return all channels
     */
    public ArrayList<ChannelEntity> getChannels() {
        return channels;
    }

    /**
     * Get channels count
     *
     * @return channels count
     */
    public int getChannelsCount() {
        return channels.size();
    }

    /**
     * Change channel
     *
     * @param index channel index
     */
    public void changeChannel(int index) {
        DatabaseHelper helper = new DatabaseHelper(mContext);
        Dao<Channel, Integer> channelDao = null;
        try {
            channelDao = helper.getmChannelDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Channel> channelDb = channelDao.queryForAll();
            Channel lastWatchedChannel = channelDb.get(0);
            lastWatchedChannel.setmLastWatchedChannel(index);

            channelDao.update(lastWatchedChannel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change channel
     *
     * @param channel channel
     */
    public void changeChannel(ChannelEntity channel) {
        DatabaseHelper helper = new DatabaseHelper(mContext);
        Dao<Channel, Integer> channelDao = null;
        try {
            channelDao = helper.getmChannelDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<Channel> channelDb = channelDao.queryForAll();
            Channel lastWatchedChannel = channelDb.get(0);
            lastWatchedChannel.setmLastWatchedChannel(channel.getId());

            channelDao.update(lastWatchedChannel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get active channel index
     *
     * @return active channel index
     */
    public int getActiveChannelIndex() {
        DatabaseHelper helper = new DatabaseHelper(mContext);
        Dao<Channel, Integer> channelDao = null;
        try {
            channelDao = helper.getmChannelDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        List<Channel> channels = null;
        try {
            channels = channelDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return channels.get(0).getmLastWatchedChannel();
    }

    /**
     * Get active channel
     *
     * @return active channel
     */
    public ChannelEntity getActiveChannel() {
        DatabaseHelper helper = new DatabaseHelper(mContext);
        Dao<Channel, Integer> channelDao = null;
        try {
            channelDao = helper.getmChannelDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        List<Channel> channelsDB = null;
        try {
            channelsDB = channelDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int lastWatchdChannel = channelsDB.get(0).getmLastWatchedChannel();

        return channels.get(lastWatchdChannel);
    }
}
