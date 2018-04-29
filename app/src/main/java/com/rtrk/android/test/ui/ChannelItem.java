package com.rtrk.android.test.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rtrk.android.test.R;
import com.rtrk.android.test.db.Channel;
import com.rtrk.android.test.sdk.BackendEmulator;
import com.rtrk.android.test.sdk.models.ChannelEntity;
import com.squareup.picasso.Picasso;

/**
 * Created by tijana on 29.4.18..
 */

public class ChannelItem extends ArrayAdapter<ChannelEntity> {

    private Context context;
    private int resource;
    private ChannelEntity[] channels;


    public ChannelItem(@NonNull Context context, int resource, ChannelEntity[] channels) {
        super(context, resource, channels);

        this.context = context;
        this.resource = resource;
        this.channels = channels;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ChannelHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ChannelHolder();
            holder.channelNumber = (TextView) row.findViewById(R.id.channel_item_number);
            holder.channelName = (TextView) row.findViewById(R.id.channel_item_name);
            holder.channelLogo = (ImageView) row.findViewById(R.id.channel_item_icon);

            row.setTag(holder);
        } else {
            holder = (ChannelHolder) row.getTag();
        }

        ChannelEntity channel = channels[position];
        holder.channelNumber.setText(channel.getId() + "");
        holder.channelName.setText(channel.getName());

        Picasso.get().load(channel.getLogo()).into(holder.channelLogo);

        return row;
    }

    static class ChannelHolder {

        TextView channelNumber;
        TextView channelName;
        ImageView channelLogo;
    }
}
