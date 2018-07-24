package com.todo.todo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    companion object {
        const val VALUE = "value"
        const val KEY = "key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val recyclerView: RecyclerView = findViewById(R.id.test_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(getData())
    }

    private fun getData() = Pair(intent.getStringExtra(VALUE), intent.getStringExtra(KEY))

    class Adapter(private val values: Pair<String, String>):
            RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun getItemCount() = 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val (value, key) = values
            holder.valueView.text = value
            holder.keyView.text = key
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var valueView: TextView = itemView.findViewById(R.id.valueTitle)
            var keyView: TextView = itemView.findViewById(R.id.keyTitle)
        }
    }
}