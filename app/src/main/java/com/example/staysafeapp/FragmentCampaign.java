package com.example.staysafeapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentCampaign extends Fragment {

    private Button btn_pindah;
    private List<CampaignItem> campaignItemList;

    ListView listCampaign;
    private static final String CAMPAIGN_URL = "http://192.168.43.140/staysafeapp/getCampaign.php";
    private TextView name, email, phone, txtDataMeninggal, txtDataPositif, txtDataSembuh, txtDataDirawat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaign, container, false);

        listCampaign = view.findViewById(R.id.listCampaign);

        listCampaign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CampaignItem campaignItem = campaignItemList.get(position);
                Intent intent = new Intent(getActivity(), DetailCampaignActivity.class);
                intent.putExtra("idCampaign", campaignItem.getIdCampaign());
                intent.putExtra("name", campaignItem.getName());
                intent.putExtra("target", campaignItem.getTarget());
                intent.putExtra("namaUser", campaignItem.getNamaUser());
                intent.putExtra("phone", campaignItem.getPhone());
                intent.putExtra("email", campaignItem.getEmail());
                intent.putExtra("startDate", campaignItem.getStartDate());
                intent.putExtra("endDate", campaignItem.getEndDate());
                intent.putExtra("info", campaignItem.getInfo());
                intent.putExtra("detail", campaignItem.getDetail());
                intent.putExtra("bankAcc", campaignItem.getBankAcc());
                intent.putExtra("bankNum", campaignItem.getBankNum());
                startActivity(intent);
            }
        });

        campaignItemList = new ArrayList<>();

        btn_pindah = view.findViewById(R.id.btnpindah);

        btn_pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateCampaignActivity.class);
                startActivity(intent);
            }
        });

        loadCampaign();

        return view;
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
