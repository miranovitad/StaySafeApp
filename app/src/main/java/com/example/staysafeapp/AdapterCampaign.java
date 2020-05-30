package com.example.staysafeapp;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


public class AdapterCampaign extends ArrayAdapter<CampaignItem> {

    private List<CampaignItem> CampaignItemList;

    private Context context;

    public AdapterCampaign(List<CampaignItem> campaignItemList, Context context) {
        super(context, R.layout.list_campaign, campaignItemList);
        this.CampaignItemList = campaignItemList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View listViewItem = inflater.inflate(R.layout.list_campaign, null, true);

        TextView namaUser      = listViewItem.findViewById(R.id.txtNamaUser);
        TextView phone         = listViewItem.findViewById(R.id.txtPhone);
        TextView email         = listViewItem.findViewById(R.id.txtEmail);
        TextView name          = listViewItem.findViewById(R.id.txtName);
        TextView startDate     = listViewItem.findViewById(R.id.txtStartDate);
        TextView endDate       = listViewItem.findViewById(R.id.txtEndDate);
        TextView info          = listViewItem.findViewById(R.id.txtInfo);
        TextView detail        = listViewItem.findViewById(R.id.txtDetail);
        TextView target        = listViewItem.findViewById(R.id.txtTarget);
        TextView bankAcc       = listViewItem.findViewById(R.id.txtBankAcc);
        TextView bankNum       = listViewItem.findViewById(R.id.txtBankNum);
        ImageView imgCampaign  = listViewItem.findViewById(R.id.imgViewCampaign);

        CampaignItem campaignItem = CampaignItemList.get(position);

        namaUser.setText("By "+campaignItem.getNamaUser());
        phone.setText(campaignItem.getPhone());
        email.setText(campaignItem.getEmail());
        name.setText(campaignItem.getName());
        startDate.setText(campaignItem.getStartDate());
        endDate.setText("Batas "+campaignItem.getEndDate());
        info.setText(campaignItem.getInfo());
        detail.setText(campaignItem.getDetail());
        target.setText("Target "+campaignItem.getTarget());
        bankAcc.setText(campaignItem.getBankAcc());
        bankNum.setText(campaignItem.getBankNum());

        String path = "http://192.168.43.140/staysafeapp/campaign_foto/"+campaignItem.getIdCampaign()+".jpeg";
        Picasso.get().load(path).into(imgCampaign);

        return listViewItem;
    }
}