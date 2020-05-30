package com.example.staysafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class DetailCampaignActivity extends AppCompatActivity {

    private TextView name, userName, email, phone, waktu, detail, target, bank, title;
    private ImageView img;
    private Button info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_campaign);

        name        = findViewById(R.id.viewName);
        userName    = findViewById(R.id.viewUserName);
        email       = findViewById(R.id.viewEmail);
        phone       = findViewById(R.id.viewPhone);
        waktu       = findViewById(R.id.viewWaktu);
        detail      = findViewById(R.id.viewDetail);
        target      = findViewById(R.id.viewTarget);
        bank        = findViewById(R.id.viewBank);
        info        = findViewById(R.id.viewInfo);
        img         = findViewById(R.id.viewImg);
        title       = findViewById(R.id.viewTitle);

        String dataId       = getIntent().getExtras().getString("idCampaign", "Null");
        String dataName     = getIntent().getExtras().getString("name", "Null");
        String datauserName = getIntent().getExtras().getString("namaUser", "Null");
        String dataTarget   = getIntent().getExtras().getString("target", "Null");
        String dataPhone    = getIntent().getExtras().getString("phone", "Null");
        String dataEmail    = getIntent().getExtras().getString("email", "Null");
        String dataStartDate= getIntent().getExtras().getString("startDate", "Null");
        String dataEndDate  = getIntent().getExtras().getString("endDate", "Null");
        final String dataInfo     = getIntent().getExtras().getString("info", "Null");
        String dataDetail   = getIntent().getExtras().getString("detail", "Null");
        String dataBankAcc  = getIntent().getExtras().getString("bankAcc", "Null");
        String dataBankNum  = getIntent().getExtras().getString("bankNum", "Null");

        String path = "http://192.168.43.140/staysafeapp/campaign_foto/"+dataId+".jpeg";

        name.setText(dataName);
        Picasso.get().load(path).into(img);
        userName.setText(datauserName);
        email.setText(dataEmail);
        phone.setText(dataPhone);
        waktu.setText(dataStartDate+" sampai dengan "+dataEndDate);
        detail.setText(dataDetail);
        target.setText("Target : Rp."+dataTarget);
        bank.setText(dataBankNum+" ("+dataBankAcc+")");
        title.setText(dataName);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse(dataInfo));
                startActivity(openURL);
            }
        });

    }
}
