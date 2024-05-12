package com.example.labtest04

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.labtest04.Model.ToDoModel
import com.example.labtest04.Utils.DatabaseHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewTask : BottomSheetDialogFragment() {
    private var newTaskText: EditText? = null
    private var newTaskSaveButton: Button? = null
    private var db: DatabaseHandler? = null

    //set dialog style
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    //Create the view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_task, container, false)
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return view
    }

    //handling text changes and save the task
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newTaskText = requireView().findViewById(R.id.newTaskText)
        newTaskSaveButton = requireView().findViewById(R.id.newTaskBtn)
        db = DatabaseHandler(requireActivity())
        db!!.openDatabase()
        var isUpdate = false
        val bundle = arguments
        if (bundle != null) {
            isUpdate = true
            val task = bundle.getString("task")
            newTaskText?.setText(task)
            if (!task.isNullOrEmpty()) {
                newTaskSaveButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDarkest))
            }
        }
        newTaskText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    newTaskSaveButton?.isEnabled = false
                    newTaskSaveButton?.setTextColor(Color.GRAY)
                } else {
                    newTaskSaveButton?.isEnabled = true
                    newTaskSaveButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDarkest))
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val finalIsUpdate = isUpdate
        newTaskSaveButton?.setOnClickListener {
            val text = newTaskText?.text.toString()
            if (finalIsUpdate) {
                bundle?.getInt("id")?.let { id ->
                    db?.updateTask(id, text)
                }
            } else {
                val task = ToDoModel().apply {
                    task = text
                    status = 0
                }
                db?.insertTask(task)
            }
            dismiss()
        }
    }

    //handle dialog dismiss
    override fun onDismiss(dialog: DialogInterface) {
        val activity: Activity? = activity
        if (activity is DialogCloseListener) {
            (activity as DialogCloseListener).handleDialogClose(dialog)
        }
    }

    companion object {
        const val TAG = "ActionBottomDialog"

        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }
}
