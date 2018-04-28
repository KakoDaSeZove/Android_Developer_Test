package com.rtrk.android.test.sdk;

import com.rtrk.android.test.sdk.models.ChannelEntity;

import java.util.ArrayList;

/**
 * Backend emulator
 *
 * @author Veljko Ilkic
 */
public class BackendEmulator {

    /**
     * Singleton instance
     */
    private final static BackendEmulator instance = new BackendEmulator();

    /**
     * Singleton constructor
     */
    private BackendEmulator() {
        setup();
    }

    /**
     * Get singleton instance
     *
     * @return singleton instance
     */
    public static BackendEmulator getInstance() {
        return instance;
    }

    /**
     * Channels
     */
    private ArrayList<ChannelEntity> channels = new ArrayList<>();

    /**
     * Active channel
     */
    private ChannelEntity activeChannel;

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

        //Initialize first channel by default
        if (channels.size() > 0) {
            activeChannel = channels.get(0);
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
        activeChannel = channels.get(index);
    }

    /**
     * Change channel
     *
     * @param channel channel
     */
    public void changeChannel(ChannelEntity channel) {
        this.activeChannel = channel;
    }

    /**
     * Get active channel index
     *
     * @return active channel index
     */
    public int getActiveChannelIndex() {
        for (ChannelEntity channelEntity : channels) {
            if (channelEntity.getId() == activeChannel.getId()) {
                return activeChannel.getId();
            }
        }

        return -1;
    }

    /**
     * Get active channel
     *
     * @return active channel
     */
    public ChannelEntity getActiveChannel() {
        return activeChannel;
    }
}
