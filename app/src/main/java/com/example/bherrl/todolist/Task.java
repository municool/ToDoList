package com.example.bherrl.todolist;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by bdomij on 22.03.2016.
 */
public class Task {
    int taskID ;
    String title = "";
    String description = "";
    //0 = Low; 1 = Medium; 2 = High;
    int priority = 0;
    //"yyyy.MM.dd G -> Format DateTime
    long date;
    boolean done = false;
    boolean notification = false;

    //Konstruktor
    public Task(int id, String title, String desc, boolean d, int prio, boolean notif, long date){
        this.taskID = id;
        this.description = desc;
        this.title = title;
        this.done = d;
        this.date = date;
        this.priority = prio;
        this.notification = notif;
    }

    //Setter
    public void setTaskID(String filename, Context context){



    }

    public void  setTitle(String title){
        this.title = title;
    }

    public void setDone(boolean d){
        this.done = d;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int prio){
        this.priority = prio;
    }

    public void setDate(long date){
        this.date = date;
    }

    public  void setNotification(boolean notif){
        this.notification = notif;
    }

    //Getter
    public int getTaskID(){  return this.taskID;  }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public int getPriority(){
        return this.priority;
    }

    public long getDate(){
        return this.date;
    }

    public boolean getNotification(){
       return this.notification;
    }

    //Checkbox selected? ... -> If returns false(no) if return true(yes)
    public boolean getDone(){
        return this.done;
    }




}
