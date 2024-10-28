package com.csc475.simpletodoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: TodoAdapter
    private lateinit var todoList: MutableList<TodoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize DatabaseHelper and load tasks from database
        dbHelper = DatabaseHelper(this)
        todoList = dbHelper.getAllTodoItems().toMutableList()

        // Set up RecyclerView and Adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = TodoAdapter(todoList) { item ->
            dbHelper.updateTodoItem(item)  // Update item in database when completed status changes
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Add new task functionality
        val todoInput: EditText = findViewById(R.id.todoInput)
        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val title = todoInput.text.toString()
            if (title.isNotBlank()) {
                val newItem = TodoItem(title = title)
                newItem.id = dbHelper.addTodoItem(newItem)
                todoList.add(newItem)
                adapter.notifyItemInserted(todoList.size - 1)
                todoInput.text.clear()
            }
        }
    }
}
