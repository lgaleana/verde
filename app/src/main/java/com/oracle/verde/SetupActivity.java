package com.oracle.verde;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.NumberFormat;
import java.util.Calendar;


public class SetupActivity extends ActionBarActivity {

    static String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    static String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static String standard = "AM";
    static int ride = 0;

    static boolean departure = true;

    static TextView date, time;
    static int year, month, day, weekDay, departHour, departMinute, arriveHour, arriveMinute;
    static {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        weekDay = c.get(Calendar.DAY_OF_WEEK);

        departHour = 8;
        departMinute = 0;
        arriveHour = 10;
        arriveMinute = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(2);
        date = (TextView) findViewById(R.id.depart_date);
        date.setText(days[weekDay - 1] + ", " + months[month] + " " + day + ", " + year);
        time = (TextView) findViewById(R.id.depart_time);
        time.setText(nf.format(departHour) + ":" + nf.format(departMinute) + " " + standard);

        Integer[] noPassengers = {1, 2, 3, 4};
        Spinner spinner = (Spinner) findViewById(R.id.passengers_selector);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, noPassengers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.drive_radio:
                if (checked) {
                    ride = -1;
                    break;
                }
            case R.id.either_radio:
                if (checked) {
                    ride = 0;
                    break;
                }
            case R.id.ride_radio:
                if (checked) {
                    ride = 1;
                    break;
                }
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker v, int y, int m, int d) {
            year = y;
            month = m;
            day = d;

            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            int weekDay = c.get(Calendar.DAY_OF_WEEK);

            date.setText(days[weekDay - 1] + ", " + months[month] + " " + day + ", " + year);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hour = departHour;
            int minute = departMinute;
            if(!departure) {
                hour = arriveHour;
                minute = arriveMinute;
            }

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if(hourOfDay > 12) {
                standard = "PM";
                hourOfDay -= 12;
            }
            else if(hourOfDay == 0)
                hourOfDay = 12;

            if(departure) {
                departHour = hourOfDay;
                departMinute = minute;
            }
            else {
                arriveHour = hourOfDay;
                arriveMinute = minute;
            }

            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumIntegerDigits(2);
            time.setText(nf.format(hourOfDay) + ":" + nf.format(minute) + " " + standard);
        }
    }
}
