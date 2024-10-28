package com.csc475.simpletodoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TodoApp.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_TODO = "todo"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IS_COMPLETED = "isCompleted"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_TODO (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_IS_COMPLETED INTEGER NOT NULL DEFAULT 0
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TODO")
        onCreate(db)
    }

    fun addTodoItem(item: TodoItem): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_IS_COMPLETED, if (item.isCompleted) 1 else 0)
        }
        return db.insert(TABLE_TODO, null, values)
    }

    fun getAllTodoItems(): List<TodoItem> {
        val items = mutableListOf<TodoItem>()
        val db = readableDatabase
        val cursor = db.query(TABLE_TODO, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))
                val isCompleted = getInt(getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1
                items.add(TodoItem(id, title, isCompleted))
            }
            close()
        }
        return items
    }

    fun updateTodoItem(item: TodoItem): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_IS_COMPLETED, if (item.isCompleted) 1 else 0)
        }
        return db.update(TABLE_TODO, values, "$COLUMN_ID = ?", arrayOf(item.id.toString()))
    }

    fun deleteTodoItem(itemId: Long): Int {
        val db = writableDatabase
        return db.delete(TABLE_TODO, "$COLUMN_ID = ?", arrayOf(itemId.toString()))
    }
}
