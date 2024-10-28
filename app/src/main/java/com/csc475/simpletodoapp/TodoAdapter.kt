package com.csc475.simpletodoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todoList: List<TodoItem>,
    private val onItemUpdated: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.todoTitle)
        val checkBox: CheckBox = view.findViewById(R.id.todoCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = todoList[position]
        holder.title.text = item.title
        holder.checkBox.isChecked = item.isCompleted
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isCompleted = isChecked
            onItemUpdated(item)
        }
    }

    override fun getItemCount() = todoList.size
}
