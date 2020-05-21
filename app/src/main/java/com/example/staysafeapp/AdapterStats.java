package com.example.staysafeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AdapterStats extends ArrayAdapter<StatsItem> {

    private List<StatsItem> StatsItemList;

    private Context context;

    public AdapterStats(List<StatsItem> statsItemList, Context context) {
        super(context, R.layout.list_stats, statsItemList);
        this.StatsItemList = statsItemList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.list_stats, null, true);

        TextView textProvinsi       = listViewItem.findViewById(R.id.txtProvinsi);
        TextView textKeterangan      = listViewItem.findViewById(R.id.txtKeterangan);
        Button   textKasus_Posi     = listViewItem.findViewById(R.id.btnKasusPosi);
        Button   textKasus_Semb     = listViewItem.findViewById(R.id.btnKasusSemb);
        Button   textKasus_Meni     = listViewItem.findViewById(R.id.btnKasusMeni);

        StatsItem statsItem = StatsItemList.get(position);

        textProvinsi.setText(statsItem.getProvinsi());
        textKeterangan.setText("Berikut ini adalah data covid-19 di Provinsi "+statsItem.getProvinsi());
        textKasus_Posi.setText("Positif: "+ statsItem.getKasus_posi());
        textKasus_Semb.setText("Sembuh: "+statsItem.getKasus_semb());
        textKasus_Meni.setText("Meninggal: "+statsItem.getKasus_meni());

        return listViewItem;
    }
}