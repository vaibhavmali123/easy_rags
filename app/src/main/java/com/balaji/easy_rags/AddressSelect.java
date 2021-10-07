package com.balaji.easy_rags;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AddressSelect extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private EditText a_addressEdit;
    private EditText a_pincode;
    private TextView a_city;
    private TextView a_or;
    private TextView a_printAddress1;
    private TextView a_printAddress2;
    private TextView a_selectOne;
    private TextView a_selectTwo;
    private TextView a_removeOne;
    private TextView a_removeTwo;
    private Button a_add;
    private CardView a_storedAddress1;
    private CardView a_storedAddress2;
    private ImageView backButton;
    private Spinner a_society;
    private Spinner a_area;
    private CheckBox curretLocationCheckBox;

    private Session session;
    private String Mobile;
    private String City;
    private String addressOne;
    private String addressTwo;
    private String addressLine;
    private String pincode;
    private String fullAddress;
    private String addressId;
    private String area_name;
    private String soc_name;

    private String flag;

    private ArrayList<String> socList;
    private ArrayList<String> areaList;

    ProgressDialog progressDialog;

    //------------------------------------------------------------------

    Location mLocation;
    Location currentLocation;
    GoogleApiClient mGoogleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 2000;  /* 2 secs */
    private long FASTEST_INTERVAL = 1000; /* 1 secs */

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private EditText addressEditThree;
    private final static int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(AddressSelect.this);
        session = new Session(getApplicationContext());
        Mobile = session.prefs.getString("Mbl", null);
        addressEditThree=findViewById(R.id.addressThree);
        //--------------------------------------------------------------
        permissions.add(ACCESS_FINE_LOCATION);
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
            new AlertDialog.Builder(AddressSelect.this)
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
        }
        //--------------------------------------------------------------

        a_addressEdit = (EditText) findViewById(R.id.a_addressEdit);
        a_pincode = (EditText) findViewById(R.id.a_pincode);
        a_city = (TextView) findViewById(R.id.a_city);
        a_or = (TextView) findViewById(R.id.a_or);
        a_printAddress1 = (TextView) findViewById(R.id.a_printAddress1);
        a_printAddress2 = (TextView) findViewById(R.id.a_printAddress2);
        a_selectOne = (TextView) findViewById(R.id.a_selectOne);
        a_selectTwo = (TextView) findViewById(R.id.a_selectTwo);
        a_removeOne = (TextView) findViewById(R.id.a_removeOne);
        a_removeTwo = (TextView) findViewById(R.id.a_removeTwo);
        a_add = (Button) findViewById(R.id.a_add);
        a_storedAddress1 = (CardView) findViewById(R.id.a_storedAddress1);
        a_storedAddress2 = (CardView) findViewById(R.id.a_storedAddress2);
        a_society = (Spinner) findViewById(R.id.a_society);
        a_area = (Spinner) findViewById(R.id.a_area);
        backButton = (ImageView) findViewById(R.id.backButton);
        curretLocationCheckBox = (CheckBox) findViewById(R.id.curretLocationCheckBox);

        curretLocationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(curretLocationCheckBox.isChecked()){
                    if (mGoogleApiClient != null) {
                        mGoogleApiClient.connect();
                    }
                }else{
                    stopLocationUpdates();
                    a_addressEdit.setText("");
                }
            }
        });

        socList = new ArrayList<>();
        areaList = new ArrayList<>();

        getSoc();
        getArea();

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        flag = b.getString("flag");

        a_storedAddress1.setVisibility(View.GONE);
        a_storedAddress2.setVisibility(View.GONE);
        findAddress();

        a_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area_name = a_area.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        a_society.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                soc_name = a_society.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        a_selectOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag.equals("account")){
                    Intent i = new Intent(getApplicationContext(), Account.class);
                    i.putExtra("address", a_printAddress1.getText().toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else if(flag.equals("cart")){
                    Intent i = new Intent(getApplicationContext(), CartActivity.class);
                    i.putExtra("address", a_printAddress1.getText().toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }

            }
        });
        a_selectTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag.equals("account")){
                    Intent i = new Intent(getApplicationContext(), Account.class);
                    i.putExtra("address", a_printAddress2.getText().toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else if(flag.equals("cart")){
                    Intent i = new Intent(getApplicationContext(), CartActivity.class);
                    i.putExtra("address", a_printAddress2.getText().toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    Log.d("TTTTTTT",a_printAddress2.getText().toString());
                }
            }
        });
        a_removeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressId = "1";
                removeAddress();
            }
        });
        a_removeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressId = "2";
                removeAddress();
            }
        });
        a_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressLine = a_addressEdit.getText().toString();
                pincode = a_pincode.getText().toString();


                if(addressLine.equals("")&& addressEditThree.equals("")){
                    Toast.makeText(AddressSelect.this, "Please add address", Toast.LENGTH_SHORT).show();
                }else{
                    fullAddress = addressLine;
                    if (addressEditThree.getText().toString().trim().equals("") ||
                            a_addressEdit.getText().toString().trim().equals("")){
                        Toast.makeText(AddressSelect.this, "Please enter address", Toast.LENGTH_SHORT).show();

                    }
                    else{

                        addAddress();
                    }
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void removeAddress() {

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"remove_address.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    String code;
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            code = JO.getString("result");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Address Removed", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
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
            params.put("mobile", Mobile);
            params.put("address_id", addressId);
            return params;
        }
        };
        queue.add(stringRequest);

    }

    private void addAddress() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"add_address.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    String code;
                    String result;
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            code = JO.getString("data_code");
                            result = JO.getString("result");
                            if(code.equals("200")){
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }else{
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

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
            params.put("mobile", Mobile);
            params.put("address3",addressEditThree.getText().toString());
            params.put("address", fullAddress);

            Log.d("ADDRESSSSSSS",params.toString());
            return params;
        }
        };
        queue.add(stringRequest);

    }

    private void findAddress() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"check_address.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    String code;
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            code = JO.getString("data_code");
                            if(code.equals("500")){
                                JSONArray JA = JO.getJSONArray("result");
                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    addressOne = JO1.getString("address1");
                                    addressTwo = JO1.getString("address2");
                                    String addressThree = JO1.getString("address3");
                                    session.saveCurrentAddress(addressOne);
                                    session.saveManualAddress(addressThree);
                                    addressTwo=addressThree;
                                    addressEditThree.setText(addressThree);
                                }
                                if(addressOne.equals("")){
                                    a_storedAddress1.setVisibility(View.GONE);
                                }else{
                                    a_storedAddress1.setVisibility(View.VISIBLE);
                                    a_printAddress1.setText(addressOne);
                                }
                                if(addressTwo.equals("")){
                                    a_storedAddress2.setVisibility(View.GONE);
                                }else{
                                    a_storedAddress2.setVisibility(View.GONE);
                                    a_printAddress2.setText(addressTwo);
                                }
                            }else{

                            }
                            progressDialog.dismiss();

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
            params.put("mobile", Mobile);
            return params;
        }
        };
        queue.add(stringRequest);
    }

    private void getSoc() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"soc_list.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        socList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        socList.add("Select Society");
                        socList.add("Skip");
                        try {
                            JSONObject JO = new JSONObject(s);
                            JSONArray JA = JO.getJSONArray("menu_banner");
                            for(int i=0; i<JA.length(); i++){
                                JSONObject JO1 = JA.getJSONObject(i);
                                socList.add(JO1.get("soc_name").toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        setSoc();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(stringRequest);

    }

    private void setSoc(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, socList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a_society.setAdapter(arrayAdapter);
    }

    private void getArea() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"area_list.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        areaList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        areaList.add("Select Area");
                        areaList.add("Skip");
                        try {
                            JSONObject JO = new JSONObject(s);
                            JSONArray JA = JO.getJSONArray("menu_banner");
                            for(int i=0; i<JA.length(); i++){
                                JSONObject JO1 = JA.getJSONObject(i);
                                areaList.add(JO1.get("area_name").toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setArea();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error.Response",Toast.LENGTH_SHORT).show();

                    }
                });
        queue.add(stringRequest);

    }

    private void setArea(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        a_area.setAdapter(arrayAdapter);
    }


    private ArrayList findUnAskedPermissions(ArrayList wanted) {
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
            a_addressEdit.setText("Please install Google Play services.");
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
                        a_addressEdit.setText(getCompleteAddressString(location.getLatitude(), location.getLongitude()));
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
        new AlertDialog.Builder(AddressSelect.this)
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
    }

}
