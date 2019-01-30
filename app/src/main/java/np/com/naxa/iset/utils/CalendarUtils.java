package np.com.naxa.iset.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtils {
    private static final String TAG = "CalendarUtils";

    public static String getCurrentDate() {

        Date date = Calendar.getInstance(new Locale("en", "US")).getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = df.format(date);
        Log.d(TAG, "getCurrentDate: "+formattedDate);
        return formattedDate;
    }

    public static String getCurrentTime() {

        Date date = Calendar.getInstance(new Locale("en", "US")).getTime();
        SimpleDateFormat df = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        String formattedTime = df.format(date);
        Log.d(TAG, "getCurrentTime: "+formattedTime);
        return formattedTime;
    }

    public static String getTimeInMilisecond(){

        Date date = Calendar.getInstance(new Locale("en", "US")).getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
        String formattedDate = df.format(date);
        Log.d(TAG, "getCurrentDate: "+formattedDate);
        return formattedDate;


    }


    public static DatePickerDialog datePickerDialog(Context context, EditText etDateDisplayView){
         int mYear, mMonth, mDay;
        // Get Current Date
        final Calendar c = Calendar.getInstance(new Locale("en", "US"));
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etDateDisplayView.setText(year+ "-" +(monthOfYear + 1) + "-" +dayOfMonth  );

                    }
                }, mYear, mMonth, mDay);
        return datePickerDialog;
    }

    public static TimePickerDialog timePickerDialog(Context context, EditText etTimeDisplayView){
        int  mHour, mMinute;
        // Get Current Time
        final Calendar c = Calendar.getInstance(new Locale("en", "US"));
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        etTimeDisplayView.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        return timePickerDialog;

    }




}
