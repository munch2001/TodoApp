package com.example.labtest04

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest04.Adapter.ToDoAdapter
import com.example.labtest04.Model.ToDoModel
import com.example.labtest04.Utils.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections


class MainActivity : AppCompatActivity(), DialogCloseListener {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private var fab: FloatingActionButton? = null

    var taskList: MutableList<ToDoModel> = mutableListOf()

    private lateinit var db: DatabaseHandler


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        supportActionBar?.hide()
//
//        db = DatabaseHandler(this)
//        db.openDatabase()
//
//        tasksRecyclerView = findViewById(R.id.taskRecyclerView)
//        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
//        tasksAdapter = ToDoAdapter(db,this)
//        tasksRecyclerView.adapter = tasksAdapter
//
//        // add new task button
//        fab = findViewById(R.id.fab);
//
//        taskList = db.allTasks.toMutableList()
//        Collections.reverse(taskList)
//        tasksAdapter.setTasks(taskList)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db.openDatabase()

        tasksRecyclerView = findViewById(R.id.taskRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)

        // Pass db and this as parameters to ToDoAdapter constructor
        tasksAdapter = ToDoAdapter(db, this)
        tasksRecyclerView.adapter = tasksAdapter

        fab = findViewById(R.id.fab)

        // Set click listener for fab to open a dialog for adding a new task
        fab?.setOnClickListener {
            val addTaskDialog = AddNewTask.newInstance()
            addTaskDialog.show(supportFragmentManager, AddNewTask.TAG)
        }

        taskList = db.allTasks.toMutableList()
        Collections.reverse(taskList)
        tasksAdapter.setTasks(taskList)
    }


    //implementing handleDialogClose
    override fun handleDialogClose(dialog: DialogInterface?) {
        taskList = db.allTasks.toMutableList()
        Collections.reverse(taskList)
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}