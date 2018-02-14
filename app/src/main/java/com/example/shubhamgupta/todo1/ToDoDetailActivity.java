package com.example.shubhamgupta.todo1;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ToDoDetailActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText reminddateEditText;
    EditText duedateEditText;
    EditText remindtimeEditText;
    TextView textView;
    EditText descriptionEditText;
    public static int i;
    public static final Boolean is24HourView=true;
    DatePickerDialog datePickerDialog;
    int x;
    int id;
    int addId;
    int calYear,calMonth,calDay,calHour,calMinutes;
    long epocTime;
    public static final  int CODE_1=1;
    public static final  int CODE_2=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);

        textView=(TextView) findViewById(R.id.tododetail_textview1);
        nameEditText= (EditText) findViewById(R.id.tododetail_edittext1);

        reminddateEditText= (EditText) findViewById(R.id.tododetail_edittext2);
        //reminddateEditText.setInputType(InputType.TYPE_NULL);

        remindtimeEditText= (EditText) findViewById(R.id.tododetail_edittext3);
        //remindtimeEditText.setInputType(InputType.TYPE_NULL);

        duedateEditText= (EditText) findViewById(R.id.tododetail_edittext4);
       // duedateEditText.setInputType(InputType.TYPE_NULL);

        descriptionEditText= (EditText) findViewById(R.id.todo_detail);

        Intent intent=getIntent();
        String name= intent.getStringExtra(IntentConstants.TODO_NAME);
        String reminddate= intent.getStringExtra(IntentConstants.TODO_REMIND_DATE);
        String remindtime= intent.getStringExtra(IntentConstants.TODO_REMIND_TIME);
        String duedate= intent.getStringExtra(IntentConstants.TODO_DUE_DATE);
        String description=intent.getStringExtra(IntentConstants.TODO_DESCRIPTION);
        id=Integer.parseInt(intent.getStringExtra(IntentConstants.TODO_ID));

        textView.setText(name);
        nameEditText.setText(name);
        reminddateEditText.setText(reminddate);
        remindtimeEditText.setText(remindtime);
        duedateEditText.setText(duedate);
        descriptionEditText.setText(description);

        if(reminddate!=null&&remindtime!=null)
        {
            String string[]=reminddate.split("/");
            calYear=Integer.parseInt(string[2]);
            calMonth=Integer.parseInt(string[1])-1;
            calDay=Integer.parseInt(string[0]);
            String string1[]=remindtime.split(":");
            calHour=Integer.parseInt(string1[0]);
            calMinutes=Integer.parseInt(string1[1]);
            Log.i("strinh",calYear+"    "+calMonth+"    "+calDay+"       "+calHour+"     "+calMinutes);
        }

        reminddateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 i=CODE_1;
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(calendar.YEAR);
                int month=calendar.get(calendar.MONTH);
                showDatePicker(ToDoDetailActivity.this,year,month,1);
            }
        });
        duedateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=CODE_2;
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(calendar.YEAR);
                int month=calendar.get(calendar.MONTH);
                showDatePicker(ToDoDetailActivity.this,year,month,1);
            }
        });

        remindtimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int hour=calendar.get(calendar.HOUR_OF_DAY);
                int minute=calendar.get(calendar.MINUTE);
                showtimepicker(ToDoDetailActivity.this,hour,minute,is24HourView);
            }
        });


    }

    private void setAlarm(int Todo_Id,String Todo_Name) {

        AlarmManager alarmManager= (AlarmManager) ToDoDetailActivity.this.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(ToDoDetailActivity.this,AlarmReciever.class);
        i.putExtra("TODO_ID",Todo_Id+"");
        i.putExtra("TODO_TITLE",Todo_Name);
        Log.i("dhhdkk",Todo_Name+"      "+Todo_Id);

        Calendar mCalender=Calendar.getInstance();
        mCalender.set(calYear,calMonth,calDay,calHour,calMinutes,0);
        epocTime=mCalender.getTime().getTime();

        long alarmTime=epocTime;
        PendingIntent pendingIntent=PendingIntent.getBroadcast(ToDoDetailActivity.this,Todo_Id,i,0);
        Log.i("time1",alarmTime+"");
        Log.i("time2",System.currentTimeMillis()+"");

        alarmManager.set(AlarmManager.RTC,alarmTime,pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.savebutton)
        {
            String name=nameEditText.getText().toString();
            String reminddate=reminddateEditText.getText().toString();
            String remindtime=remindtimeEditText.getText().toString();
            String duedate=duedateEditText.getText().toString();
            String description=descriptionEditText.getText().toString();

            if(name.trim().equalsIgnoreCase(""))
            {
                nameEditText.setError("this field cant be empty");
                return true;
            }
            if(reminddate.trim().equalsIgnoreCase(""))
            {
                reminddateEditText.setError("this field cant be empty");
                return true;
            }
            if(remindtime.trim().equalsIgnoreCase(""))
            {
                remindtimeEditText.setError("this field cant be empty");
                return true;
            }
            if(duedate.trim().equalsIgnoreCase(""))
            {
                duedateEditText.setError("this field cant be empty");
                return true;
            }

            if(description.trim().equalsIgnoreCase(""))
            {
                descriptionEditText.setError("this field cant be empty");
                return true;
            }

            Calendar mCalender=Calendar.getInstance();
            mCalender.set(calYear,calMonth,calDay,calHour,calMinutes,0);
            epocTime=mCalender.getTime().getTime();
            if(epocTime<System.currentTimeMillis())
            {
                reminddateEditText.setError("date has passed");
                remindtimeEditText.setError("time has passed");
                return true;
            }

            ToDoDatabase toDoDatabase=new ToDoDatabase(ToDoDetailActivity.this);
            SQLiteDatabase database=toDoDatabase.getWritableDatabase();

            ContentValues contentValues=new ContentValues();
            contentValues.put(ToDoDatabase.TODO_NAME,name);
            contentValues.put(ToDoDatabase.TODO_REMIND_DATE,reminddate);
            contentValues.put(ToDoDatabase.TODO_REMIND_TIME,remindtime);
            contentValues.put(ToDoDatabase.TODO_DUE_DATE,duedate);
            contentValues.put(ToDoDatabase.TODO_DESCRIPTION,description);
            contentValues.put(ToDoDatabase.TODO_IMAGE,ToDoDatabase.TODO_DESCRIPTION);
            if(id==0) {
                database.insert(ToDoDatabase.TODO_TABLE, null, contentValues);
                ToDoDatabase addToDoDatabase=new ToDoDatabase(ToDoDetailActivity.this);
                SQLiteDatabase addDatabase=toDoDatabase.getReadableDatabase();
                Cursor mCursor=addDatabase.query(ToDoDatabase.TODO_TABLE,null,null,null,null,null,null);
                mCursor.moveToLast();
                 addId=mCursor.getInt(mCursor.getColumnIndex(ToDoDatabase.TODO_ID));
                x=addId;
            }
            else
            {
                database.update(ToDoDatabase.TODO_TABLE,contentValues,ToDoDatabase.TODO_ID+"="+id,null);
                x=id;
            }
            setAlarm(x,name);
            setResult(RESULT_OK);
            finish();


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }


    public void showDatePicker(Context context,int initialYear,int initialMonth,int initialDay)
    {
        if(i==CODE_1) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calYear=year;
                    calMonth=month;
                    calDay=dayOfMonth;
                    reminddateEditText.setText(dayOfMonth + "/" + (month+1) + "/" + year);

                }
            }, initialYear, initialMonth, initialDay);
            datePickerDialog.show();
        }
        else if(i==CODE_2) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    duedateEditText.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }
            }, initialYear, initialMonth, initialDay);
            datePickerDialog.show();
        }

    }

    public void showtimepicker(Context context,int hour,int minute,Boolean is24HourView)
    {
        TimePickerDialog timePickerDialog=new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour;
                String minutes;
                if(hourOfDay<10)
                {
                     hour="0"+hourOfDay;
                }
                else
                {
                    hour=hourOfDay+"";
                }
                if(minute<10)
                {
                     minutes="0"+minute;
                }
                else
                {
                    minutes=minute+"";
                }
                calHour=Integer.parseInt(hour);
                calMinutes=Integer.parseInt(minutes);

                remindtimeEditText.setText(hour+":"+minutes);

            }
        },hour,minute,is24HourView);
        timePickerDialog.show();
    }
}
