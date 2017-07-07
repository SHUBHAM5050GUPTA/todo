package com.example.shubhamgupta.todo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 03-07-2017.
 */


public class ToDoAdapter extends ArrayAdapter {
    Context context;
    OncheckboxClickListner listner;
    ArrayList<ToDoContent> todoList;
   // MainActivity mainActivity;
    public ToDoAdapter(Context context, ArrayList<ToDoContent> todoList) {
        super(context,0,todoList);
        this.context=context;
        this.todoList=todoList;
      //  mainActivity=(MainActivity) context;
    }
    void setOnCheckboxClickListner(OncheckboxClickListner listner)
    {
        this.listner=listner;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        final View v= LayoutInflater.from(context).inflate(R.layout.list_items,null);
        TextView name= (TextView) v.findViewById(R.id.listItemTextView1);
        TextView reminddate= (TextView) v.findViewById(R.id.listItemTextView2);
        TextView remindtime= (TextView) v.findViewById(R.id.listItemTextView3);
        TextView duedate= (TextView) v.findViewById(R.id.listItemTextView4);

        ToDoContent toDoContent=  todoList.get(position);

        name.setText(toDoContent.TodoName);
        reminddate.setText(toDoContent.Reminddate);
        remindtime.setText(toDoContent.Remindtime);
        duedate.setText(toDoContent.Duedate);
        final int pos=position;
        CheckBox checkBox= (CheckBox) v.findViewById(R.id.listItemCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               {
               /*  ToDoDatabase toDoDatabase=new ToDoDatabase(mainActivity);
                    SQLiteDatabase database=toDoDatabase.getWritableDatabase();

                    database.delete(ToDoDatabase.TODO_TABLE,ToDoDatabase.TODO_ID+"="+todoList.get(pos).TodoId,null);

                    mainActivity.updateToDoList();*/

                   listner.OncheckboxClick(v,pos,isChecked);



                }
            }
        });


        return v;
    }
    interface OncheckboxClickListner
    {
        void  OncheckboxClick(View v,int position,boolean isChecked);
    }
}
