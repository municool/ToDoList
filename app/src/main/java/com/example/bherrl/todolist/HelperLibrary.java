package com.example.bherrl.todolist;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bherrl on 29.03.2016.
 */
public class HelperLibrary {

    private Context context;

    public HelperLibrary(Context context){
        this.context = context;
    }

    //Create Json Array with a Arraylist type Task
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

    // Creates ArrrayList type Task out of String delivered to it containing all the data
    public ArrayList<Task> parseJson(String data) {

        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        JSONArray jsObj;
        try {
            jsObj = new JSONArray(data);
            for (int i = 0; i < jsObj.length(); i++) {
                JSONObject jo = jsObj.getJSONObject(i);

                int id = jo.getInt("taskID");
                String title = jo.getString("title");
                String desc = jo.getString("description");
                int prio = jo.getInt("priority");
                long date = jo.getLong("date");
                boolean notif = jo.getBoolean("notification");
                boolean done = jo.getBoolean("done");

                Task t = new Task(id, title, desc, done, prio, notif, date);
                taskArrayList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskArrayList;
    }


    // Gets an element out of ArrayList by delivering it both ID and the List
    public Task getTaskWithId(int id, ArrayList<Task> taskArrayList) {
        for (Task t : taskArrayList) {
            if (t.getTaskID() == id) return t;
        }
        return null;
    }

    // Convert MilliSeconds to a real Date with a given format
    public String convertToDate(long miliS){

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliS);
        return formatter.format(calendar.getTime());
    }

    // Creates an ArrayList out of a Json Object
    public ArrayList<Task> convertJSONArrayToTasklist (JSONArray jsObj){

        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        try {
            for (int i = 0; i < jsObj.length(); i++) {
                JSONObject jo = jsObj.getJSONObject(i);

                int id = jo.getInt("taskID");
                String title = jo.getString("title");
                String desc = jo.getString("description");
                int prio = jo.getInt("priority");
                long date = jo.getLong("date");
                boolean notif = jo.getBoolean("notification");
                boolean done = jo.getBoolean("done");

                Task t = new Task(id, title, desc, done, prio, notif, date);
                taskArrayList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskArrayList;

    }

    public static JSONArray removeEntry(final int idx, final JSONArray from) {
        final List<JSONObject> objs = asList(from);
        objs.remove(idx);

        final JSONArray ja = new JSONArray();
        for (final JSONObject obj : objs) {
            ja.put(obj);
        }

        return ja;
    }

    public static List<JSONObject> asList(final JSONArray ja) {
        final int len = ja.length();
        final ArrayList<JSONObject> result = new ArrayList<JSONObject>(len);
        for (int i = 0; i < len; i++) {
            final JSONObject obj = ja.optJSONObject(i);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

    public void saveFile(JSONArray tasks) {
        FileOutputStream outputStream;
        String fileName = "tasks.json";


        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            String jsonString = tasks.toString();
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String openFile() {
        String myData = "";
        try {
            FileInputStream fis = context.openFileInput("tasks.json");
            DataInputStream in = new DataInputStream(fis);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null){
                myData = myData + line;
            }

            in.close();
            return myData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
