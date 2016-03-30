package com.example.bherrl.todolist;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;


import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private static long dateMillis;
    private HelperLibrary hl;

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void createTask(View v) {

        Task ts;
        int id;
        MainActivity ma = new MainActivity();
        ArrayList<Task> t = ma.getTaskList();
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

        Task tsk = new Task(id, etTitle.getText().toString(), etDescription.getText().toString(), false, idx, sw.isChecked(), dateMillis);

        t.add(tsk);
        ma.setTaskList(t);

        hl.saveFile(hl.convertTasksToJSONArray(ma.getTaskList()));

        finish();

    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }
}
