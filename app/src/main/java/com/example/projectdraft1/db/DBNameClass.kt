package com.example.projectdraft1.db

import android.provider.BaseColumns

//класс для хранения константных значений базы данных (имя базы данных, имена полей и тд)
object DBNameClass : BaseColumns {
    const val TABLE_NAME = "myTable"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_IMAGE_ID = "image_id"
    const val COLUMN_NAME_DOSE = "dose"
    const val COLUMN_NAME_DATE_START = "date_start"
    const val COLUMN_NAME_DAYS = "days"
    const val COLUMN_NAME_ACCEPT_DAYS = "accept_days"
    const val COLUMN_NAME_END = "end"

    const val DATABASE_VERSION = 2
    const val DATABASE_NAME = "MyDB.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT, " +
            "$COLUMN_NAME_IMAGE_ID INTEGER, $COLUMN_NAME_DOSE TEXT, $COLUMN_NAME_DATE_START TEXT, " +
            "$COLUMN_NAME_DAYS INTEGER, $COLUMN_NAME_ACCEPT_DAYS INTEGER, $COLUMN_NAME_END INTEGER)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}