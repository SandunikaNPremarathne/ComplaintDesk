package com.example.complaintapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DatetimeActivity extends AppCompatActivity {
    TextView time;
    TimePicker simpleTimePicker;
    String category;
    DatePicker simpleDatePicker;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        Intent intent = getIntent();
        Calendar calendar=Calendar.getInstance();
        if (intent.hasExtra("category")) {
            category = intent.getStringExtra("category");
            Toast.makeText(this, category, Toast.LENGTH_SHORT).show();
        }


        //  initiate the view's
        time = (TextView) findViewById(R.id.time);
        simpleTimePicker = (TimePicker) findViewById(R.id.simpleTimePicker);
        simpleTimePicker.setIs24HourView(false); // used to display AM/PM mode

        // perform set on time changed listener event
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                time.setText(hourOfDay + ":" + minute); // set the current time in text view
            }
        });


        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        simpleDatePicker.setMaxDate(calendar.getTimeInMillis());
        submit = (Button) findViewById(R.id.nextbtn);
        // perform click event on submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker
                String day = "" + simpleDatePicker.getDayOfMonth();
                String month = "" + (simpleDatePicker.getMonth() + 1);
                String year = "" + simpleDatePicker.getYear();
                // display the values by using a toast
                Intent intent = new Intent(DatetimeActivity.this, LocationActivity.class);
                intent.putExtra("category", category);
                intent.putExtra("date",day+"/"+month+"/"+year);
                intent.putExtra("time",time.getText());
                startActivity(intent);
                finish();
               // Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}
