package com.hidtechs.quiethours;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class ExceptionActivity extends AppCompatActivity implements View.OnClickListener,MyAdapter.DeleteButtonListner {

    private  String name;
    private String number;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView addcontacts,exceptions,arrow1,arrow2;
    TextView allcontacts;
    SwitchCompat s1;
    ImageView addButton;
    LinearLayout l4,l5,allowLayout,allContactsLayout,exceptionsLayout;
    Intent intent;
    final int PICK_CONTACT=1;
    List<Information> data;
    int icon;
    boolean allContactsClicked,exceptionsClicked=false;
    SharedPreferences preferences;
    DBHelper dbHelper = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        allcontacts= (TextView) findViewById(R.id.allContacts);
        exceptions= (TextView) findViewById(R.id.exceptions);
        s1= (SwitchCompat) findViewById(R.id.calls_switch);
        arrow1= (TextView) findViewById(R.id.arrow1);
        arrow2= (TextView) findViewById(R.id.arrow2);
        addButton= (ImageView) findViewById(R.id.add_button);
        l4= (LinearLayout) findViewById(R.id.linearLayout4);
        l5= (LinearLayout) findViewById(R.id.linearLayout5);
        allowLayout= (LinearLayout) findViewById(R.id.allowcalls);
        allContactsLayout= (LinearLayout) findViewById(R.id.linearLayout2);
        exceptionsLayout= (LinearLayout) findViewById(R.id.linearLayout3);
        allowLayout.setOnClickListener(this);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        preferences = getSharedPreferences("MyFiles", MODE_PRIVATE);
        boolean cbpref = preferences.getBoolean("callsButton", false);
        allContactsClicked =preferences.getBoolean("allcontacts",false);
        exceptionsClicked =preferences.getBoolean("exceptions",false);
        s1.setChecked(cbpref);
        addcontacts= (TextView) findViewById(R.id.add_contact);
        addButton.setOnClickListener(this);
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("callsButton", s1.isChecked());
                editor.commit();
            }
        });
        allContactsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allContactsClicked=true;
                exceptionsClicked=false;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("allcontacts", allContactsClicked);
                editor.putBoolean("exceptions", exceptionsClicked);
                editor.commit();
                arrow1.setVisibility(arrow1.VISIBLE);
                arrow2.setVisibility(arrow2.GONE);
                exceptions.setTextColor(getResources().getColor(R.color.primary_dark_material_dark));
                arrow2.setTextColor(getResources().getColor(R.color.primary_dark_material_dark));
                allcontacts.setTextColor(getResources().getColor(R.color.accentColor));
                arrow1.setTextColor(getResources().getColor(R.color.accentColor));
                mRecyclerView.setVisibility(mRecyclerView.GONE);
                l4.setVisibility(l4.GONE);
                l5.setVisibility(l5.GONE);

            }
        });
        exceptionsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allContactsClicked = false;
                exceptionsClicked = true;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("exceptions", exceptionsClicked);
                editor.putBoolean("allcontacts", allContactsClicked);
                editor.commit();
                arrow1.setVisibility(arrow1.GONE);
                arrow2.setVisibility(arrow2.VISIBLE);
                allcontacts.setTextColor(getResources().getColor(R.color.primary_dark_material_dark));
                arrow1.setTextColor(getResources().getColor(R.color.primary_dark_material_dark));
                exceptions.setTextColor(getResources().getColor(R.color.accentColor));
                arrow2.setTextColor(getResources().getColor(R.color.accentColor));
                mRecyclerView.setVisibility(mRecyclerView.VISIBLE);
                l4.setVisibility(l4.VISIBLE);
                l5.setVisibility(l5.VISIBLE);

            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        if (allContactsClicked)
        {
            allContactsLayout.performClick();
        }
        else if(exceptionsClicked)
        {
            exceptionsLayout.performClick();
        }

        data = dbHelper.getAllContacts();

        mAdapter = new MyAdapter(this, data);
        mAdapter.setDeleteButtonListner(this);
        mRecyclerView.setAdapter(mAdapter);


    }


    public  List<Information> getData()
    {
        Information current=new Information();
        current.name = name;
        current.iconId = icon;
        current.number=number;
        data.add(current);
        return data;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(allowLayout))
        {
            s1.toggle();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("callsButton", s1.isChecked());
            editor.commit();



        }
        if(v.getId()==addButton.getId())
        {
            intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_CONTACT) {
            // Perform a query to the contact's content provider for the contact's name
            Cursor cursor = managedQuery(data.getData(), null, null, null, null);
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d("HELLO : ", " "+contactId);
                String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                Log.d("HELLO : ", " "+hasPhone);
                if ((Integer.parseInt(hasPhone)==1 )){
                    Log.d("HELLO : ", " IN if");
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                            null, null);
                    while (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        number=number.replaceAll("\\s+", "");
                    }
                    phones.close();
                }
                Log.d("HELLO : ", " "+name);
                Log.d("HELLO : ", " "+number);
                    mAdapter.setData(getData());
                    mAdapter.notifyDataSetChanged();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DBHelper.CONTACTS_COLUMN_NAME, name);
                values.put(DBHelper.CONTACTS_COLUMN_PHONE, number);
                db.insert(DBHelper.CONTACTS_TABLE_NAME,null,values);


            }
        }

    }

    @Override
    public void deleteClicked(String name) {
        dbHelper.deleteContact(name);

    }
}
