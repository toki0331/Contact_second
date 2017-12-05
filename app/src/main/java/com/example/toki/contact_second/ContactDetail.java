package com.example.toki.contact_second;

import android.widget.ArrayAdapter;

/**
 * Created by toki on 2017/12/04.
 */

public class ContactDetail {
    private String name;
    private String phoneNo;

    public String getName(){return name;}
    public void setName(String name)
    {
        this.name=name;
    }
    public String getPhoneNo()
    {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo)
    {this.phoneNo=phoneNo;
    }



}