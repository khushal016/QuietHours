package com.hidtechs.quiethours;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;


public class SetTimeActivity extends AppCompatActivity implements CheckBox.OnCheckedChangeListener {

    TextView fromTime, toTime, fromTimeShow, toTimeShow;
    static final int FROMTEXT_DIALOG_ID = 0;
    static final int TOTEXT_DIALOG_ID = 1;
    int fromHour_x,fromMinute_x,toHour_x,toMinute_x;
    CheckBox check_all, check_mon, check_tue, check_wed, check_thu, check_fri, check_sat, check_sun;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        fromTimeShow = (TextView) findViewById(R.id.from_time_show);
        toTimeShow = (TextView) findViewById(R.id.to_time_show);
        check_all = (CheckBox) findViewById(R.id.always);
        check_all.setOnCheckedChangeListener(this);
        preferences=getSharedPreferences("MyFiles",MODE_PRIVATE);
        fromHour_x=preferences.getInt("fromHour", 22);
        fromMinute_x=preferences.getInt("fromMinute", 00);
        fromTimeShow.setText(fromHour_x+" : "+fromMinute_x);
        toHour_x=preferences.getInt("toHour",06);
        toMinute_x=preferences.getInt("toMinute",00);
        toTimeShow.setText(toHour_x+" : "+toMinute_x);
        showTimePickerDialog();
    }

    public void showTimePickerDialog() {
        fromTime = (TextView) findViewById(R.id.from_time);
        fromTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(FROMTEXT_DIALOG_ID);
                    }
                }
        );
        toTime = (TextView) findViewById(R.id.to_time);
        toTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(TOTEXT_DIALOG_ID);
                    }
                }
        );
    }

    protected Dialog onCreateDialog(int id) {
        if (id == FROMTEXT_DIALOG_ID)
            return new TimePickerDialog(this, ftTimePickerListener, fromHour_x, fromMinute_x, false);
        else if (id == TOTEXT_DIALOG_ID)
            return new TimePickerDialog(this, ttTimePickerListener, toHour_x, toMinute_x, false);
        else return null;
    }

    protected TimePickerDialog.OnTimeSetListener ftTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            fromHour_x = hourOfDay;
            fromMinute_x = minute;
            fromTimeShow.setText(fromHour_x + " : " + fromMinute_x);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putInt("fromHour",fromHour_x);
            editor.putInt("fromMinute",fromMinute_x);
            editor.commit();
        }
    };

    protected TimePickerDialog.OnTimeSetListener ttTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            toHour_x = hourOfDay;
            toMinute_x = minute;
            toTimeShow.setText(toHour_x + " : " + toMinute_x);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putInt("toHour",toHour_x);
            editor.putInt("toMinute",toMinute_x);
            editor.commit();
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        check_mon = (CheckBox) findViewById(R.id.day_mon);
        check_tue = (CheckBox) findViewById(R.id.day_tue);
        check_wed = (CheckBox) findViewById(R.id.day_wed);
        check_thu = (CheckBox) findViewById(R.id.day_thu);
        check_fri = (CheckBox) findViewById(R.id.day_fri);
        check_sat = (CheckBox) findViewById(R.id.day_sat);
        check_sun = (CheckBox) findViewById(R.id.day_sun);
        if (buttonView.isChecked() == true) {
            check_mon.setChecked(true);
            check_tue.setChecked(true);
            check_wed.setChecked(true);
            check_thu.setChecked(true);
            check_fri.setChecked(true);
            check_sat.setChecked(true);
            check_sun.setChecked(true);
        } else {
            check_mon.setChecked(false);
            check_tue.setChecked(false);
            check_wed.setChecked(false);
            check_thu.setChecked(false);
            check_fri.setChecked(false);
            check_sat.setChecked(false);
            check_sun.setChecked(false);
        }
    }
}
