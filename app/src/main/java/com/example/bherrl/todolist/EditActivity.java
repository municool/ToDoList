package com.example.bherrl.todolist;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private static long dateMillis;
    private HelperLibrary hl = new HelperLibrary();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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

        MainActivity ma = new MainActivity();
        EditText etTitle = (EditText) findViewById(R.id.etTitle);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        RadioGroup rgPriority = (RadioGroup) findViewById(R.id.rgPriority);
        Switch sw = (Switch) findViewById(R.id.swNotification);

        int radioButtonID = rgPriority.getCheckedRadioButtonId();
        View radioButton = rgPriority.findViewById(radioButtonID);
        int idx = rgPriority.indexOfChild(radioButton);

//        Log.v("Index", Integer.toString(idx));
//        Log.v("switch", Boolean.toString(sw.isChecked()));

        Task tsk = new Task(etTitle.getText().toString(), etDescription.getText().toString(), false, idx, sw.isChecked(), dateMillis);

        ArrayList<Task> t = ma.getTaskList();
        t.add(tsk);
        ma.setTaskList(t);
        hl.saveTasks(ma.getTaskList());

        finish();

    }


    private void saveFile(JSONArray tasks) {
        FileOutputStream outputStream;
        String fileName = "tasks.json";

        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(tasks.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }
}
