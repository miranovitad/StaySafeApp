package com.example.staysafeapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentHome extends Fragment {

    SessionManager sessionManager;
    private TextView name, email, phone, txtDataMeninggal, txtDataPositif, txtDataSembuh, txtDataDirawat;
    private Button btnLogout;
    CarouselView carouselView;

    private List<CampaignItem> campaignItemList;

    ListView listCampaign;
    private static final String CAMPAIGN_URL = "http://192.168.43.140/staysafeapp/getCampaign.php";

    int[] sampleImages = {R.drawable.slidea, R.drawable.slideb, R.drawable.slidec};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listCampaign = view.findViewById(R.id.listCampaign);
        loadCampaign();

        campaignItemList = new ArrayList<>();

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName    = user.get(sessionManager .NAME);
        String mEmail   = user.get(SessionManager .EMAIL);
        String mId      = user.get(SessionManager .ID);
        String mPhone   = user.get(SessionManager .PHONE);

        name    = view.findViewById(R.id.txtNameHome);
        email   = view.findViewById(R.id.txtEmailHome);
        phone   = view.findViewById(R.id.txtPhoneHome);

        name.setText(mName);
        email.setText(mEmail);
        phone.setText(mPhone);

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });

        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        txtDataMeninggal    = view.findViewById(R.id.txtDataMeninggal);
        txtDataPositif      = view.findViewById(R.id.txtDataPositif);
        txtDataSembuh       = view.findViewById(R.id.txtDataSembuh);
        txtDataDirawat      = view.findViewById(R.id.txtDataDirawat);

        getdata();

        return view;
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    void getdata(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://api.kawalcorona.com/indonesia/";

        JSONObject jsonBody = new JSONObject();

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i < jsonArray.length(); i++){
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject jsonPost = jsonArray.getJSONObject(i);
                        txtDataMeninggal.setText(jsonPost.getString("meninggal"));
                        txtDataPositif.setText(jsonPost.getString("positif"));
                        txtDataDirawat.setText(jsonPost.getString("dirawat"));
                        txtDataSembuh.setText(jsonPost.getString("sembuh"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response",error.toString());
            }
        });
        queue.add(stringRequest);
    }

    private void loadCampaign() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CAMPAIGN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray campaignArray = jsonObject.getJSONArray("campaign");

                            for (int i = 0; i < campaignArray.length(); i++) {

                                JSONObject jsonPost = campaignArray.getJSONObject(i);
                                CampaignItem campaignItem = new CampaignItem(
                                        jsonPost.getString("idCampaign"),
                                        jsonPost.getString("idUser"),
                                        jsonPost.getString("namaUser"),
                                        jsonPost.getString("phone"),
                                        jsonPost.getString("email"),
                                        jsonPost.getString("name"),
                                        jsonPost.getString("startDate"),
                                        jsonPost.getString("endDate"),
                                        jsonPost.getString("info"),
                                        jsonPost.getString("detail"),
                                        jsonPost.getString("target"),
                                        jsonPost.getString("bankAcc"),
                                        jsonPost.getString("bankNum"));

                                campaignItemList.add(campaignItem);

                                AdapterCampaign adapter = new AdapterCampaign(campaignItemList, getActivity());

                                listCampaign.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
