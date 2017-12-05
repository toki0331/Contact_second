package com.example.toki.contact_second;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<ContactDetail> {
    private Activity activity;
    private List<ContactDetail> items;
    private int row;
    private ContactDetail objDetail;

    public ContactAdapter(@NonNull Activity act, int row, @NonNull List<ContactDetail> items) {
        super(act, row, items);
        this.activity = act;
        this.items = items;
        this.row = row;
    }

    public View getview(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        ViewHolder holder;
        if(view==null)
        {
            LayoutInflater inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(row,null);
            holder=new ViewHolder();
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)view.getTag();
        }
        if(items==null || (position+1)>items.size())
        {
            return view;
        }
        objDetail=items.get(position);

        holder.tvname=view.findViewById(R.id.tvname);
        holder.tvphone=view.findViewById(R.id.tvnumber);

        if(holder.tvname !=null && objDetail.getName() !=null &&objDetail.getName().trim().length()>0)
        {
            holder.tvname.setText(Html.fromHtml(objDetail.getName()));
        }
        if(holder.tvphone!=null && objDetail.getPhoneNo()!=null &&objDetail.getPhoneNo().trim().length()>0)
        {
            holder.tvphone.setText(Html.fromHtml(objDetail.getPhoneNo()));
        }
        return view;
    }
    class ViewHolder{
        public TextView tvname,tvphone;
    }

}