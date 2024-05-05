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

public class EditTask extends AppCompatActivity {

    TaskDB taskDB;
    Task task;

    EditText taskTitleEditText;
    EditText taskDescriptionEditText;
    EditText taskDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        //initialize database
        taskDB = new TaskDB(this);

        //get task from intent
        task = getIntent().getParcelableExtra("task");

        //gets title, desc and date from task
        String taskTitle = task.getTitle();
        String taskDescription = task.getDescription();
        String taskDate = task.getDate();

        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskDueDate = findViewById(R.id.taskDueDateEditText);

        //set current components to details from intent
        taskTitleEditText.setText(taskTitle);
        taskDescriptionEditText.setText(taskDescription);
        taskDueDate.setText(taskDate);
    }

    public void saveChangesButtonClick(View view) {
        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskDueDate = findViewById(R.id.taskDueDateEditText);

        //save changes to variables
        String newTitle = taskTitleEditText.getText().toString();
        String newDescription = taskDescriptionEditText.getText().toString();
        String newDueDate = taskDueDate.getText().toString();

        // Error validation for task title
        if (newTitle.trim().isEmpty()) {
            taskTitleEditText.setError("Task title cannot be empty");
            return;
        }

        // Error validation for task description
        if (newDescription.trim().isEmpty()) {
            taskDescriptionEditText.setError("Task description cannot be empty");
            return;
        }

        // Error validation for due date
        if (!isValidDate(newDueDate)) {
            // Display error message or handle invalid date input
            taskDueDate.setError("Invalid date format or date is not valid. Use format yyyy-MM-dd");
            return;
        }

        //update task with new changes locally
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setDate(newDueDate);

        //remove task from DB
        taskDB.deleteTask(task.getId());
        //replace with new task
        taskDB.addTask(task);

        //go back to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //goes back to main activity without changing task
    public void returnButtonClick(View view) {
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
}