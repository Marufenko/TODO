package com.todo.todo

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.todo.todo.dailog.MyDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG_ADD_GOAL = "add goal"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    fun putData(view: View) {

        val keyInputField = view.rootView.findViewById<EditText>(R.id.inputKey)
        val key = keyInputField.text.toString()
        val value = "0" // "0" by default as boolean value which represents not ticked goal

        val context = applicationContext

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

    fun getAllData(view: View) {

        // Create an Intent to start the second activity
        val dataIntent = Intent(this, SecondActivity::class.java)

        // db request
        val context = applicationContext
        // Access database
        val dbHelper = FeedReaderDbHelper(context)
        // Gets the data repository in read mode
        val db = dbHelper.readableDatabase
        // Define a projection that specifies which columns from the database fun actually use after query
        val projection = arrayOf(
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_KEY,
                FeedReaderContract.FeedEntry.COLUMN_VALUE
        )
        // Sort results in Cursor
        val sortOrder = "${BaseColumns._ID} DESC"
        val cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME, // The table to query
                projection, // The array of columns to return (pass null to get all)
                null, // no WHERE statement
                null, // no values for WHERE statement
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder // The sort order
        )

        // Access to data within Cursor
        lateinit var itemKey: String
        lateinit var itemValue: String
        val dataArray = arrayListOf<Goal>()
        with(cursor) {
            while (moveToNext()) {
                itemKey = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_KEY))
                itemValue = getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_VALUE))
                dataArray.add(Goal(itemKey, itemValue))
            }
        }

        // Add data from cursor to the extras for the Intent
        dataIntent.putExtra(SecondActivity.DATA, dataArray)

        // Start second activity
        startActivity(dataIntent)
    }

    fun showAddGoalDialog(view: View) {
        MyDialogFragment.show(
                supportFragmentManager = supportFragmentManager,
                tag = TAG_ADD_GOAL,
                dialogTitle = "New goal"
        )
    }
}