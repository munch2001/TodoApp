package com.example.labtest04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest04.Adapter.ToDoAdapter
import com.example.labtest04.Model.ToDoModel

class MainActivity : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter

//    private lateinit var taskList: List<ToDoModel>
var taskList: MutableList<ToDoModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

//        taskList = new ArrayList<>()

        tasksRecyclerView = findViewById(R.id.taskRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(this)
        tasksRecyclerView.adapter = tasksAdapter

        val task = ToDoModel().apply {
            task = "This is a Test Task"
            status = 0
            id = 1
        }

        // Add the task to the list multiple times
        repeat(5) {
            taskList.add(task)
        }

        tasksAdapter.setTasks(taskList)


    }
}