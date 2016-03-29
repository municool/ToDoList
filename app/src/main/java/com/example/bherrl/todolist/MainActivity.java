package com.example.bherrl.todolist;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    private void parseJson(String data){

        JSONArray jsObj = null;
        try {
            jsObj = new JSONArray(data);
            for(int i=0; i < jsObj.length(); i++) {
                JSONObject jo = jsObj.getJSONObject(i);

                String title = jo.getString("title");
                String desc = jo.getString("description");
                int prio = jo.getInt("priority");
                long date = jo.getLong("date");
                boolean notif = jo.getBoolean("notification");
                boolean done = jo.getBoolean("done");

//    Konsttruktor:
//    public Task(String title, String desc, int prio, long date, boolean d, boolean notif){

                Task t = new Task(title, desc, prio, date, done, notif);

                //add to list
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Start showing the tasks
    private void displayTasks() {
        //ArrayList with elements maybe from a database
        ArrayList<Task> TaskList = new ArrayList<Task>();

        String myData = "";
        // Baustelle
        try {
            FileInputStream fis = new FileInputStream("tasks.json");
            DataInputStream in = new DataInputStream(fis);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null){
                myData = myData + line;
                parseJson(myData);
            }



            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Initialize the CustomAdapter and pass the correspondent vars to constructor
        CustomAdapter dataAdapter = new CustomAdapter(this,R.layout.task, TaskList);
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
}
