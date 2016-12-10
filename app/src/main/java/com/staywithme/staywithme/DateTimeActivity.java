package com.staywithme.staywithme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeActivity extends AppCompatActivity {

    private EditText dateText;
    private EditText timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        // remove Status bar aka Fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Calendar current = Calendar.getInstance();
        int cHour = current.get(Calendar.HOUR_OF_DAY);
        int cMinute = current.get(Calendar.MINUTE);

        dateText = (EditText) findViewById(R.id.dateText);
        timeText = (EditText) findViewById(R.id.timeText);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        dateText.setText(dateFormat.format(current.getTime()));
        timeText.setText(cHour + ":" + cMinute);

//        Intent i = new Intent(FromActivity.this, ToActivity.class);
//        startActivity(i);

    }

    public void datePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = "";

                        switch(monthOfYear) {
                            case 0:
                                month = "Jan";
                                break;
                            case 1:
                                month = "Feb";
                                break;
                            case 2:
                                month = "Mar";
                                break;
                            case 3:
                                month = "Apr";
                                break;
                            case 4:
                                month = "May";
                                break;
                            case 5:
                                month = "Jun";
                                break;
                            case 6:
                                month = "Jul";
                                break;
                            case 7:
                                month = "Aug";
                                break;
                            case 8:
                                month = "Sep";
                                break;
                            case 9:
                                month = "Oct";
                                break;
                            case 10:
                                month = "Nov";
                                break;
                            case 11:
                                month = "Dec";
                                break;
                        }
                        dateText.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void timePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.DAY_OF_MONTH);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog dpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String hour = "";
                        String minute = "";

                        if(i < 10) {
                            hour = "0" + i;
                        }else {
                            hour = String.valueOf(i);
                        }

                        if(i1 < 10) {
                            minute = "0" + i1;
                        }else {
                            minute = String.valueOf(i1);
                        }
                        timeText.setText(hour + ":" + minute);
                    }

                }, mHour, mMinute, false);
        dpd.show();
    }
}
