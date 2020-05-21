package com.example.staysafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText txtemail, txtpassword;
    private TextView tvRegister;
    private Button btnLogin;
    private static String URL_LOGIN = "http://192.168.43.140/staysafeapp/login.php";
    private ProgressBar progressLogin;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        txtemail    = findViewById(R.id.txtLoginEmail);
        txtpassword = findViewById(R.id.txtLoginPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        progressLogin = findViewById(R.id.progresLoading);
        tvRegister  = findViewById(R.id.linkRegister);

        progressLogin.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail   = txtemail.getText().toString().trim();
                String mPass    = txtpassword.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()){
                    Login(mEmail, mPass);
                } else {
                    txtemail.setError("Please insert email");
                    txtpassword.setError("Please insert password");
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Login(final String email, final String password){

        progressLogin.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name  = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id    = object.getString("id").trim();
                                    String phone = object.getString("phone").trim();

                                    sessionManager.createSession(name, email, id, phone);

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    intent.putExtra("id", id);
                                    intent.putExtra("phone", phone);

                                    startActivity(intent);

                                    progressLogin.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                }
                            }
                            else{

                                progressLogin.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);

                                Toast.makeText(LoginActivity.this, "Login failed, check email or password again", Toast.LENGTH_LONG).show();

                                txtemail.setText("");
                                txtpassword.setText("");
                            }
                        } catch (JSONException e){

                            progressLogin.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);

                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
