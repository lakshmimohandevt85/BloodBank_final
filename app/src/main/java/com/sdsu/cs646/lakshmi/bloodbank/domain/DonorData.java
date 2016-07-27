package com.sdsu.cs646.lakshmi.bloodbank.domain;

/**
 * This is a Plain Old Java Object (POJO) class for
 * holding the Donor data..
 */
public class DonorData
{

    /** name of the donor **/
    private String name;
    /** contact # of the donor **/
    private String contact_no;
    /** address of the donor **/
    private String address;
    /** medical condition of the donor **/
    private String medical_conditions;
    /** holds the latitude value **/
    private String latitude;
    /** holds the longitude  value **/
    private String longitude;
    /** state of the donor **/
    private String state;
    /** blood group of the donor **/
    private String blood_group;

    /** email id of the user **/
    private String email_id;

    private String id;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContact_no()
    {
        return contact_no;
    }

    public void setContact_no(String contact_no)
    {
        this.contact_no = contact_no;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getMedical_conditions()
    {
        return medical_conditions;
    }

    public void setMedical_conditions(String medical_conditions)
    {
        this.medical_conditions = medical_conditions;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getBlood_group()
    {
        return blood_group;
    }

    public void setBlood_group(String blood_group)
    {
        this.blood_group = blood_group;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id)
    {
        this.email_id = email_id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "DonorData{" +
                "name='" + name + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", address='" + address + '\'' +
                ", medical_conditions='" + medical_conditions + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", state='" + state + '\'' +
                ", blood_group='" + blood_group + '\'' +
                ", email_id='" + email_id + '\'' +
                '}';
    }

    /**  No arg constructor **/

    public DonorData()
    {
        super();
    }

    /**  String arg constructor with location details.  **/

    public DonorData(String name, String contact_no, String address, String medical_conditions,
                     String latitude, String longitude, String state,
                     String blood_group, String email_id)
    {
        this.name = name;
        this.contact_no = contact_no;
        this.address = address;
        this.medical_conditions = medical_conditions;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
        this.blood_group = blood_group;
        this.email_id = email_id;
    }
}
