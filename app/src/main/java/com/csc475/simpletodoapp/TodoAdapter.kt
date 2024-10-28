package com.csc475.simpletodoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val todoList: MutableList<TodoItem>,
    private val onItemUpdated: (TodoItem) -> Unit,
    private val onItemDeleted: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.todoTitle)
        val checkBox: CheckBox = view.findViewById(R.id.todoCheckBox)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)
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
        //Toggle checkbox when pressed
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isCompleted = isChecked
            onItemUpdated(item)
        }
        //Delete item when delete button is pressed
        holder.deleteButton.setOnClickListener{
            onItemDeleted(item)
        }
    }

    override fun getItemCount() = todoList.size

    /*Method to remove an item from the list
    fun removeItem(position: Int){
        todoList.removeAt(position)
        notifyItemRemoved(position)
    }

     */

}
