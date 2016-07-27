package com.sdsu.cs646.lakshmi.bloodbank.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sdsu.cs646.lakshmi.bloodbank.R;


public class ListRowViewHolder extends RecyclerView.ViewHolder
{

    protected TextView id;
    protected RelativeLayout relativeLayout;
    protected TextView name;
    protected TextView contact_no;
    protected TextView address;
    protected TextView medical_conditions;
    protected TextView longitude;
    protected TextView latitude;
    protected TextView blood_group;
    protected TextView state;
    protected TextView email;



    public ListRowViewHolder(View view)
    {
        super(view);

        this.id = (TextView) view.findViewById(R.id.id);
        this.name = (TextView) view.findViewById(R.id.name);
        this.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        this.contact_no = (TextView) view.findViewById(R.id.contact_no);
        this.address = (TextView) view.findViewById(R.id.address);
        this.medical_conditions = (TextView) view.findViewById(R.id.medical_conditions);
        this.latitude = (TextView) view.findViewById(R.id.latitude);
        this.longitude = (TextView) view.findViewById(R.id.longitude);
        this.blood_group = (TextView) view.findViewById(R.id.blood);
        this.state = (TextView) view.findViewById(R.id.state);
        this.email = (TextView) view.findViewById(R.id.email);
        view.setClickable(true);
    }
}
