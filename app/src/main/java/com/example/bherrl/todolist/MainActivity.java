package com.example.bherrl.todolist;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;


import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements OnItemClickListener{


    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private HelperLibrary hl;

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        MainActivity.taskList = taskList;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hl = new HelperLibrary();


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        final Intent editIntent = new Intent(this, EditActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(editIntent);
            }
        });


        // Alarm Manager is created using ALARM_SERVICE.
        //Params_> PendingIntent and Time
        //The Intent is launched  after the time is over
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Notification Intent is created with an Action
        Intent notifIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notifIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);


        // Method to Display Tasks
        displayTasks();
    }

    //Start showing the tasks
    private void displayTasks() {
        String myData = "";
        try {
            FileInputStream fis = openFileInput("tasks.json");
            DataInputStream in = new DataInputStream(fis);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null){
                myData = myData + line;
            }

            taskList = hl.parseJson(myData);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Task t = new Task(1,"Title", "Low", false, 0, false, 1);
//        Task tt = new Task(1,"Title", "Medium", false, 1, false, 1);
//        Task ts = new Task(1,"Title", "High", false, 2, false, 1);
//        taskList.add(t);taskList.add(tt);taskList.add(ts);
        //Initialize the CustomAdapter and pass the correspondent vars to constructor
        CustomAdapter dataAdapter = new CustomAdapter(this, R.layout.task, taskList);
        //ListView_Tasks-> ID of the ListView available in content_main.xml, where Tasks are added
        ListView listView = (ListView) findViewById(R.id.ListView_Tasks);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        //Click Event Listener
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Task task = (Task) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + task.getTitle(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Filter_Prio) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
