package com.sktl.nbrbcurrency;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UsedDate {
    private static final String TAG = "sssUD";
    private Date date;
    private Date previousDate;
    private Date nextDate;
    private String dateString;
    private String previousDateString;
    private String nextDateString;
    private DateFormat dateFormat;
    private Calendar cal;


    public UsedDate() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        nextDate = previousDate = new Date();
        cal = Calendar.getInstance();
        date = cal.getTime();

        dateString = dateFormat.format(date);
        Log.d(TAG, "date = " + dateString);
        Log.d(TAG, "dateFormat.format(cal.getTime()) = " + dateFormat.format(cal.getTime()));
        Log.d(TAG, "cal.getTime() = " + cal.getTime());

        cal.setTime(nextDate);
        cal.add(Calendar.DATE, 1);



        Log.d(TAG, "dateFormat.format(cal.getTime()) = " + dateFormat.format(cal.getTime()));
        Log.d(TAG, "cal.getTime() = " + cal.getTime());

        nextDate = cal.getTime();

        nextDateString = dateFormat.format(nextDate);

        Log.d(TAG, "nextDate = " + dateFormat.format(nextDate));
        Log.d(TAG, "date = " + dateFormat.format(date));

        cal.add(Calendar.DATE, -2);
        Log.d(TAG, "dateFormat.format(cal.getTime()) = " + dateFormat.format(cal.getTime()));
        previousDate = cal.getTime();

        previousDateString = dateFormat.format(previousDate);

        Log.d(TAG, "nextDate = " + dateFormat.format(nextDate));
        Log.d(TAG, "previousDate = " +  previousDateString );
        Log.d(TAG, "date = " + dateFormat.format(date));
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getPreviousDateString() {
        return previousDateString;
    }

    public void setPreviousDateString(String previousDateString) {
        this.previousDateString = previousDateString;
    }

    public String getNextDateString() {
        return nextDateString;
    }

    public void setNextDateString(String nextDateString) {
        this.nextDateString = nextDateString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(Date previousDate) {
        this.previousDate = previousDate;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }


}
