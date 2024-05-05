package com.example.taskmanagerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Task> taskList;
    Context context;

    public RecyclerViewAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.task_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitleTextView.setText(task.getTitle());
        holder.dueDateTextView.setText(task.getDate());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitleTextView;
        TextView dueDateTextView;
        Button completeTaskButton;
        Button viewEditTaskButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            completeTaskButton = itemView.findViewById(R.id.completeTaskButton);
            viewEditTaskButton = itemView.findViewById(R.id.viewEditTaskButton);

            completeTaskButton.setOnClickListener(v -> completeTask());
            viewEditTaskButton.setOnClickListener(v -> goToEditTaskPage());
        }

        private void completeTask() {
            int position = getAdapterPosition();
            Task task = taskList.get(position);
            TaskDB taskDB = new TaskDB(context);
            taskDB.deleteTask(task.getId());
            taskList.remove(position);
            notifyItemRemoved(position);
        }

        private void goToEditTaskPage() {
            int position = getAdapterPosition();
            Task task = taskList.get(position);

            Intent intent = new Intent(context, EditTask.class);
            intent.putExtra("task", task);
            context.startActivity(intent);
        }
    }
}