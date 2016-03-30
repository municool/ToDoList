package com.example.bherrl.todolist;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private static long dateMillis;
    private HelperLibrary hl;
    private int editingTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        hl = new HelperLibrary(this);

        ImageButton cancel = (ImageButton) findViewById(R.id.btnCancel);
        if (cancel != null) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Call this Method in order to go back to the main View
                    finish();
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        editingTaskId = getIntent().getIntExtra("id", -1);
        if(editingTaskId!=-1) {
            HelperLibrary hl = new HelperLibrary(this);
            String myData = hl.openFile();
            ArrayList<Task> taskList = hl.parseJson(myData);
            Task task = hl.getTaskWithId(editingTaskId, taskList);

            TextView title = (TextView) findViewById(R.id.etTitle);
            TextView descr = (TextView) findViewById(R.id.etDescription);
            RadioGroup prio = (RadioGroup) findViewById(R.id.rgPriority);
            RadioButton rb = (RadioButton) prio.getChildAt(task.priority);
            TextView date = (TextView)findViewById(R.id.tvChooseDate);
            Switch sw = (Switch) findViewById(R.id.swNotification);

            title.setText(task.title);
            descr.setText(task.description);
            rb.setChecked(true);
            date.setText(hl.convertToDate(task.getDate()));
            sw.setChecked(task.getNotification());
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void createTask(View v) {
        Task ts;
        int id;

        String myData = hl.openFile();
        ArrayList<Task> t = hl.parseJson(myData);

        EditText etTitle = (EditText) findViewById(R.id.etTitle);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        RadioGroup rgPriority = (RadioGroup) findViewById(R.id.rgPriority);
        Switch sw = (Switch) findViewById(R.id.swNotification);

        int radioButtonID = rgPriority.getCheckedRadioButtonId();
        View radioButton = rgPriority.findViewById(radioButtonID);
        int idx = rgPriority.indexOfChild(radioButton);

        if (t.size() != 0) {
            ts = t.get(t.size() - 1);
            id = ts.getTaskID() + 1;
        } else {
            id = 1;
        }

        //Neuer Task oder bestehender Task bearbeiten
        if(editingTaskId==-1) {
            Task tsk = new Task(id, etTitle.getText().toString(), etDescription.getText().toString(), false, idx, sw.isChecked(), dateMillis);
            t.add(tsk);
        }
        else {
            for(Task task: t) {
                if(task.getTaskID()==editingTaskId) {
                    task.setTitle(etTitle.getText().toString());
                    task.setDescription(etDescription.getText().toString());
                    task.setDate(dateMillis);
                    task.setPriority(idx);
                    task.setNotification(sw.isChecked());
                }
            }
        }

        hl.saveFile(hl.convertTasksToJSONArray(t));

        Toast.makeText(getApplicationContext(), "Task created", Toast.LENGTH_SHORT).show();

        finish();

    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }
}
