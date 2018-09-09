package com.todo.todo.dailog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.todo.todo.R

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
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .setView(view)
                .create()
    }

    private fun notifyListener(goalInput: EditText) {
        (activity as SingleActionDialogFragment.Listener).onPositiveDialogButtonClicked(
                dialogTag = tag!!,
                extras = Bundle().apply {
                    putString(KEY_NAME, goalInput.text.toString())

                    //looks like adding record to db should be implemented here
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