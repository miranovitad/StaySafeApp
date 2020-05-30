package com.example.staysafeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.IDNA;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateCampaignActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private TextView CampaignName, StartDate, EndDate, CampaignInfo, CampaignDetail, Target, BankAcc, BankNum;
    private Button UploadCampaignImage, btnSubmit;
    private Bitmap bitmap;
    private ImageView imgCampaign;
    private static String URL_UPLOAD = "http://192.168.43.140/staysafeapp/upload.php";
    String getId, name, startDate, endDate, info, detail, target, bankAcc, bankNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager .ID);

        CampaignName    = findViewById(R.id.txtCampaignName);
        StartDate       = findViewById(R.id.txtStartDate);
        EndDate         = findViewById(R.id.txtEndDate);
        CampaignInfo    = findViewById(R.id.txtCampaignInfo);
        CampaignDetail  = findViewById(R.id.txtCampaignDetail);
        Target          = findViewById(R.id.txtTarget);
        BankAcc         = findViewById(R.id.txtBankAcc);
        BankNum         = findViewById(R.id.txtBankNum);
        imgCampaign     = findViewById(R.id.imgCampaign);
        btnSubmit       = findViewById(R.id.btnSubmit);

        //get text



        UploadCampaignImage = findViewById(R.id.btnUploadCampaignImage);

        UploadCampaignImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name        = CampaignName.getText().toString().trim();
                startDate   = StartDate.getText().toString().trim();
                endDate     = EndDate.getText().toString().trim();
                info        = CampaignInfo.getText().toString().trim();
                detail      = CampaignDetail.getText().toString().trim();
                target      = Target.getText().toString().trim();
                bankAcc     = BankAcc.getText().toString().trim();
                bankNum     = BankNum.getText().toString().trim();

                UploadPicture(getId, getStringImage(bitmap), name, startDate, endDate, info, detail, target, bankAcc, bankNum);
            }
        });
    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgCampaign.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UploadPicture(final String id, final String photo, final String name, final String startDate, final String endDate, final String
                               info, final String detail, final String target, final String bankAcc, final String bankNum) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(CreateCampaignActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateCampaignActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(CreateCampaignActivity.this, "Try Again!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CreateCampaignActivity.this, "Try Again!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);
                params.put("name", name);
                params.put("startDate", startDate);
                params.put("endDate", endDate);
                params.put("info", info);
                params.put("detail", detail);
                params.put("target", target);
                params.put("bankAcc", bankAcc );
                params.put("bankNum", bankNum);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        System.out.println("encodedImage "+ encodedImage);
        return encodedImage;
    }
}
