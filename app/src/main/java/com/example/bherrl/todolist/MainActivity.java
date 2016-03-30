package com.example.bherrl.todolist;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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


import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;


public class MainActivity extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemLongClickListener {


    private static ArrayList<Task> taskList = new ArrayList<Task>();
    private static Task taskToEdit;
    private HelperLibrary hl;
    private AlertDialog.Builder builder;
    private CustomAdapter dataAdapter;

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public Task getTaskToEdit() {
        return taskToEdit;
    }

    public boolean validateTask() {
        return taskToEdit != null;
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
        hl = new HelperLibrary(this);
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

        builder = new AlertDialog.Builder(this);


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
        String myData;

        myData = hl.openFile();
        taskList = hl.parseJson(myData);
        try {
            FileInputStream fis = openFileInput("tasks.json");
            DataInputStream in = new DataInputStream(fis);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null) {
                myData = myData + line;
            }

            taskList = hl.parseJson(myData);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initialize the CustomAdapter and pass the correspondent vars to constructor
        dataAdapter = new CustomAdapter(this, R.layout.task, taskList);
        //ListView_Tasks-> ID of the ListView available in content_main.xml, where Tasks are added
        ListView listView = (ListView) findViewById(R.id.ListView_Tasks);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

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
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task lhs, Task rhs) {
                    return lhs.getPriority() - rhs.getPriority();
                }
            });
            dataAdapter.taskList = taskList;
            dataAdapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.action_Filter_Status) {
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    if (!o1.getDone() && o2.getDone()) {
                        return 1;
                    } else if (o1.getDone() && !o2.getDone()) {
                        return -1;
                    }
                    return 0;
                }
            });
            dataAdapter.taskList = taskList;
            dataAdapter.notifyDataSetChanged();
            return true;

        } else if (id == R.id.action_Filter_Date) {
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    if (o1.getDate() > o2.getDate()) {
                        return 1;
                    } else if (o1.getDate() < o2.getDate()) {
                        return -1;
                    }
                    return 0;
                }
            });

            dataAdapter.taskList = taskList;
            dataAdapter.notifyDataSetChanged();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int taskId = taskList.get(position).getTaskID();

        final Intent detailsIntent = new Intent(this, DetailsActivity.class);
        detailsIntent.putExtra("id", taskId);
        MainActivity.this.startActivity(detailsIntent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final int taskid = (int) id;
        builder.setTitle("Warning")
                .setMessage("Do you really want to delete this task?")
                .setCancelable(false)
                .setIcon(R.mipmap.ic_delete)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        JSONArray ja = hl.removeEntry(taskid, hl.convertTasksToJSONArray(taskList));
                        hl.saveFile(ja);
                        taskList = hl.convertJSONArrayToTasklist(ja);

                        displayTasks();

                        Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog disc = builder.create();
        disc.show();
        return true;
    }
}
