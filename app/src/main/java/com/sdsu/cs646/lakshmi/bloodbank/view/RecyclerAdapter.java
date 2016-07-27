package com.sdsu.cs646.lakshmi.bloodbank.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdsu.cs646.lakshmi.bloodbank.constants.Constant;
import com.sdsu.cs646.lakshmi.bloodbank.R;
import com.sdsu.cs646.lakshmi.bloodbank.domain.DonorData;


import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolder>
{
    private List<DonorData> donorDataList;
    private Context context;
    private int focusedItem = 0;

    public RecyclerAdapter( List<DonorData> donorDataList)
    {
        this.donorDataList = donorDataList;

    }

    public RecyclerAdapter(Context context, List<DonorData> donorDataList)
    {
        this.donorDataList = donorDataList;
        this.context = context;
    }
    @Override
    public ListRowViewHolder onCreateViewHolder(final ViewGroup viewGroup, int position)
    {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        ListRowViewHolder holder = new ListRowViewHolder(v);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("List Size", Integer.toString(getItemCount()));
                TextView textView_id = (TextView) v.findViewById(R.id.id);
                TextView textView_name = (TextView) v.findViewById(R.id.name);
                TextView textView_contact_no = (TextView) v.findViewById(R.id.contact_no);
                TextView textView_address = (TextView) v.findViewById(R.id.address);
                TextView textView_medical_conditions = (TextView) v.findViewById(R.id.medical_conditions);
                TextView textView_latitude = (TextView) v.findViewById(R.id.latitude);
                TextView textView_longitude = (TextView) v.findViewById(R.id.longitude);
                TextView textView_blood_group = (TextView) v.findViewById(R.id.blood);
                TextView textView_state = (TextView) v.findViewById(R.id.state);
                TextView textView_email = (TextView) v.findViewById(R.id.email);


                String id = ""+textView_id.getText();
                String latitude = ""+textView_latitude.getText();
                String longitude = ""+textView_longitude.getText();
                String name = ""+textView_name.getText();
                String contact_no = ""+textView_contact_no.getText();
                String address = ""+textView_address.getText();
                String medical_condition = ""+textView_medical_conditions.getText();
                String blood = "" +textView_blood_group.getText();
                String state = "" + textView_state.getText();
                String email = "" + textView_email.getText();


                Intent intent = new Intent(context, ViewDonorsActivity.class);

                intent.putExtra(Constant.STR_LATITUDE, latitude);
                intent.putExtra(Constant.STR_LONGITUDE, longitude);
                intent.putExtra(Constant.STR_NAME, name);
                intent.putExtra(Constant.STR_CONTACT, contact_no);
                intent.putExtra(Constant.STR_ADDRESS, address);
                intent.putExtra(Constant.STR_MEDICAL, medical_condition);
                intent.putExtra(Constant.STR_BLOOD, blood);
                intent.putExtra(Constant.STR_STATE, state);
                intent.putExtra(Constant.STR_EMAIL, email);
                intent.putExtra(Constant.STR_ID, id);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
        return holder;

    }
    @Override
    public void onBindViewHolder(final ListRowViewHolder listRowViewHolder, int position)
    {
        DonorData donorData = null;
        String abc = null;
        if (donorDataList.get(position) == null)
        {
            return;
        }
        donorData = donorDataList.get(position);

        listRowViewHolder.itemView.setSelected(focusedItem == position);
        listRowViewHolder.getLayoutPosition();
        listRowViewHolder.name.setText((donorData.getName()));
        listRowViewHolder.contact_no.setText(donorData.getContact_no());
        listRowViewHolder.address.setText(donorData.getAddress());
        listRowViewHolder.medical_conditions.setText(donorData.getMedical_conditions());
        listRowViewHolder.longitude.setText(donorData.getLongitude());
        listRowViewHolder.latitude.setText(donorData.getLatitude());
        listRowViewHolder.email.setText(donorData.getEmail_id());
        listRowViewHolder.state.setText(donorData.getState());
        listRowViewHolder.blood_group.setText(donorData.getBlood_group());
        listRowViewHolder.id.setText(donorData.getId());

    }

    public void clearAdapter()
    {
        donorDataList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount()
    {
        return (null != donorDataList ? donorDataList.size() : 0);
    }
}
