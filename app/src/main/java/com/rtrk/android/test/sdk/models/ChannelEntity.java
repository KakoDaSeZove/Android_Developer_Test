package com.rtrk.android.test.sdk.models;

/**
 * Channel entity
 *
 * @author Veljko Ilkic
 */
public class ChannelEntity {

    /**
     * Channel id
     * <p>
     * Can be used as channel index
     */
    private int id;

    /**
     * Channel name
     */
    private String name;

    /**
     * Channel image logo
     */
    private String logo;

    /**
     * Channel url
     * <p>
     * Video url that should be passed to player for decoding
     */
    private String url;

    /**
     * Is favorite
     */
    private boolean isFavorite;

    /**
     * Constructor
     *
     * @param id   channel id
     * @param name channel name
     * @param logo channel logo
     * @param url  video url
     */
    public ChannelEntity(int id, String name, String logo, String url) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.url = url;
    }

    /**
     * Get channel id
     *
     * @return channel id
     */
    public int getId() {
        return id;
    }

    /**
     * Get name
     *
     * @return channel name
     */
    public String getName() {
        return name;
    }

    /**
     * Get channel log
     *
     * @return logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * Get url
     *
     * @return video url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set favorite
     *
     * @param isFavorite is favorite
     */
    public void setFavorte(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    /**
     * Is favorite
     *
     * @return is favorite
     */
    public boolean isFavorite() {
        return isFavorite;
    }

}
