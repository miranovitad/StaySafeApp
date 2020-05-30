package com.example.staysafeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AdapterHotline extends ArrayAdapter<HotlineItem> {

    private List<HotlineItem> HotlineItemList;

    private Context context;

    public AdapterHotline(List<HotlineItem> hotlineItemList, Context context) {
        super(context, R.layout.list_hotline, hotlineItemList);
        this.HotlineItemList = hotlineItemList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.list_hotline, null, true);


        TextView hosName    = listViewItem.findViewById(R.id.txtHosName);
        TextView address    = listViewItem.findViewById(R.id.txtAddress);
        TextView phone      = listViewItem.findViewById(R.id.txtPhone);

        HotlineItem hotlineItem = HotlineItemList.get(position);

        hosName.setText(hotlineItem.getHosName());
        address.setText(hotlineItem.getAddress());
        phone.setText(hotlineItem.getPhone());

        return listViewItem;
    }
}