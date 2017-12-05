package com.example.toki.contact_second;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private List<ContactDetail> list=new ArrayList<>();


    private final int REQUEST_PERMISSION=1000;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle saveInsutanceState) {
        super.onCreate(saveInsutanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        if(Build.VERSION.SDK_INT >=23)
        {
            checkPermission();
        }else{
            locationActivity();
        }
    }

    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            locationActivity();
        } else {
            requestPermission();
        }
    }

    public void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_PERMISSION);
        }else{
            Toast.makeText(this, "we cant start application", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_PERMISSION);
        }
    }
    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResults){
        if(requestCode==REQUEST_PERMISSION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                locationActivity();
                return;
            }else{
                Toast.makeText(this, "we cant start application if no permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

     protected  void locationActivity(){
             Cursor phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
             while (phone.moveToNext()) {
                 String name = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                 String number = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                 ContactDetail contactDetail = new ContactDetail();
                 contactDetail.setName(name);
                 contactDetail.setPhoneNo(number);
                 list.add(contactDetail);
             }
             phone.close();

             ContactAdapter objAdapter = new ContactAdapter(this, R.layout.activity_all_user, list);
             listView.setAdapter(objAdapter);

             if (list != null && list.size() > 0) {
                 Collections.sort(list, new Comparator<ContactDetail>() {
                     @Override
                     public int compare(ContactDetail lhs, ContactDetail rhs) {
                         return lhs.getName().compareTo(rhs.getName());
                     }
                 });

                 final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                 builder.setTitle("Contact detail");
                 builder.setMessage(list.size() + "Contact Found");
                 builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int which) {
                         dialogInterface.dismiss();
                     }
                 });
                 builder.show();
             } else {
                 Toast.makeText(this, "NO Contact FOUND", Toast.LENGTH_SHORT).show();
             }
         }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
       ContactDetail detail= (ContactDetail) listView.getItemAtPosition(position);
       showCallDialog(detail.getName(),detail.getPhoneNo());
    }

    public void showCallDialog(String name ,final String phoneNo) {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
       alert.setTitle("call??");
        alert.setMessage("are you sure want to call" +name+ "?");

        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }

        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String phoneno="title"+phoneNo;
                Intent intent=new Intent(Intent.ACTION_DIAL,Uri.parse(phoneno));

                if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                startActivity(intent);
            }
        });
        alert.show();
    }
}
