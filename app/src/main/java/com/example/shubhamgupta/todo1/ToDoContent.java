package com.example.shubhamgupta.todo1;

/**
 * Created by Shubham Gupta on 03-07-2017.
 */

public class ToDoContent {
    int TodoId;
    String TodoName;
    String Reminddate;
    String Duedate;
    String Remindtime;

    public ToDoContent(int todoId, String todoName, String reminddate,String remindtime,String duedate) {
        TodoId = todoId;
        TodoName = todoName;
        Reminddate = reminddate;
        Remindtime=remindtime;
        Duedate = duedate;
    }
}
