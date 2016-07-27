package com.sdsu.cs646.lakshmi.bloodbank.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sdsu.cs646.lakshmi.bloodbank.R;
import com.sdsu.cs646.lakshmi.bloodbank.domain.DonorData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ListViewActivity extends ActionBarActivity
{

    String fireBaseURL = "https://androidbloodbank.firebaseio.com/";
    Firebase firebase;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ProgressDialog progressDialog;
    private List<DonorData> listItemsList = new ArrayList<DonorData>();
    List<DonorData> donorDataList  = Collections.emptyList();
    String state = "";
    String blood_group = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        /** Get the value from Intent **/
        state = ""+intent.getStringExtra(getResources().getString(R.string.state));
        blood_group = ""+intent.getStringExtra(getResources().getString(R.string.bloodGroup));

        /** for Firebase**/
        Firebase.setAndroidContext(this);
        firebase = new Firebase(fireBaseURL);

        /** for recycler view **/
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.BLACK)
                        .build());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateList();
    }

    public void updateList()
    {
        showPD();
        getDataFromServer();

    }

    /**
     * This method will pull the JSON data from server
     * based on the selected state and blood group.
     */
    private void getDataFromServer()
    {

        donorDataList  = new ArrayList<DonorData>();
        final String bloodGroup = blood_group;

        fireBaseURL = fireBaseURL + state + "/" + bloodGroup;
        firebase = new Firebase(fireBaseURL);

        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                hidePD();
                donorDataList = getData(dataSnapshot, bloodGroup);
                if (donorDataList == null || donorDataList.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.noDatas), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RecyclerView.Adapter adapter = new RecyclerAdapter(getApplicationContext(), donorDataList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                hidePD();
            }
        });

    }

    private List<DonorData> getData(DataSnapshot dataSnapshot,String blood_group)
    {

        List<DonorData> donorDataListValue  = new ArrayList<DonorData>();
        DonorData donorData = new DonorData();
        List<HashMap<String, String>> donorDataListofMap = new ArrayList<HashMap<String, String>>();
        for(DataSnapshot data: dataSnapshot.getChildren())
        {

                try
                {
                    String key = data.getKey();
                    HashMap<String, String> datafromFirebase = (HashMap<String, String>) data.getValue();
                    datafromFirebase.put(getResources().getString(R.string.key), key);
                    donorDataListofMap.add(datafromFirebase);

                }
                catch(Exception ex)
                {

                }

        }

        HashMap<String, String> donorDataMap = new HashMap<String, String>();

        for (int i=0; i< donorDataListofMap.size();i++ )
        {
            donorDataMap = donorDataListofMap.get(i);
            donorData = new DonorData();
            donorData.setAddress(donorDataMap.get(getResources().getString(R.string.address)));
            donorData.setName(donorDataMap.get(getResources().getString(R.string.name)));
            donorData.setMedical_conditions(donorDataMap.get(getResources().getString(R.string.medical_conditions)));
            donorData.setContact_no(donorDataMap.get(getResources().getString(R.string.contact_no)));
            donorData.setState(donorDataMap.get("state"));
            donorData.setBlood_group(donorDataMap.get("blood_group"));
            donorData.setEmail_id(donorDataMap.get("email_id"));
            donorData.setId(donorDataMap.get(getResources().getString(R.string.key)));
            donorData.setLatitude(donorDataMap.get("latitude"));
            donorData.setLongitude(donorDataMap.get("longitude"));
            donorDataListValue.add(donorData);
        }

     return donorDataListValue;
    }

    public void back(View button)
    {
        finish();
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

    private void showPD()
    {
        if(progressDialog == null)
        {
            progressDialog  = new ProgressDialog(this);
            progressDialog .setMessage(getResources().getString(R.string.loading));
            progressDialog .setCancelable(false);
            progressDialog .setCanceledOnTouchOutside(false);
            progressDialog .show();
        }
    }
    // function to hide the loading dialog box
    private void hidePD()
    {
        if (progressDialog  != null)
        {
            progressDialog .dismiss();
            progressDialog  = null;
        }
    }


}
