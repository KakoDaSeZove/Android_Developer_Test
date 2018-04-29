package com.rtrk.android.test.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by tijana on 29.4.18..
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static String DATABASE_NAME = "channel.db";
    public static int DATABASE_VERSION = 2;

    private Dao<Channel, Integer> mChannelDao = null;

    public DatabaseHelper(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Channel.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Channel.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Channel, Integer> getmChannelDao() throws SQLException {
        if (mChannelDao == null) {
            mChannelDao = getDao(Channel.class);
        }
        return mChannelDao;
    }

    @Override
    public void close() {
        mChannelDao = null;

        super.close();
    }
}
