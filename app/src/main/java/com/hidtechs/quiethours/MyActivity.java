package com.hidtechs.quiethours;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private AudioManager audioManager;
    Boolean phoneIsSilent = false;
    SwitchCompat s1, s2;
    LinearLayout timerow;
    Intent intent;
    TextView t1,t2;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getPreferences(MODE_PRIVATE);
        boolean rbpref = preferences.getBoolean("repeatButton", false);
        setContentView(R.layout.activity_my);
        s1 = (SwitchCompat) findViewById(R.id.silent_switch1);
        s2 = (SwitchCompat) findViewById(R.id.repeat_switch);
        timerow = (LinearLayout) findViewById(R.id.linearLayout3);
        t1 = (TextView) findViewById(R.id.setTime);
        t2 = (TextView) findViewById(R.id.exceptions_settings);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        s1.setOnClickListener(this);
        s2.setOnClickListener(this);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        s2.setChecked(rbpref);
        isRepeatOn();

    }

    private void isRepeatOn() {
        if (!s2.isChecked()) {
            timerow.setVisibility(timerow.GONE);
        } else {
            timerow.setVisibility(timerow.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPhoneSilent();
        s1.setChecked(phoneIsSilent);

    }


    public void isPhoneSilent() {
        int i = audioManager.getRingerMode();
        if (i == AudioManager.RINGER_MODE_SILENT || i == AudioManager.RINGER_MODE_VIBRATE) {
            phoneIsSilent = true;
        } else {
            phoneIsSilent = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == s1.getId()) {
            boolean on = s1.isChecked();
            if (on) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(this, "Silent mode on", Toast.LENGTH_SHORT).show();

            } else {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(this, "Silent mode off", Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId() == s2.getId()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("repeatButton", s2.isChecked());
            editor.commit();
            if (s2.isChecked()) {
                timerow.setVisibility(timerow.VISIBLE);
            } else {
                timerow.setVisibility(timerow.GONE);
            }
        }

        if (v.getId() == t1.getId()) {
            intent = new Intent(this, SetTimeActivity.class);
            startActivity(intent);
        }

        if(v.getId()==t2.getId())
        {
            intent=new Intent(this,ExceptionActivity.class);
            startActivity(intent);

        }
    }
}

