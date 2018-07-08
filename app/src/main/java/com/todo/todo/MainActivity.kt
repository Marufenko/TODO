package com.todo.todo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val MY_PREFS_NAME = "MyPrefsFile"
    private val MY_PREFS_KEY = "gaols"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveString(view: View) {
        val tv = view.rootView.findViewById<EditText>(R.id.editText)
        val goal = tv.text.toString()
        val text = GoalDataModel.addFirstGoal(goal)

        getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit().apply {
            putString(MY_PREFS_KEY, text)
            commit()
        }
    }

    fun retrieveString(view: View) {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val goals = prefs.getString(MY_PREFS_KEY, "no data")

        val tv = view.rootView.findViewById<TextView>(R.id.textView)
        tv.text = GoalDataModel.retrieveFirstGoal(goals)
    }
}