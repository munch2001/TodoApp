package com.example.labtest04.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest04.MainActivity
import com.example.labtest04.Model.ToDoModel
import com.example.labtest04.R

class ToDoAdapter(private val activiy: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder?>() {
    private var todoList: List<ToDoModel>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = todoList!![position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)
    }

    override fun getItemCount(): Int {
        return todoList!!.size
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    fun setTasks(todoList: List<ToDoModel>?) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var task: CheckBox

        init {
            task = view.findViewById(R.id.todoCheck)
        }
    }
}
