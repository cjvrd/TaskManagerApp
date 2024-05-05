package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTask extends AppCompatActivity {

    TaskDB taskDB;
    List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        taskDB = new TaskDB(this);
    }

    public void addTaskButtonClick(View view) {
        EditText taskTitleEditText = findViewById(R.id.taskTitleEditText);
        EditText taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        EditText taskDueDate = findViewById(R.id.taskDueDateEditText);

        String title = taskTitleEditText.getText().toString();
        String description = taskDescriptionEditText.getText().toString();
        String dueDate = taskDueDate.getText().toString();

        // Error validation for task title
        if (title.trim().isEmpty()) {
            taskTitleEditText.setError("Task title cannot be empty");
            return;
        }

        // Error validation for task description
        if (description.trim().isEmpty()) {
            taskDescriptionEditText.setError("Task description cannot be empty");
            return;
        }

        // Error validation for due date
        if (!isValidDate(dueDate)) {
            // Display error message or handle invalid date input
            taskDueDate.setError("Invalid date format or date is not valid. Use format yyyy-MM-dd");
            return;
        }

        //adds new task to the DB
        Task task = new Task(setTaskId(), title, description, dueDate);
        taskDB.addTask(task);

        //goes back to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //checks if a date string is valid
    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false); // Make sure the date parsing is strict
        try {
            Date date = sdf.parse(dateStr);
            return date != null;
        } catch (ParseException e) {
            return false;
        }
    }
    //function to set the id of each task to be used in other functions
    public String setTaskId() {
        taskList = taskDB.getAllTasks();

        //if its empty, then set first id to 1
        if (taskList.isEmpty()) {
            return "1";
        }

        //find id of last task in the list
        int lastTask = Integer.parseInt(taskList.get(taskList.size() - 1).getId());
        //add 1
        int taskId = lastTask + 1;
        return String.valueOf(taskId);
    }
}