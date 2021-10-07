package com.balaji.easy_rags;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class ForgotPassword extends AppCompatActivity {

    private Button closebtn;
    private Button forgot_send;
    private Button forgot_verifyOtp;
    private Button forgot_resendOtp;
    private EditText forgot_mobileNumber, forgot_otp;
    private LinearLayout llOtp;
    private LinearLayout llButton;

    private String mobileNumber;
    private String otp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("Forgot Password");

        progressDialog = new ProgressDialog(ForgotPassword.this);


        forgot_mobileNumber = (EditText) findViewById(R.id.forgot_mobileNumber);
        forgot_otp = (EditText) findViewById(R.id.forgot_otp);
        closebtn = (Button) findViewById(R.id.closebtn);
        forgot_send = (Button) findViewById(R.id.forgot_send);
        forgot_verifyOtp = (Button) findViewById(R.id.forgot_verifyOtp);
        forgot_resendOtp = (Button) findViewById(R.id.forgot_resendOtp);
        llButton = (LinearLayout) findViewById(R.id.llButton);
        llOtp = (LinearLayout) findViewById(R.id.llOtp);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        forgot_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = forgot_mobileNumber.getText().toString();
                if(mobileNumber.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }else{
                    sendOtp();
                }
            }
        });

        forgot_verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = forgot_otp.getText().toString();
                verifyOtp();
            }
        });

        forgot_resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
            }
        });


    }

    private void verifyOtp() {
        progressDialog.setMessage("Verifying...");
        progressDialog.show();
        String uri = String.format(getResources().getString(R.string.base_url)+"forgot_verify_otp.php");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject JO = new JSONObject(s);
                            String data_code = JO.getString("data_code");
                            if(data_code.equals("200")){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Password Sent", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }else if(data_code.equals("500")){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Re-Enter Otp", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", mobileNumber);
            params.put("otp", otp);
            return params;
        }
        };
        queue.add(stringRequest);

    }

    private void sendOtp() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String uri = String.format("http://admin.bigmartpune.com/bigmart_api/forgot_send_otp.php");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject JO = new JSONObject(s);
                            String data_code = JO.getString("data_code");
                            if(data_code.equals("200")){
                                progressDialog.dismiss();
                                llOtp.setVisibility(View.VISIBLE);
                                llButton.setVisibility(View.VISIBLE);
                                forgot_send.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Otp Sent", Toast.LENGTH_SHORT).show();

                            }else if(data_code.equals("500")){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Re-Enter Otp", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", mobileNumber);
            return params;
        }
        };
        queue.add(stringRequest);

    }

}
