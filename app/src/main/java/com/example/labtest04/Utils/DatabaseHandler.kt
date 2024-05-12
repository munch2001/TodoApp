package com.example.labtest04.Utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.labtest04.Model.ToDoModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, NAME, null, VERSION) {
    private var db: SQLiteDatabase? = null

    //Create the database table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TODO_TABLE)
    }

    //function to update the database when the version changes
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Drop the older tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE)
        //Create tables again
        onCreate(db)
    }

    fun openDatabase() {
        db = this.writableDatabase
    }

    //function to Insert a new Task into the database
    fun insertTask(task: ToDoModel) {
        val cv = ContentValues()
        cv.put(TASK, task.task)
        cv.put(STATUS, 0)
        db!!.insert(TODO_TABLE, null, cv)
    }

    //Get all tasks from the database
    @get:SuppressLint("Range")
    val allTasks: List<ToDoModel>
        get() {
            val taskList: MutableList<ToDoModel> = ArrayList()
            var cur: Cursor? = null
            db!!.beginTransaction()
            try {
                cur = db!!.query(TODO_TABLE, null, null, null, null, null, null, null)
                if (cur != null) {
                    if (cur.moveToFirst()) {
                        do {
                            val task = ToDoModel()
                            task.id = cur.getInt(cur.getColumnIndex(ID))
                            task.task = cur.getString(cur.getColumnIndex(TASK))
                            task.status = cur.getInt(cur.getColumnIndex(STATUS))
                            taskList.add(task)
                        } while (cur.moveToNext())
                    }
                }
            } finally {
                db!!.endTransaction()
                cur!!.close()
            }
            return taskList
        }

    //function update the status of a task
    fun updateStatus(id: Int, status: Int) {
        val cv = ContentValues()
        cv.put(STATUS, status)
        db!!.update(TODO_TABLE, cv, ID + "=?", arrayOf(id.toString()))
    }

    //function update the task text
    fun updateTask(id: Int, task: String?) {
        val cv = ContentValues()
        cv.put(TASK, task)
        db!!.update(TODO_TABLE, cv, ID + "=?", arrayOf(id.toString()))
    }

    //function to delete task
    fun deleteTask(id: Int) {
        db!!.delete(TODO_TABLE, ID + "=?", arrayOf(id.toString()))
    }

    //Database constants and query to create Table in the database
    companion object {
        private const val VERSION = 1
        private const val NAME = "todoListDatabase"
        private const val TODO_TABLE = "todo"
        private const val ID = "id"
        private const val TASK = "task"
        private const val STATUS = "status"
        private const val CREATE_TODO_TABLE =
            ("CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
                    + STATUS + " INTEGER)")
    }
}
