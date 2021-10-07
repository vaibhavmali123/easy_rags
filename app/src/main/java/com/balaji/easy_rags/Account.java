package com.balaji.easy_rags;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Account extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText a_name;

    private TextView a_email;
    private TextView a_mobile;
    private TextView a_male;
    private TextView a_female;
    private TextView a_address;
    private TextView a_change_password;

    private Button a_bdate;
    private CardView a_update;

    private Session session;
    private String gender;

    private EditText resetTwoOtp;
    private EditText resetTwoPwd;
    private EditText resetTwoPwdC;
    private Button resetTwoOkay;
    private Button closebtn;

    public String change_Otp;
    public String change_Pwd;
    public String change_PwdC;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().hide();

        session = new Session(getApplicationContext());

        a_address = (TextView) findViewById(R.id.a_address);
        a_email = (TextView) findViewById(R.id.a_email);
        a_mobile = (TextView) findViewById(R.id.a_mobile);
        a_male = (TextView) findViewById(R.id.a_male);
        a_female = (TextView) findViewById(R.id.a_female);
        a_change_password = (TextView) findViewById(R.id.a_change_password);

        a_name = (EditText) findViewById(R.id.a_name);

        a_bdate = (Button) findViewById(R.id.a_bdate);

        a_update = (CardView) findViewById(R.id.a_update);
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        a_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddressSelect.class);
                i.putExtra("flag", "account");
                startActivity(i);
            }
        });


        a_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Account.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_box_change_password,null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                resetTwoOtp = (EditText) mView.findViewById(R.id.resetTwoOtp);
                resetTwoPwd = (EditText) mView.findViewById(R.id.resetTwoPwd);
                resetTwoPwdC = (EditText) mView.findViewById(R.id.resetTwoPwdC);
                resetTwoOkay = (Button) mView.findViewById(R.id.resetTwoOkay);
                closebtn = (Button) mView.findViewById(R.id.closebtn);

                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                resetTwoOkay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        change_Otp = resetTwoOtp.getText().toString();
                        change_Pwd = resetTwoPwd.getText().toString();
                        change_PwdC = resetTwoPwdC.getText().toString();

                        if(change_Otp.equals("")){
                            Toast.makeText(getApplicationContext(), "Please Enter Otp", Toast.LENGTH_SHORT).show();
                        }else{
                            if(change_Pwd.equals(change_PwdC)){
                                //dialog.dismiss();
                                resetPassword();
                            }else{
                                Toast.makeText(getApplicationContext(), "Passwords does not matched", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        a_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Select Date");
            }
        });

        a_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a_male.setBackgroundResource(R.color.colorPrimary);
                gender = "Male";
                a_female.setBackgroundResource(R.drawable.buttonborder);
            }
        });
        a_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a_female.setBackgroundResource(R.color.colorPrimary);
                gender = "Female";
                a_male.setBackgroundResource(R.drawable.buttonborder);
            }
        });

        a_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfile();
            }
        });


        getProfile();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        @SuppressLint("SimpleDateFormat") String selectedDate = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
        a_bdate.setText(selectedDate);
    }

    private void setProfile(){

        String uri = getResources().getString(R.string.base_url)+"profile_details.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);

                            String data_code = JO.getString("code");
                            if(data_code.equals("200")){
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_SHORT).show();
                            }else if(data_code.equals("500")){
                                Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", session.prefs.getString("Mbl",null));
            params.put("birthday", a_bdate.getText().toString());
            params.put("name", a_name.getText().toString());
            params.put("gender", gender);
            return params;
        }
        };
        queue.add(stringRequest);

    }

    private void getProfile(){

        String uri = getResources().getString(R.string.base_url)+"send_profile_details.php";
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
                                JSONArray JA = JO.getJSONArray("item_list");
                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    a_name.setText(JO1.getString("first_name"));
                                    a_email.setText(JO1.getString("email"));
                                    a_mobile.setText(session.prefs.getString("Mbl",null));
                                    if(JO1.getString("birthday").equals("")){}
                                    else{
                                        a_bdate.setText(JO1.getString("birthday"));

                                    }
                                    gender = JO1.getString("gender");
                                    if(gender.equals(null)){}
                                    else{
                                        if(gender.equals("Male")){
                                            a_male.setBackgroundResource(R.color.colorPrimary);
                                            gender = "Male";
                                            a_female.setBackgroundResource(R.drawable.buttonborder);

                                        }else if(gender.equals("Female")){
                                            a_female.setBackgroundResource(R.color.colorPrimary);
                                            gender = "Female";
                                            a_male.setBackgroundResource(R.drawable.buttonborder);
                                        }
                                    }

                                }
                            }else if(data_code.equals("500")){
                                Toast.makeText(getApplicationContext(), JO.getString("password updated"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //  Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", session.prefs.getString("Mbl",null));
            return params;
        }
        };
        queue.add(stringRequest);
    }

    private void resetPassword() {

        String uri = getResources().getString(R.string.base_url)+"change_password_verify_otp.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            String data_code = JO.getString("data_code");
                            if(data_code.equals("100")){
                                Toast.makeText(getApplicationContext(), JO.getString("password updated"), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Home.class);
                                startActivity(i);
                                finish();
                            }else if(data_code.equals("200")){
                                Toast.makeText(getApplicationContext(), JO.getString("password updated"), Toast.LENGTH_SHORT).show();
                            }else if(data_code.equals("500")){
                                Toast.makeText(getApplicationContext(), JO.getString("password updated"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", session.prefs.getString("Mbl",null));
            params.put("npwd", change_Pwd);
            params.put("password", change_Otp);
            return params;
        }
        };
        queue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
