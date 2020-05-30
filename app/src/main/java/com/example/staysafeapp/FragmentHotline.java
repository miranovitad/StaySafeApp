package com.example.staysafeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.List;


public class FragmentHotline extends Fragment {

    private List<HotlineItem> hotlineItemList;

    ListView listHotline;
    private static final String HOTLINE_URL = "http://192.168.43.140/staysafeapp/getHotline.php";
    private TextView hosName, address, phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hotline, container, false);

        listHotline = view.findViewById(R.id.listHotline);

        listHotline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callPhoneNumber(position);
        }
        });

        hotlineItemList = new ArrayList<>();

        loadHotline();

        return view;
    }

    private void callPhoneNumber(int position) {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                HotlineItem hotlineItem = hotlineItemList.get(position);
                Intent intent = new Intent(Intent.ACTION_CALL);
                String temp = "tel:" +hotlineItem.getPhone();
                intent.setData(Uri.parse(temp));
                startActivity(intent);

            }
            else {
                HotlineItem hotlineItem = hotlineItemList.get(position);
                Intent intent = new Intent(Intent.ACTION_CALL);
                String temp = "tel:" +hotlineItem.getPhone();
                intent.setData(Uri.parse(temp));
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void loadHotline() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HOTLINE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray hotlineArray = jsonObject.getJSONArray("hotline");

                            for (int i = 0; i < hotlineArray.length(); i++) {

                                JSONObject jsonPost = hotlineArray.getJSONObject(i);
                                HotlineItem hotlineItem = new HotlineItem(
                                        jsonPost.getString("idHotline"),
                                        jsonPost.getString("hosName"),
                                        jsonPost.getString("address"),
                                        jsonPost.getString("phone"));

                                hotlineItemList.add(hotlineItem);

                                AdapterHotline adapter = new AdapterHotline(hotlineItemList, getActivity());

                                listHotline.setAdapter(adapter);
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
