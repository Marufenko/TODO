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

    val MY_PREFS_NAME = "MyPrefsFile"
    val MY_PREFS_KEY = "key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    @SuppressLint("CommitPrefEdits")
    fun saveString(view: View) {
        val tv = view.rootView.findViewById<EditText>(R.id.editText)
        val text = tv.text.toString()

        getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit().apply {
            putString(MY_PREFS_KEY, text)
            commit()
        }
    }

    fun retrieveString(view: View) {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString(MY_PREFS_KEY, "no data")

        val tv = view.rootView.findViewById<TextView>(R.id.textView)
        tv.text = name

    }
}