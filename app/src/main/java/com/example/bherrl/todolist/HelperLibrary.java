package com.example.bherrl.todolist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bherrl on 29.03.2016.
 */
public class HelperLibrary {


    public void saveTasks(){
        JSONArray ja = new JSONArray();
        ArrayList<Task> tasks = getTasks();

        for(Task task : tasks){
            JSONObject jo = new JSONObject();

            try {
                jo.put("title", task.getTitle());
                jo.put("description", task.getDescription());
                jo.put("priority", task.getPriority());
                jo.put("date", task.getDate());
                jo.put("notification", task.getNotification());

                ja.put(jo);

            } catch (JSONException je) {
                je.printStackTrace();
            }

        }
    }

}
