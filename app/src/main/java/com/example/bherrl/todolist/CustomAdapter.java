package com.example.bherrl.todolist;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.renderscript.RenderScript;
import android.support.v4.content.ContextCompat;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
        TextView title;
        TextView description;
        CheckBox checkBox;

        Shape form;
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

            //Create a new ViewHolder Obj. In the ViewHolder Object you define all the things you need for the actual task to show in the List
            holder = new ViewHolder();


            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox_Task);


            convertView.setTag(holder);


//            holder.checkBox.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Log.v("todolist", "checkbox clicked");
//                    CheckBox cb = (CheckBox) v;
//                    Task Task = (Task) cb.getTag();
//                    Toast.makeText(context.getApplicationContext(),
//                            "Clicked on Checkbox: " + cb.getText() +
//                                    " is " + cb.isChecked(),
//                            Toast.LENGTH_LONG).show();
//                    Task.setDone(cb.isChecked());
//                }
//            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task Task = TaskList.get(position);
        holder.title.setText(Task.getTitle());
        holder.description.setText(Task.getDescription());
        holder.checkBox.setChecked(Task.getDone());

        RelativeLayout r = (RelativeLayout)convertView.findViewById(R.id.relativeLayout_Task);
        GradientDrawable bgShape = (GradientDrawable)r.getBackground();
        if(Task.getPriority() == 2){
            bgShape.setColor(ContextCompat.getColor(context, R.color.defaultLow));
        }else if(Task.getPriority() == 1){
            bgShape.setColor(ContextCompat.getColor(context, R.color.medium));
        }else if(Task.getPriority() == 0){
            bgShape.setColor(ContextCompat.getColor(context, R.color.high));
        }

        holder.description.setTag(Task);

        return convertView;

    }



}
