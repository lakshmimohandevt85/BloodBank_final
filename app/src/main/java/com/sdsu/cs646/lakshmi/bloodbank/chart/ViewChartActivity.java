package com.sdsu.cs646.lakshmi.bloodbank.chart;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sdsu.cs646.lakshmi.bloodbank.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewChartActivity extends ActionBarActivity
{
    Firebase firebase;
    private PieChart pieChart;
    private RelativeLayout mainLayout;
    private  float[] yData;
    private  String[] xData;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        /** Get the value from Intent **/
        String state = ""+intent.getStringExtra(getResources().getString(R.string.toPassState));

        Firebase.setAndroidContext(this);
        getDataFromServer(state);

    }


    private  void addData()
    {
        ArrayList<Entry> yValue = new ArrayList<Entry>();
        for (int i = 0; i < yData.length; i++)
            yValue.add(new Entry(yData[i],i));

        ArrayList<String> xValue = new ArrayList<String>();
        for (int i = 0; i < xData.length; i++)
            xValue.add(xData[i]);

        // Create pie data set
        PieDataSet dataSet = new PieDataSet(yValue,getResources().getString(R.string.blood_group));
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xValue, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        //data.setValueTypeface(tf);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private void setDetails(Map<String, Map<String, Map<String, String>>> donorDataMapOfState, String state)
    {

        float[] yData_temp = new float[8];
        String[] xData_temp = new String[8];

        Map<String, Map<String, String>> donorDataMap = donorDataMapOfState.get(state);

        int count = 0;

        if (donorDataMap == null ){
           //ß Toast.makeText(getApplicationContext(), getResources().getString(R.string.noData), Toast.LENGTH_SHORT).show();
            return;
        }

        if (donorDataMap.get(getResources().getString(R.string.aPositive))!= null &&
                donorDataMap.get(getResources().getString(R.string.aPositive)).size() > 0)
        {

            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.aPositive)).size();
            xData_temp[count] = getResources().getString(R.string.aPlus);
            count = count + 1;
        }

        if (donorDataMap.get(getResources().getString(R.string.aNegative))!= null &&
                donorDataMap.get(getResources().getString(R.string.aNegative)).size() > 0)
        {

            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.aNegative)).size();
            xData_temp[count] = getResources().getString(R.string.aMinus);
            count = count + 1;
        }

        if (donorDataMap.get(getResources().getString(R.string.bPositive))!= null &&
        donorDataMap.get(getResources().getString(R.string.bPositive)).size() > 0)
        {
            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.bPositive)).size();
            xData_temp[count] = getResources().getString(R.string.bPlus);
            count = count + 1;
        }
        if (donorDataMap.get(getResources().getString(R.string.bNegative))!= null &&
                donorDataMap.get(getResources().getString(R.string.bNegative)).size() > 0)
        {
            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.bNegative)).size();
            xData_temp[count] = getResources().getString(R.string.bNegative);
            count = count + 1;
        }

        if (donorDataMap.get(getResources().getString(R.string.abPositive))!= null &&
                donorDataMap.get(getResources().getString(R.string.abPositive)).size() > 0)

        {
            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.abPositive)).size();
            xData_temp[count] = getResources().getString(R.string.abPlus);
            count = count + 1;
        }
        if (donorDataMap.get(getResources().getString(R.string.abNegative))!= null &&
                donorDataMap.get(getResources().getString(R.string.abNegative)).size() > 0)

        {
            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.abNegative)).size();
            xData_temp[count] = getResources().getString(R.string.abMinus);
            count = count + 1;
        }
        if (donorDataMap.get(getResources().getString(R.string.oPositive))!= null &&
                donorDataMap.get(getResources().getString(R.string.oPositive)).size() > 0)

        {
            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.oPositive)).size();
            xData_temp[count] = getResources().getString(R.string.oPlus);
            count = count + 1;
        }
        if (donorDataMap.get(getResources().getString(R.string.oNegative))!= null &&
                donorDataMap.get(getResources().getString(R.string.oNegative)).size() > 0)

        {
            yData_temp[count] = donorDataMap.get(getResources().getString(R.string.oNegative)).size();
            xData_temp[count] = getResources().getString(R.string.oMinus);
            count = count + 1;
        }


          float[]  yData_arr = Arrays.copyOf(yData_temp,count);
          String[] xData_arr = Arrays.copyOf(xData_temp,count);

        yData = yData_arr;
        xData = xData_arr;
        populateChart();

    }

    /**
     * This method will pull the JSON data from server
     * based on the selected state and blood group.
     */
    private void getDataFromServer(String state)
    {

        final String state_selected = state;

        String fireBaseURL = "https://androidbloodbank.firebaseio.com/";
        firebase = new Firebase(fireBaseURL);

        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, Map<String, Map<String, String>>> donorDataMap  = new HashMap<String, Map<String, Map<String, String>>>();
                donorDataMap = getData(dataSnapshot, state_selected);

                setDetails(donorDataMap, state_selected);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });

    }


    private  Map<String, Map<String, Map<String, String>>> getData(DataSnapshot dataSnapshot,String state_selected)
    {

        Map<String, Map<String, Map<String, String>>> donorDataMapOfState  = new HashMap<String, Map<String, Map<String, String>>>();

        for(DataSnapshot data: dataSnapshot.getChildren())
        {
            if (state_selected.equalsIgnoreCase(data.getKey()))
            {

                donorDataMapOfState.put(data.getKey(), (Map<String, Map<String, String>>) data.getValue());

            }

        }
        return donorDataMapOfState;
    }

    private void populateChart(){

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        pieChart = new PieChart(this);

        pieChart = (PieChart) findViewById(R.id.chart1);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        // add piechart to mainlayout
        //  mainLayout.addView(pieChart);
        mainLayout.setBackgroundColor(Color.LTGRAY);

        //configure pie chart
        pieChart.setUsePercentValues(true);

        pieChart.setDescription(getResources().getString(R.string.bloodDistribution));

        // enable hole  of pie chart and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setTransparentCircleColor(Color.WHITE);
        // pieChart.setHoleColor

        // enable rotation of pie chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setRotationAngle(0);

        //set a chart value selected by listener
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight)
            {
                // display message when value is selected

                if (entry ==null)
                    return;
                Toast.makeText(ViewChartActivity.this,
                        xData[entry.getXIndex()] + " = " + entry.getVal() + "%",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected()
            {

            }
        });

        // add Data
        addData();

        // customize legends
        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

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
