package com.albert.duedatecalculator;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final Calendar c = Calendar.getInstance();

    private EditText etDatePicker;
    private EditText etTurnaroundHours;
    private Button btnSubmit;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDatePicker = findViewById(R.id.et_date);
        etDatePicker.setOnClickListener(this);
        etTurnaroundHours = findViewById(R.id.et_turnaround_hours);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_date:
                getDate();
                break;
            case R.id.btn_submit:
                int turnaroundHours;
                try {
                    turnaroundHours = Integer.parseInt(etTurnaroundHours.getText().toString());
                } catch (NumberFormatException e) {
                    turnaroundHours = 0;
                }
                getDueDate(turnaroundHours);
                break;
        }
    }

    private void getDate() {
        datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            etDatePicker.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.YEAR, year);
            getTime();
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getTime() {
        timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dateFormat.setTimeZone(c.getTimeZone());
            etDatePicker.setText(dateFormat.format(c.getTime()));
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void getDueDate(int turnaroundHours) {
        final int WORKING_HOURS = 8;
        final int DAYS_A_WEEK = 7;
        final int WORKING_DAYS_A_WEEK = 5;
        final int END_HOUR = 17;
        final int RESTING_HOURS = 16;
        int days = turnaroundHours / WORKING_HOURS;
        int weeks = days / WORKING_DAYS_A_WEEK;
        c.add(Calendar.DAY_OF_MONTH, weeks * DAYS_A_WEEK);
        int resDays = days % WORKING_DAYS_A_WEEK;
        c.add(Calendar.DAY_OF_MONTH, resDays);
        int resHours = turnaroundHours % WORKING_HOURS;
        if (END_HOUR - c.get(Calendar.HOUR_OF_DAY) > resHours) {
            c.add(Calendar.HOUR_OF_DAY, resHours);
        } else {
            c.add(Calendar.HOUR_OF_DAY, resHours + RESTING_HOURS);
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            c.add(Calendar.DAY_OF_MONTH, 2);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setTimeZone(c.getTimeZone());
        tvResult.setText(dateFormat.format(c.getTime()));
    }
}