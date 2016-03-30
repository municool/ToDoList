package com.example.bherrl.todolist;

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

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        int taskId = getIntent().getIntExtra("id", 0);
        HelperLibrary hl = new HelperLibrary(this);
        String myData = hl.openFile();
        ArrayList<Task> taskList = hl.parseJson(myData);
        Task task = hl.getTaskWithId(taskId, taskList);

        TextView title = (TextView)findViewById(R.id.tvTitle);
        TextView descr = (TextView)findViewById(R.id.tvDescription);
        RadioGroup prio = (RadioGroup) findViewById(R.id.rgPriority);
        RadioButton rb = (RadioButton)prio.getChildAt(task.priority);
        //Date
        TextView sw = (TextView) findViewById(R.id.tvNotifications);

        title.setText(task.title);
        descr.setText(task.description);
        rb.setChecked(true);
        //Date
        sw.setText((task.notification == false) ? "Notifications: None" : "Notifications: Active");
    }

    public void setTaskDetails(){

    }

}
