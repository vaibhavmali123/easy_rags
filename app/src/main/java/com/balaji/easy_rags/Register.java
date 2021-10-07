package com.balaji.easy_rags;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText regi_name;
    private EditText regi_email;
    private EditText regi_mobile;
    private EditText regi_password;
    private EditText reg_otp;
    private Spinner employeeSpinner;
    private TextView textView;

    private CardView submit;
    private Button verifyOtp;
    private Button resendOtp;

    private LinearLayout layoutOtp;
    ProgressDialog progressDialog;
    private String s_otp;
    private Session session;
    private String employee_mobile_number="";


    ArrayList<String> employeeList;
    ArrayList<String> employeeMobileList;
    Location currentLocation;

    //------------------------------------------------------------------

    Location mLocation;
    GoogleApiClient mGoogleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2000;  /* 2 secs */
    private long FASTEST_INTERVAL = 1000; /* 1 secs */

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private EditText companyNameEditText;
    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(Register.this);
        session = new Session(getApplicationContext());
        companyNameEditText=findViewById(R.id.companyNameEt);
        ImageView backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //--------------------------------------------------------------
/*        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(Register.this)
                    .setTitle("GPS not found")
                    .setMessage("Want to enable?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }else{
//            loginText.setText("Please wait till we locate your device..");
//            loginText.setTextSize(14);
        }*/
        //--------------------------------------------------------------

        regi_name = (EditText) findViewById(R.id.regi_name);
        regi_email = (EditText) findViewById(R.id.regi_email);
        regi_mobile = (EditText) findViewById(R.id.regi_mobile);
        regi_password = (EditText) findViewById(R.id.regi_password);
        reg_otp = (EditText) findViewById(R.id.reg_otp);
        employeeSpinner = (Spinner) findViewById(R.id.employeeSpinner);
        textView = (TextView) findViewById(R.id.textView);
        submit = (CardView) findViewById(R.id.submit);
        verifyOtp = (Button) findViewById(R.id.verifyOtp);
        resendOtp = (Button) findViewById(R.id.resendOtp);
        layoutOtp = (LinearLayout) findViewById(R.id.layoutOtp);

        employeeList = new ArrayList<String>();
        employeeMobileList = new ArrayList<String>();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(employee_mobile_number.equals("")){
                    Toast.makeText(Register.this, "Select Employee", Toast.LENGTH_SHORT).show();
                }else{
                    if (companyNameEditText.getText().toString().equals("")){
                        Toast.makeText(Register.this, "Enter company name", Toast.LENGTH_SHORT).show();

                         }
                    else{

                           registerUser();
                    }
                }
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(employee_mobile_number.equals("")){
                    Toast.makeText(Register.this, "Select Employee", Toast.LENGTH_SHORT).show();
                }else{

                   // registerUser();
                }
            }
        });

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_otp = reg_otp.getText().toString();
                if(s_otp.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Otp", Toast.LENGTH_SHORT).show();
                }else{
                    verigyOtp();
                }
            }
        });

        getEmployeeList();

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee_mobile_number = employeeMobileList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        }
        );

    }

    private void getEmployeeList() {

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        employeeList.clear();
        employeeMobileList.clear();
        String uri = getResources().getString(R.string.base_url)+"employee_list.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            String code = JO.getString("data_code");
                            if(code.equals("200")){
                                progressDialog.dismiss();
                                JSONArray JA = JO.getJSONArray("employee_list");
                                employeeList.add("Select Employee");
                                employeeMobileList.add("");
                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    employeeList.add(JO1.getString("emp_name"));
                                    employeeMobileList.add(JO1.getString("phone"));
                                }
                                employeeList.add("Other");
                                employeeMobileList.add("NA");

                            }else{
                                //progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setAdapter();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Registration.Error!",Toast.LENGTH_SHORT).show();
                    }
                }){};
        queue.add(stringRequest);

    }

    private void setAdapter() {

        ArrayAdapter<String> maritalStatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, employeeList);
        maritalStatusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(maritalStatusAdapter);

    }

    private void verigyOtp() {

        progressDialog.setMessage("Verifying...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"regi_verify_otp.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            String result = JO.getString("data_code");
                            if(result.equals("200")){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();
                                openApp();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), JO.getString("res"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //  Toast.makeText(getApplicationContext(), "Registration.Error!",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("otp", s_otp);
                params.put("mobile", regi_mobile.getText().toString());
                params.put("status", "Y");

                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void openApp() {

        session.setLoggedin(true);
        session.setMobile(regi_mobile.getText().toString());
        session.setUserName(regi_name.getText().toString());
        session.setEmailId(regi_email.getText().toString());
        Intent i = new Intent(getApplicationContext(), Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

    private void registerUser() {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"registration.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            String result = JO.getString("data_code");
                            if(result.equals("200")){
                                submit.setVisibility(View.INVISIBLE);
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_LONG).show();
//                                layoutOtp.setVisibility(View.VISIBLE);
                                openApp();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // Toast.makeText(getApplicationContext(), "Registration.Error!",Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("first_name", regi_name.getText().toString());
                params.put("mobile", regi_mobile.getText().toString());
                params.put("company_name",companyNameEditText.getText().toString());
                params.put("email", regi_email.getText().toString());
                params.put("password", regi_password.getText().toString());
                params.put("employee_number", employee_mobile_number);
                /*params.put("location", textView.getText().toString());
                params.put("loc_lat", String.valueOf(currentLocation.getLatitude()));
                params.put("loc_long", String.valueOf(currentLocation.getLongitude()));*/
                params.put("location", "");
                params.put("loc_lat", "");
                params.put("loc_long", "");

                return params;
            }
        };
        queue.add(stringRequest);

    }

/*    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            textView.setText("Please install Google Play services.");
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if(mLocation!=null)
        {
//            textView.setText("Latitude : "+mLocation.getLatitude()+" , Longitude : "+mLocation.getLongitude());
        }

        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location!=null){}
//            textView.setText("Latitude : "+location.getLatitude()+" , Longitude : "+location.getLongitude());

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else
                finish();

            return false;
        }
        return true;
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if(location!=null)
                            currentLocation = location;
//                            textView.setText("Latitude : "+location.getLatitude()+" , Longitude : "+location.getLongitude());
                            textView.setText(getCompleteAddressString(location.getLatitude(), location.getLongitude()));
                    }
                });


    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(String.valueOf(permission)) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(String.valueOf(permissionsRejected.get(0)))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Register.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }


    public void stopLocationUpdates()
    {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, new com.google.android.gms.location.LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }
                    });
            mGoogleApiClient.disconnect();
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }*/

}
