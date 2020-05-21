package com.example.staysafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtname, txtemail, txtpassword, txtphone;
    private Button btnRegister, btnLogin;
    private static String URL_REGIST = "http://192.168.43.140/staysafeapp/register.php";
    private ProgressBar progressRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtname        = findViewById(R.id.txtName);
        txtemail       = findViewById(R.id.txtEmail);
        txtphone       = findViewById(R.id.txtTlpNum);
        txtpassword    = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressRegister = findViewById(R.id.progresRegister);
        btnLogin    = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        progressRegister.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mname    = txtname.getText().toString().trim();
                String mphone   = txtphone.getText().toString().trim();
                String mEmail   = txtemail.getText().toString().trim();
                String mPass    = txtpassword.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty() || !mname.isEmpty() || !mphone.isEmpty()){
                    Regist();
                } else {
                    txtemail.setError("Please insert email");
                    txtpassword.setError("Please insert password");
                    txtphone.setError("Please insert phone number");
                    txtname.setError("Please insert name");
                }
            }
        });
    }

    private void Regist(){

        btnRegister.setVisibility(View.GONE);
        progressRegister.setVisibility(View.VISIBLE);

        final String name       = this.txtname.getText().toString().trim();
        final String phone      = this.txtphone.getText().toString().trim();
        final String email      = this.txtemail.getText().toString().trim();
        final String password   = this.txtpassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                btnRegister.setVisibility(View.VISIBLE);
                                progressRegister.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Register Sukses!", Toast.LENGTH_LONG).show();

                                txtname.setText("");
                                txtpassword.setText("");
                                txtemail.setText("");
                                txtphone.setText("");
                            }
                            else{
                                btnRegister.setVisibility(View.VISIBLE);
                                progressRegister.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "Register Error!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            btnRegister.setVisibility(View.VISIBLE);
                            progressRegister.setVisibility(View.GONE);
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error!" + e.toString(), Toast.LENGTH_LONG).show();
                            btnRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error!" + error.toString(), Toast.LENGTH_LONG).show();
                        btnRegister.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
