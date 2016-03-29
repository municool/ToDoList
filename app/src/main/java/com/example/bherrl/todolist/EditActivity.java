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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;

public class EditActivity extends AppCompatActivity {

    private static long dateMillis;

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
        JSONObject jo = new JSONObject();
        EditText etTitle = (EditText) findViewById(R.id.etTitle);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        RadioGroup rgPriority = (RadioGroup) findViewById(R.id.rgPriority);
        Switch sw = (Switch) findViewById(R.id.swNotification);

        int radioButtonID = rgPriority.getCheckedRadioButtonId();
        View radioButton = rgPriority.findViewById(radioButtonID);
        int idx = rgPriority.indexOfChild(radioButton);

//        Log.v("Index", Integer.toString(idx));
//        Log.v("switch", Boolean.toString(sw.isChecked()));

        try {
            jo.put("title", etTitle.getText().toString());
            jo.put("description", etDescription.getText().toString());
            jo.put("priority", idx);
            jo.put("date", dateMillis);
            jo.put("notification", sw.isChecked());

        } catch (JSONException je) {
            je.printStackTrace();
        }
        createFile("tasks.json", jo.toString());

    }


    private void createFile(String fileName, String fileContent) {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileContent.getBytes());
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
