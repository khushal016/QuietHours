package com.hidtechs.quiethours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    SwitchCompat s1, s2;
    LinearLayout timeRow, exceptionRow, dndRow, repeatRow;
    TextView exceptions_number;
    Intent intent;
    SharedPreferences preferences;
    DBHelper dbHelper = new DBHelper(this);
    long exceptionsCount;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("MyFiles",MODE_PRIVATE);
        boolean rbpref = preferences.getBoolean("repeatButton", false);
        boolean sbpref = preferences.getBoolean("silentButton",false);
        setContentView(R.layout.activity_my);
        s1 = (SwitchCompat) findViewById(R.id.silent_switch1);
        s2 = (SwitchCompat) findViewById(R.id.repeat_switch);
        exceptions_number= (TextView) findViewById(R.id.exceptions_number);
        timeRow = (LinearLayout) findViewById(R.id.setTime_layout);
        exceptionRow = (LinearLayout) findViewById(R.id.exceptions_layout);
        dndRow = (LinearLayout) findViewById(R.id.dnd_layout);
        repeatRow = (LinearLayout) findViewById(R.id.repeat_layout);
        repeatRow.setOnClickListener(this);
        dndRow.setOnClickListener(this);
        exceptionRow.setOnClickListener(this);
        s1.setOnClickListener(this);
        s2.setOnClickListener(this);
        timeRow.setOnClickListener(this);
        s1.setChecked(sbpref);
        s2.setChecked(rbpref);
        db = dbHelper.getReadableDatabase();
        isRepeatOn();
        if (preferences.getBoolean("silentButton",false))
        {
            startService(new Intent(this,ScreenService.class));
        }
        else if (!preferences.getBoolean("silentButton", false))
        {
            stopService(new Intent(this,ScreenService.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences.getBoolean("exceptions",false)) {
            exceptionsCount= DatabaseUtils.queryNumEntries(db,DBHelper.CONTACTS_TABLE_NAME);
            exceptions_number.setText(exceptionsCount + " exceptions");
        }
        else if(preferences.getBoolean("allcontacts",false)) {
            exceptions_number.setText("all contacts");
        }
    }

    private void isRepeatOn() {
        if (!s2.isChecked()) {
            timeRow.setVisibility(timeRow.GONE);
        } else {
            timeRow.setVisibility(timeRow.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.equals(dndRow))
        {
            s1.toggle();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("silentButton", s1.isChecked());
            editor.commit();

            if (preferences.getBoolean("silentButton",false))
            {
                startService(new Intent(this,ScreenService.class));
            }
            else if (!preferences.getBoolean("silentButton", false))
            {
                stopService(new Intent(this,ScreenService.class));
            }
        }
        if (v.equals(repeatRow))
        {
            s2.toggle();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("repeatButton", s2.isChecked());
            editor.commit();
            if (s2.isChecked()) {
                timeRow.setVisibility(timeRow.VISIBLE);
            } else {
                timeRow.setVisibility(timeRow.GONE);
            }

        }
        if (v.getId() == s1.getId()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("silentButton", s1.isChecked());
            editor.commit();
            if (preferences.getBoolean("silentButton",false))
            {
                startService(new Intent(this,ScreenService.class));
            }
            else if (!preferences.getBoolean("silentButton", false))
            {
                stopService(new Intent(this,ScreenService.class));
            }

        }
        if (v.getId() == s2.getId()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("repeatButton", s2.isChecked());
            editor.commit();
            if (s2.isChecked()) {
                timeRow.setVisibility(timeRow.VISIBLE);
            } else {
                timeRow.setVisibility(timeRow.GONE);
            }
        }

        if (v.getId() == timeRow.getId()) {
            intent = new Intent(this, SetTimeActivity.class);
            startActivity(intent);
        }

        if(v.getId()== exceptionRow.getId())
        {
            intent=new Intent(this,ExceptionActivity.class);
            startActivity(intent);

        }
    }
}

