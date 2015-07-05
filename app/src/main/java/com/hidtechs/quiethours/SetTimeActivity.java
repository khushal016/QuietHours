package com.hidtechs.quiethours;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;


public class SetTimeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView fromTime, toTime, fromTimeShow, toTimeShow;
    TimePicker tp;
    Time t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        fromTime = (TextView) findViewById(R.id.from_time);
        fromTimeShow = (TextView) findViewById(R.id.from_time_show);
        toTime = (TextView) findViewById(R.id.to_time);
        toTimeShow = (TextView) findViewById(R.id.to_time_show);
        tp = (TimePicker) findViewById(R.id.timePicker);
        fromTime.setOnClickListener(this);
        toTime.setOnClickListener(this);
        if (tp.is24HourView()) {
            fromTimeShow.setText("22:00 PM");
            toTimeShow.setText("06:00 AM");
        } else {
            fromTimeShow.setText("10:00 PM");
            toTimeShow.setText("06:00 AM");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fromTime.getId()) {
            fromTime.setTextColor(getResources().getColor(R.color.highlihtColor));
            int hour = tp.getCurrentHour();
            int minute = tp.getCurrentMinute();

        }
    }

//
//    @Override
//
//    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//        fromTimeShow.setText(hourOfDay + ":" + minute);
//    }
}
