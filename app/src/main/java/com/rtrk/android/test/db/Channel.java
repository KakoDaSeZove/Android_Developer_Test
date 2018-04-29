package com.rtrk.android.test.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tijana on 29.4.18..
 */

@DatabaseTable(tableName = Channel.TABLE_NAME_CHANNEL)
public class Channel {

    public static final String TABLE_NAME_CHANNEL = "channel";

    public static final String FIELD_NAME_ID = "_id";
    public static final String FIELD_NAME_LAST_WATCHED_CHANNEL = "last_watched_channel";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_LAST_WATCHED_CHANNEL)
    private int mLastWatchedChannel;

    public Channel() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmLastWatchedChannel() {
        return mLastWatchedChannel;
    }

    public void setmLastWatchedChannel(int mLastWatchedChannel) {
        this.mLastWatchedChannel = mLastWatchedChannel;
    }
}
