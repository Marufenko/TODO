package com.todo.todo.dailog

import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.todo.todo.FeedReaderContract
import com.todo.todo.FeedReaderDbHelper
import com.todo.todo.MyApplication
import com.todo.todo.R
import android.widget.Toast
import android.content.DialogInterface



class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_add_goal, null)
        val goalInput = view.findViewById<EditText>(R.id.goalInput)
        val goal = arguments!!.getString(KEY_NAME)
        val title = arguments!!.getString(KEY_DIALOG_TITLE)
        goalInput.setText(goal)
        goalInput.setSelection(goal.length)

        goalInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                notifyListener(goalInput)
                return@setOnEditorActionListener true
            }
            false
        }

        return AlertDialog.Builder(activity!!)
                .setTitle(title)
                .setPositiveButton("OK") { _, _ ->

                    // add record to database
                    val key =  goalInput.text.toString()
                    val value = "0" // "0" by default as boolean value which represents not ticked goal

                    // add item to database
                    val context = MyApplication.appContext

                    // Access database
                    val dbHelper = FeedReaderDbHelper(context)
                    // Gets the data repository in write mode
                    val db = dbHelper.writableDatabase
                    // Create a new map of values, where column names are the keys
                    val values = ContentValues().apply {
                        put(FeedReaderContract.FeedEntry.COLUMN_KEY, key)
                        put(FeedReaderContract.FeedEntry.COLUMN_VALUE, value)
                    }
                    // Insert new entry to db
                    db?.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)

                }
                .setNegativeButton("Cancel", null)
                .setView(view)
                .create()
    }

    private fun notifyListener(goalInput: EditText) {
        (activity as SingleActionDialogFragment.Listener).onPositiveDialogButtonClicked(
                dialogTag = tag!!,
                extras = Bundle().apply {
                    putString(KEY_NAME, goalInput.text.toString())
                }
        )
    }

    companion object {
        const val KEY_NAME = "key_name"
        private const val KEY_DIALOG_TITLE = "key_dialog_title"

        fun show(supportFragmentManager: FragmentManager, tag: String, dialogTitle: String, name: String = "") {
            val fragment = MyDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(KEY_NAME, name)
                putString(KEY_DIALOG_TITLE, dialogTitle)
            }
            fragment.showAllowingStateLoss(supportFragmentManager, tag)
        }
    }
}