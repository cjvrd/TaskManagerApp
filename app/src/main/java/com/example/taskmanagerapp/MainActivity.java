package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TaskDB taskDB;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize database
        taskDB = new TaskDB(this);

        //get tasks from DB and set to taskList
        taskList = taskDB.getAllTasks();

        //sort tasks bye date before displaying
        sortTasksByDueDate(taskList);

        //initialize recycler view and set adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(taskList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void goToAddTaskPageButtonClick(MenuItem item) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    //sorts tasks by due date
    public static void sortTasksByDueDate(List<Task> tasks) {
        // Define a custom comparator to compare tasks based on their due dates
        Comparator<Task> dueDateComparator = new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                return task1.getDate().compareTo(task2.getDate());
            }
        };
        // Sort the tasks list using the custom comparator
        tasks.sort(dueDateComparator);
    }


}
