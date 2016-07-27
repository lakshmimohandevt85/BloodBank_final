package com.sdsu.cs646.lakshmi.bloodbank.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.sdsu.cs646.lakshmi.bloodbank.MainActivity;
import com.sdsu.cs646.lakshmi.bloodbank.R;
import com.sdsu.cs646.lakshmi.bloodbank.map.ViewLocation;
import com.sdsu.cs646.lakshmi.bloodbank.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ViewDonorsActivity extends ActionBarActivity
{
    TextView textview_name;
    TextView textview_address;
    TextView textview_contact_no;
    TextView textview_medical_condition;
    TextView textview_state;
    TextView textview_blood_group;
    TextView textview_location;

    String email_id = "";
    String state_code = "";
    String blood_group = "";
    String id_value = "";
    String latitude_value = "";
    String longitude_value = "";

    String fireBaseURL = "https://androidbloodbank.firebaseio.com/";
    Firebase firebase;
    Geocoder geocoder;;
    List<Address> addresses;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donors);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /** for Firebase**/
        Firebase.setAndroidContext(this);

        geocoder = new Geocoder(this, Locale.getDefault());


        textview_name = (TextView) findViewById(R.id.textView_nameListed);
        textview_address = (TextView) findViewById(R.id.textView_addressListed);
        textview_contact_no = (TextView) findViewById(R.id.textView_contactListed);
        textview_medical_condition = (TextView) findViewById(R.id.textView_medicalListed);
        textview_state = (TextView) findViewById(R.id.textView_stateListed);
        textview_blood_group = (TextView) findViewById(R.id.textView_bloodListed);
        textview_location = (TextView) findViewById(R.id.textView_locationView);

        Intent intent = getIntent();

        /** Get the value from Intent **/
        String name = ""+intent.getStringExtra(getResources().getString(R.string.name));
        String latitude = ""+intent.getStringExtra(getResources().getString(R.string.latitude));
        String longitude = ""+intent.getStringExtra(getResources().getString(R.string.longitude));
        String contact_no = ""+intent.getStringExtra(getResources().getString(R.string.contact_no));

        String address = ""+intent.getStringExtra(getResources().getString(R.string.address));
        String medical_condition = ""+intent.getStringExtra(getResources().getString(R.string.medical_conditions));
        String blood = ""+intent.getStringExtra("blood");
        String state = ""+intent.getStringExtra("state");
        String email = ""+intent.getStringExtra("email");
        String id = ""+intent.getStringExtra("id");
        String city = getResources().getString(R.string.na);

        if (latitude.length() > 0 &&  longitude.length() > 0){
            try {
                addresses = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                city = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        email_id  = email;
        state_code = state;
        blood_group  = blood;
        id_value = id;
        latitude_value = latitude;
        longitude_value = longitude;

        textview_name.setText(name);
        textview_address.setText(address);
        textview_contact_no.setText(contact_no);
        textview_medical_condition.setText(medical_condition);
        textview_state.setText(state_code);
        textview_blood_group.setText(blood_group);
        textview_location.setText(city);
    }

    /** Delete donor data **/
    public void deleteUser(View view) {

        firebase = new Firebase(fireBaseURL);
        String login_email_id = (String) firebase.getAuth().getProviderData().get("email");

        if (email_id.equalsIgnoreCase(login_email_id)){

            String blood_group_str = StringUtils.convertSymbolToString(blood_group);
            fireBaseURL = fireBaseURL + state_code + "/" + blood_group_str + "/" + String.valueOf(id_value);
            firebase = new Firebase(fireBaseURL);
            firebase.removeValue();

            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.deleted)), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,MainActivity.class);
            //  go to MainBloodActivity
            startActivity(intent);

        }
        else
        {
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.noPermission)), Toast.LENGTH_SHORT).show();
        }
    }

    /** go to  MapViewActivity **/
    public void goToMapActivity(View ImageButton)
    {
        if (latitude_value.length() > 0 && longitude_value.length() >0){
            Intent selectMap = new Intent(this, ViewLocation.class);
            selectMap.putExtra("latitude", latitude_value);
            selectMap.putExtra("longitude", longitude_value);
            // selectMap
            startActivity(selectMap);
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.mapView)), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), (getResources().getString(R.string.locationNotAvailable)), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
