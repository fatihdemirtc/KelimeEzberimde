package com.kelimeezberimde;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ChoiceClock extends Activity {
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_clock);

        Button btnclc1, btnclc2, btnclc3, btnclc4, btnok;
        btnclc1 = (Button) findViewById(R.id.btnclc1);
        btnclc2 = (Button) findViewById(R.id.btnclc2);
        btnclc3 = (Button) findViewById(R.id.btnclc3);
        btnclc4 = (Button) findViewById(R.id.btnclc4);
        btnok = (Button) findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPickerDialog(false);
            }
        });
    }

    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                ChoiceClock.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){

                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
        }};

    private void setAlarm(Calendar alarmCalender){


        Toast.makeText(getApplicationContext(),"Alarm Ayarlandı!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("",true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);

    }
    //region butonlar
    //        btnfav.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intentfav;
//                intentfav = new Intent(view.getContext(), FavoriWords.class);
//                startActivity(intentfav);
//            }
//        });
//        btncontinuing.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intentcontinuing;
//                intentcontinuing = new Intent(view.getContext(), ContinuingWords.class);
//                startActivity(intentcontinuing);
//            }
//        });
//        btncompleted.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intentcompleted;
//                intentcompleted = new Intent(view.getContext(), CompletedWords.class);
//                startActivity(intentcompleted);
//            }
//        });
//        btntestyourself.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intenttest;
//                intenttest = new Intent(view.getContext(), TestYourself.class);
//                startActivity(intenttest);
//            }
//        });
    //endregion

}
