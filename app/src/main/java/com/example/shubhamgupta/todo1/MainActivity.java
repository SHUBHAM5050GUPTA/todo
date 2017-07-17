package com.example.shubhamgupta.todo1;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ToDoAdapter.OncheckboxClickListner {

    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE1 = 2;
     ArrayList<ToDoContent> todoList;
    ToDoAdapter toDoAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

         listView = (ListView) findViewById(R.id.list_view);
        todoList = new ArrayList<>();
        toDoAdapter = new ToDoAdapter(this, todoList);

        toDoAdapter.setOnCheckboxClickListner(this);
        listView.setAdapter(toDoAdapter);
        updateToDoList();




        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ToDoDetailActivity.class);
                intent.putExtra(IntentConstants.TODO_NAME, "NAME");
                /*intent.putExtra(IntentConstants.TODO_REMIND_DATE, "REMIND DATE");
                intent.putExtra(IntentConstants.TODO_REMIND_TIME, "REMIND TIME");
                intent.putExtra(IntentConstants.TODO_DUE_DATE, "DUE DATE");*/
                intent.putExtra(IntentConstants.TODO_ID,0+"");
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ToDoDetailActivity.class);
                intent.putExtra(IntentConstants.TODO_NAME,todoList.get(position).TodoName);
                intent.putExtra(IntentConstants.TODO_REMIND_DATE,todoList.get(position).Reminddate);
                intent.putExtra(IntentConstants.TODO_REMIND_TIME,todoList.get(position).Remindtime);
                intent.putExtra(IntentConstants.TODO_DUE_DATE,todoList.get(position).Duedate);
                intent.putExtra(IntentConstants.TODO_ID,todoList.get(position).TodoId+"");
                startActivityForResult(intent, REQUEST_CODE1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                ToDoDatabase toDoDatabase=new ToDoDatabase(MainActivity.this);
               final SQLiteDatabase database=toDoDatabase.getWritableDatabase();
                  final  int pos=position;

                AlertDialog.Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("DELETE THIS TASK?");
                mBuilder.setCancelable(false);
                View mView=getLayoutInflater().inflate(R.layout.dialog_box,null);
                mBuilder.setView(mView);

                mBuilder.setPositiveButton("OK", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.delete(ToDoDatabase.TODO_TABLE,ToDoDatabase.TODO_ID+"="+todoList.get(pos).TodoId,null);
                        updateToDoList();
                        Snackbar.make(view,"TASK DELETED",Snackbar.LENGTH_LONG).show();
                    }
                });

                mBuilder.setNegativeButton("CANCEL", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Snackbar.make(view,"CANCEL",Snackbar.LENGTH_LONG).show();
                    }
                });

                AlertDialog dialog=mBuilder.create();
                dialog.show();

                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                updateToDoList();
                Snackbar.make(listView,"NEW TASK ADDED",Snackbar.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode==REQUEST_CODE1)
        {
            if (resultCode == RESULT_OK) {
                updateToDoList();
                Snackbar.make(listView,"TASK UPDATED",Snackbar.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateToDoList() {

        ToDoDatabase toDoDatabase = new ToDoDatabase(this);
        SQLiteDatabase database = toDoDatabase.getReadableDatabase();
        todoList.clear();
        Cursor cursor = database.query(ToDoDatabase.TODO_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ToDoDatabase.TODO_NAME));
            String remindadate = cursor.getString(cursor.getColumnIndex(ToDoDatabase.TODO_REMIND_DATE));
            String remindtime = cursor.getString(cursor.getColumnIndex(ToDoDatabase.TODO_REMIND_TIME));
            String duedate = cursor.getString(cursor.getColumnIndex(ToDoDatabase.TODO_DUE_DATE));
            int id = cursor.getInt(cursor.getColumnIndex(ToDoDatabase.TODO_ID));
            ToDoContent toDoContent = new ToDoContent(id, name, remindadate, remindtime, duedate);
            todoList.add(toDoContent);

        }
        toDoAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    /*return super.onCreateOptionsMenu(menu);*/
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*return super.onOptionsItemSelected(item);*/
        int id=item.getItemId();
                if(id==R.id.feedback)
                {
//                    Intent mIntent-new Intent();
//                    mIntent.setAction(Intent.ACTION_SENDTO);
//                    Uri mUri=Uri.parse("mailto:shubham5050gupta@gmail.com");
//                    mIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject");
//                    mIntent.setData(mUri);
//                    if(mIntent.resolveActivity(getPackageManager())!=null)
//                    {
//                        startActivity(mIntent);
//                    }

                }
        return true;
    }

    @Override
    public void OncheckboxClick(View v, int position,boolean isChecked) {
        TextView tv = (TextView) v.findViewById(R.id.listItemTextView1);
        if(isChecked)
        {
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
