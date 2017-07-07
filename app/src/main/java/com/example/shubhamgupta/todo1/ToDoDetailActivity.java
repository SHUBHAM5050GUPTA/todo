package com.example.shubhamgupta.todo1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
    public static int i;
    public static final Boolean is24HourView=true;
    DatePickerDialog datePickerDialog;
    String id;
    long date;
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

        Intent intent=getIntent();
        String name= intent.getStringExtra(IntentConstants.TODO_NAME);
        String reminddate= intent.getStringExtra(IntentConstants.TODO_REMIND_DATE);
        String remindtime= intent.getStringExtra(IntentConstants.TODO_REMIND_TIME);
        String duedate= intent.getStringExtra(IntentConstants.TODO_DUE_DATE);
        id=intent.getStringExtra(IntentConstants.TODO_ID);

        textView.setText(name);
        nameEditText.setText(name);
        reminddateEditText.setText(reminddate);
        remindtimeEditText.setText(remindtime);
        duedateEditText.setText(duedate);

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

            ToDoDatabase toDoDatabase=new ToDoDatabase(ToDoDetailActivity.this);
            SQLiteDatabase database=toDoDatabase.getWritableDatabase();

            ContentValues contentValues=new ContentValues();
            contentValues.put(ToDoDatabase.TODO_NAME,name);
            contentValues.put(ToDoDatabase.TODO_REMIND_DATE,reminddate);
            contentValues.put(ToDoDatabase.TODO_REMIND_TIME,remindtime);
            contentValues.put(ToDoDatabase.TODO_DUE_DATE,duedate);
            if(id==null) {
                database.insert(ToDoDatabase.TODO_TABLE, null, contentValues);
            }
            else
            {
                database.update(ToDoDatabase.TODO_TABLE,contentValues,ToDoDatabase.TODO_ID+"="+id,null);
            }
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
                    // Calendar calendar=Calendar.getInstance();
                    reminddateEditText.setText(dayOfMonth + "/" + month + "/" + year);

                }
            }, initialYear, initialMonth, initialDay);
            datePickerDialog.show();
        }
        else if(i==CODE_2) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Calendar calendar=Calendar.getInstance();
                    duedateEditText.setText(dayOfMonth + "/" + month + "/" + year);
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
            //    Calendar calendar=Calendar.getInstance();
                remindtimeEditText.setText(hourOfDay+":"+minute);

            }
        },hour,minute,is24HourView);
        timePickerDialog.show();
    }
}
