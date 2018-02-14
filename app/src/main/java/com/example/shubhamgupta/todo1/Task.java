package com.example.shubhamgupta.todo1;

/**
 * Created by Shubham Gupta on 03-02-2018.
 */

public class Task {

    int taskId;
    String title;
    String description;
    String remindDate;
    String remindTime;
    String dueDate;
    String image;

    public Task(int taskId,String title, String description, String remindDate, String remindTime, String dueDate, String image) {
        this.taskId=taskId;
        this.title = title;
        this.description = description;
        this.remindDate = remindDate;
        this.remindTime = remindTime;
        this.dueDate = dueDate;
        this.image = image;
    }

    public int  getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

