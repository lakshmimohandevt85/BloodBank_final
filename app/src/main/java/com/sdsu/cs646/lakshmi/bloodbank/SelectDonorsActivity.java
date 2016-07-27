package com.sdsu.cs646.lakshmi.bloodbank;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sdsu.cs646.lakshmi.bloodbank.util.StringUtils;
import com.sdsu.cs646.lakshmi.bloodbank.view.ListViewActivity;

public class SelectDonorsActivity extends ActionBarActivity
{

    Spinner spinner_state;
    Spinner spinner_bloodSelect;

    ArrayAdapter<CharSequence> adapter_state;
    ArrayAdapter<CharSequence> adapter_blood_group;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_donors);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner_state = (Spinner) findViewById(R.id.spinner_state_select);
        spinner_bloodSelect = (Spinner) findViewById(R.id.spinner_blood_select);

        adapter_blood_group = ArrayAdapter.createFromResource(this, R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter_state = ArrayAdapter.createFromResource(this, R.array.state_names, android.R.layout.simple_spinner_item);

        adapter_blood_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_bloodSelect.setAdapter(adapter_blood_group);
        spinner_state.setAdapter(adapter_state);


        Button button_post = (Button) findViewById(R.id.button_submit);
        button_post.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String state = ((Spinner) findViewById(R.id.spinner_state_select)).getSelectedItem().toString();
                String bloodGroup = ((Spinner) findViewById(R.id.spinner_blood_select)).getSelectedItem().toString();

                if (bloodGroup.equalsIgnoreCase(getResources().getString(R.string.select_1)))
                {
                    Toast.makeText(getApplicationContext(), (getResources().getString(R.string.selectBlood)), Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (state.equalsIgnoreCase(getResources().getString(R.string.select_1)))
                {
                    Toast.makeText(getApplicationContext(), (getResources().getString(R.string.selectState)), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(SelectDonorsActivity.this,ListViewActivity.class);

                bloodGroup = StringUtils.convertSymbolToString(bloodGroup);
                intent.putExtra(getResources().getString(R.string.state), state);
                intent.putExtra(getResources().getString(R.string.bloodGroup), bloodGroup);

                //  Call DonateBloodActivity
                startActivity(intent);
            }
        });

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

    public void goBack(View view){
        finish();
    }

}
