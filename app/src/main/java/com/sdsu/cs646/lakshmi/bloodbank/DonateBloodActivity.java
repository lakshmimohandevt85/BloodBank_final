package com.sdsu.cs646.lakshmi.bloodbank;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sdsu.cs646.lakshmi.bloodbank.domain.DonorData;
import com.sdsu.cs646.lakshmi.bloodbank.util.StringUtils;
// AppCompatActivity
public class DonateBloodActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,View.OnClickListener
{


        // Firebase URL
    final static String fireBaseURL = "https://androidbloodbank.firebaseio.com/";
    Firebase firebase;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private  Double latitude;
    private  Double longitude;

    Spinner spinner_bloodSelect;
    Spinner spinner_state;
    Spinner spinner_medicalHistory;
    Spinner spinner_currentLocation;

    ArrayAdapter<CharSequence> adapter_state;
    ArrayAdapter<CharSequence> adapter_blood_group;
    ArrayAdapter<CharSequence> adapter_med_condition;
    ArrayAdapter<CharSequence> adapter_currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Firebase.setAndroidContext(this);
        firebase = new Firebase(fireBaseURL);

        spinner_bloodSelect = (Spinner) findViewById(R.id.spinner_bloodGroups);
        spinner_state = (Spinner) findViewById(R.id.spinner_state);
        spinner_medicalHistory = (Spinner) findViewById(R.id.spinner_medicalHistory);
        spinner_currentLocation = (Spinner) findViewById(R.id.spinner_location);

        adapter_blood_group = ArrayAdapter.createFromResource(this, R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter_state = ArrayAdapter.createFromResource(this, R.array.state_names, android.R.layout.simple_spinner_item);
        adapter_med_condition = ArrayAdapter.createFromResource(this, R.array.medical_history, android.R.layout.simple_spinner_item);
        adapter_currentLocation = ArrayAdapter.createFromResource(this, R.array.current_location, android.R.layout.simple_spinner_item);

        adapter_blood_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_med_condition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_currentLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_bloodSelect.setAdapter(adapter_blood_group);
        spinner_state.setAdapter(adapter_state);
        spinner_medicalHistory.setAdapter(adapter_med_condition);
        spinner_currentLocation.setAdapter(adapter_currentLocation);

        buildGoogleApiClient();

    }
    /**
     * This method will post the Data to the FireBase server
     * while clicking the "Post" button.
     */
    public void postDataToServer(View view)
    {
        DonorData donorData;
        firebase = new Firebase(fireBaseURL);
        String contactNumber = ((EditText) findViewById(R.id.editText_contactNo)).getText().toString();
        String name = ((EditText) findViewById(R.id.editText_name)).getText().toString();
        String address = ((EditText) findViewById(R.id.editText_postalAddress)).getText().toString();
        String medicalConditions = ((Spinner) findViewById(R.id.spinner_medicalHistory)).getSelectedItem().toString();
        String state = ((Spinner) findViewById(R.id.spinner_state)).getSelectedItem().toString();
        String bloodGroup = ((Spinner) findViewById(R.id.spinner_bloodGroups)).getSelectedItem().toString();
        String allowLocation =((Spinner) findViewById(R.id.spinner_location)).getSelectedItem().toString();
        String location_latitude = "";
        String location_longitude = "";

        if (name == null || name.trim().equals("")){
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterDonor)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (address == null || address.trim().equals("")){
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterAddress)), Toast.LENGTH_SHORT).show();
            return;
        }
        if (contactNumber == null || contactNumber.trim().equals("")){
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterContact)), Toast.LENGTH_SHORT).show();
            return;
        }else {

            if (StringUtils.valiatePhoneNumber(contactNumber) == null){
                Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterContact)), Toast.LENGTH_SHORT).show();
                return;
            }
            contactNumber = StringUtils.valiatePhoneNumber(contactNumber);
        }
        if (bloodGroup.equals("Select")){
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterBlood)), Toast.LENGTH_SHORT).show();
            return;
        }

        if (state.equals("Select")){
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterState)), Toast.LENGTH_SHORT).show();
            return;
        }

        if (medicalConditions.equals("Select")){
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterMedical)), Toast.LENGTH_SHORT).show();
            return;
        }

        if (getResources().getString(R.string.yes).equalsIgnoreCase(allowLocation)){
            location_latitude = latitude.toString();
            location_longitude = longitude.toString();
        }

        String email_id =  (String) firebase.getAuth().getProviderData().get("email");
        donorData = new DonorData(name, contactNumber, address, medicalConditions,
                location_latitude, location_longitude, state,
                bloodGroup, email_id);


        try
        {
            bloodGroup = StringUtils.convertSymbolToString(bloodGroup);
            firebase.child(state).child(bloodGroup).push().setValue(donorData);
             Toast.makeText(getApplicationContext(), (getResources().getString(R.string.donorDetails)), Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex)
        {
            // Exception scenario..
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.exception)), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            finish();
        }
    }

    protected synchronized void buildGoogleApiClient()
    {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    protected void onStart()
    {
        googleApiClient.connect();
        super.onStart();
    }
    protected void onPause()
    {
        googleApiClient.disconnect();
        super.onPause();
    }

    public void back(View button)
    {
        finish();
    }



    @Override
    public void onConnected(Bundle bundle)
    {
        Log.d(getResources().getString(R.string.connected), getResources().getString(R.string.connect));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)
        {

            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (lastLocation != null)
        {
            Log.d(getResources().getString(R.string.latitude), String.valueOf(lastLocation.getLatitude()));
            Log.d(getResources().getString(R.string.longitude), String.valueOf(lastLocation.getLongitude()));
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
        }
    }
    @Override
    public void onConnectionSuspended(int i)
    {
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
    }

    @Override
    public void onClick(View v)
    {
        //Toast.makeText(getApplicationContext(), "Report Submitted !",
                //Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goBack(View view){
        finish();
    }

    /**
     * method to hide the keyboard when clicked outside editTextField
     * @param event
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}
