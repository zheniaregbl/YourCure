package com.example.projectdraft1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// класс для создания и обновления базы данных
class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DBNameClass.DATABASE_NAME,
    null,
    DBNameClass.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBNameClass.CREATE_TABLE)
        db?.execSQL(DBNameClass.CREATE_TABLE_DOSE)
        db?.execSQL(DBNameClass.CREATE_TABLE_MEASURE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DBNameClass.DELETE_TABLE)
        db?.execSQL(DBNameClass.DELETE_TABLE_DOSE)
        db?.execSQL(DBNameClass.DELETE_TABLE_MEASURE)
        onCreate(db)
    }
}