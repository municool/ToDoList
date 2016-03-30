package com.example.bherrl.todolist;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by bdomij on 22.03.2016.
 */
public class CustomAdapter extends ArrayAdapter<Task> {

    public ArrayList<Task> taskList;
    Context context;

    public CustomAdapter(Context context, int textViewResourceId,
                         ArrayList<Task> TaskList) {
        super(context, textViewResourceId, TaskList);
        this.taskList = new ArrayList<Task>();
        this.taskList.addAll(TaskList);
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

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task Task = taskList.get(position);
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
