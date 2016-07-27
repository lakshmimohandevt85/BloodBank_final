package com.sdsu.cs646.lakshmi.bloodbank.chart;

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

import com.sdsu.cs646.lakshmi.bloodbank.R;

public class SelectChartActivity extends ActionBarActivity
{

    Spinner spinner_state;
    ArrayAdapter<CharSequence> adapter_state;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner_state = (Spinner) findViewById(R.id.spinner_state_select);
        adapter_state = ArrayAdapter.createFromResource(this, R.array.state_names, android.R.layout.simple_spinner_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_state.setAdapter(adapter_state);
        Button button_post = (Button) findViewById(R.id.button_selectChart);

        button_post.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String state = ((Spinner) findViewById(R.id.spinner_state_select)).getSelectedItem().toString();
                if (state.equalsIgnoreCase(getResources().getString(R.string.select_1)))
                {
                    Toast.makeText(getApplicationContext(), (getResources().getString(R.string.selectState)), Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    Intent viewChart = new Intent(SelectChartActivity.this,ViewChartActivity.class);
                    viewChart.putExtra(getResources().getString(R.string.toPassState), state);
                    //  Call ViewChartActivity
                    startActivity(viewChart);
                }

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

}
