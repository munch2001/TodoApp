package com.example.labtest04

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest04.Adapter.ToDoAdapter
import com.example.labtest04.Model.ToDoModel
import com.example.labtest04.Utils.DatabaseHandler
import java.util.Collections


class MainActivity : AppCompatActivity(), DialogCloseListener {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter

    //    private lateinit var taskList: List<ToDoModel>
    var taskList: MutableList<ToDoModel> = mutableListOf()

    private lateinit var db: DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db.openDatabase()


//        taskList = new ArrayList<>()

        tasksRecyclerView = findViewById(R.id.taskRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(this)
        tasksRecyclerView.adapter = tasksAdapter

        taskList = db.allTasks.toMutableList()
        Collections.reverse(taskList)
        tasksAdapter.setTasks(taskList)

        //dummy data
//        val task = ToDoModel().apply {
//            task = "This is a Test Task"
//            status = 0
//            id = 1
//        }

//        // Add the task to the list multiple times
//        repeat(5) {
//            taskList.add(task)
//        }

//        tasksAdapter.setTasks(taskList)

    }
    //implementing handleDialogClose
    override fun handleDialogClose(dialog: DialogInterface?) {
        taskList = db.allTasks.toMutableList()
        Collections.reverse(taskList)
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}