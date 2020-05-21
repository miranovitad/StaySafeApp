package com.example.staysafeapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FragmentStats extends Fragment {

    private List<StatsItem> statsItemList;

    ListView listStats;
    private static final String STATS_URL = "https://api.kawalcorona.com/indonesia/provinsi/";
    private TextView name, email, phone, txtDataMeninggal, txtDataPositif, txtDataSembuh, txtDataDirawat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        listStats           = view.findViewById(R.id.listStats);

        statsItemList = new ArrayList<>();

        loadStatistik();

        return view;
    }
    private void loadStatistik() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i < jsonArray.length(); i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                for (int j = 0; j < jsonObject.length(); j++){
                                    JSONObject jsonPost = jsonObject.getJSONObject(String.valueOf("attributes"));

                                    StatsItem statsItem = new StatsItem(
                                            jsonPost.getString("Provinsi"),
                                            jsonPost.getString("Kasus_Posi"),
                                            jsonPost.getString("Kasus_Semb"),
                                            jsonPost.getString("Kasus_Meni"));

                                    statsItemList.add(statsItem);
                                }

                                AdapterStats adapter = new AdapterStats(statsItemList, getActivity());

                                listStats.setAdapter(adapter);

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
