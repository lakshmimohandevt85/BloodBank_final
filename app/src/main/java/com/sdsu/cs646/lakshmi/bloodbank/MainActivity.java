package com.sdsu.cs646.lakshmi.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.sdsu.cs646.lakshmi.bloodbank.chart.SelectChartActivity;
import com.sdsu.cs646.lakshmi.bloodbank.constants.Constant;
import com.sdsu.cs646.lakshmi.bloodbank.map.ViewLocation;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /**
     * Invoke the DonateBloodActivity
     * @param ImageButton
     */
    public void goDonateBloodActivity(View ImageButton)
    {
        Intent donateBlood = new Intent(this,DonateBloodActivity.class);
        //  Call DonateBloodActivity
        startActivity(donateBlood);
        Toast.makeText(getApplicationContext(), (getResources().getString(R.string.enterInformation)), Toast.LENGTH_SHORT).show();
    }
    /**
     * Invoke the ViewDonorsActivity
     * @param ImageButton
     */
    public void goSelectDonorsActivity(View ImageButton)
    {
        Intent intent = new Intent(this,SelectDonorsActivity.class);
        //  Call DonateBloodActivity
        startActivity(intent);
        Toast.makeText(getApplicationContext(), (getResources().getString(R.string.viewDonors)), Toast.LENGTH_SHORT).show();
    }
    /**
     * Invoke the ViewDonorsActivity
     * @param ImageButton
     */
    public void goSelectChartActivity(View ImageButton)
    {
        Intent selectChart = new Intent(this,SelectChartActivity.class);
        //  Call DonateBloodActivity
        startActivity(selectChart);
        Toast.makeText(getApplicationContext(), (getResources().getString(R.string.chartView)), Toast.LENGTH_SHORT).show();
    }

    public void goToMapActivity(View ImageButton)
    {
        Intent selectMap = new Intent(this, ViewLocation.class);
        //  Call MapViewActivity
        startActivity(selectMap);
        Toast.makeText(getApplicationContext(), (getResources().getString(R.string.mapView)), Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param view
     */
    public void logout(View view){

        Firebase firebase = new Firebase(Constant.FIREBASE_URL);
        firebase.unauth();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
