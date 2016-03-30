package com.example.bherrl.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    private static int viewingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int taskId = getIntent().getIntExtra("id", 0);
        HelperLibrary hl = new HelperLibrary(this);
        String myData = hl.openFile();
        ArrayList<Task> taskList = hl.parseJson(myData);
        Task task = hl.getTaskWithId(taskId, taskList);

        final Calendar c = Calendar.getInstance();

        TextView title = (TextView)findViewById(R.id.tvTitle);
        TextView descr = (TextView)findViewById(R.id.tvDescription);
        RadioGroup prio = (RadioGroup) findViewById(R.id.rgPriority);
        RadioButton rb = (RadioButton)prio.getChildAt(task.priority);
        TextView date = (TextView)findViewById(R.id.tvChooseDate);
        TextView sw = (TextView) findViewById(R.id.tvNotifications);

        title.setText(task.title);
        descr.setText(task.description);


        date.setText(hl.convertToDate(task.getDate()));
        rb.setChecked(true);
        //Date
        sw.setText((task.notification == false) ? "Notifications: None" : "Notifications: Active");

        viewingTask = taskId;
    }


    public void editTask(View v){
        final Intent editIntent = new Intent(this, EditActivity.class);
        editIntent.putExtra("id", viewingTask);
        DetailsActivity.this.startActivity(editIntent);
    }

}
