package com.example.bherrl.todolist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bherrl on 29.03.2016.
 */
public class HelperLibrary {


    public JSONArray convertTasksToJSONArray(ArrayList<Task> tasks) {
        JSONArray ja = new JSONArray();

        for (Task task : tasks) {
            JSONObject jo = new JSONObject();

            try {
                jo.put("taskID", task.getTaskID());
                jo.put("title", task.getTitle());
                jo.put("description", task.getDescription());
                jo.put("priority", task.getPriority());
                jo.put("date", task.getDate());
                jo.put("notification", task.getNotification());
                jo.put("done", task.getDone());

                ja.put(jo);

            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return ja;
    }

    public ArrayList<Task> parseJson(String data){

        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        JSONArray jsObj;
        try {
            jsObj = new JSONArray(data);
            for(int i=0; i < jsObj.length(); i++) {
                JSONObject jo = jsObj.getJSONObject(i);

                int id = jo.getInt("taskID");
                String title = jo.getString("title");
                String desc = jo.getString("description");
                int prio = jo.getInt("priority");
                long date = jo.getLong("date");
                boolean notif = jo.getBoolean("notification");
                boolean done = jo.getBoolean("done");

//    Konsttruktor:
//    public Task(int id, String title, String desc, boolean d, int prio, boolean notif, long date){
                Task t = new Task(id,title, desc,done, prio, notif, date);
                taskArrayList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskArrayList;
    }

}
