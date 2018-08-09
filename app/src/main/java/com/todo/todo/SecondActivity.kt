package com.todo.todo

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import android.widget.CompoundButton
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.support.v4.content.ContextCompat


class SecondActivity : AppCompatActivity() {

    companion object {
        const val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val recyclerView: RecyclerView = findViewById(R.id.test_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(getData())
    }

    private fun getData() = intent.getParcelableArrayListExtra<Goal>(DATA)

    class Adapter(private val values: ArrayList<Goal>) :
            RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun getItemCount() = values.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycler_view, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.checkBox.text = values[position].key

            //in some cases, it will prevent unwanted situations
            holder.checkBox.setOnCheckedChangeListener(null)

            //if true, your checkbox will be selected, else unselected
            holder.checkBox.isChecked = false

            //simple onClick action for checkBox
            holder.checkBox.setOnCheckedChangeListener(
                    { _, _ ->
                        holder.checkBox.paintFlags = holder.checkBox.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                        holder.checkBox.setTextColor(Color.parseColor("#898989"))
                    })
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        }
    }
}