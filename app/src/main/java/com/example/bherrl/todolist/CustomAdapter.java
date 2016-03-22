package com.example.bherrl.todolist;

import android.widget.ArrayAdapter;
import com.example.bherrl.todolist.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

/**
 * Created by bdomij on 22.03.2016.
 */
public class CustomAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> TaskList;
    Context context;

    public CustomAdapter(Context context, int textViewResourceId,
                           ArrayList<Task> TaskList) {
        super(context, textViewResourceId, TaskList);
        this.TaskList = new ArrayList<Task>();
        this.TaskList.addAll(TaskList);
        this.context = context;
    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            //If-> To know which view has to be redered (Tasks Main)
            convertView = vi.inflate(R.layout.task, null);

            holder = new ViewHolder();

            holder.code = (TextView) convertView.findViewById(R.id.title);
            holder.name = (CheckBox) convertView.findViewById(R.id.description);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Task Task = (Task) cb.getTag();
                    Toast.makeText(context.getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    Task.setDone(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task Task = TaskList.get(position);
        holder.code.setText(" (" +  Task.getTitle() + ")");
        holder.name.setText(Task.getDescription());
        holder.name.setChecked(Task.getDone());
        holder.name.setTag(Task);

        return convertView;

    }

}
