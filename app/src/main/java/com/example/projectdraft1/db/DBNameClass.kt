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
    const val COLUMN_NAME_ACCEPT_DOSE = "accept_dose"
    const val COLUMN_NAME_END = "end"

    const val TABLE_NAME_DOSE = "myDoseTable"
    const val COLUMN_NAME_MEDICATION_ID = "med_id"
    const val COLUMN_NAME_TITLE_DOSE = "title"
    const val COLUMN_NAME_IMAGE_ID_DOSE = "image_id"
    const val COLUMN_NAME_TIME = "time"
    const val COLUMN_NAME_AMOUNT = "amount"
    const val COLUMN_NAME_STRING_DOSE = "string_dose"
    const val COLUMN_NAME_DOSE_DONE = "done"

    const val DATABASE_VERSION = 5
    const val DATABASE_NAME = "MyDB.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT, " +
            "$COLUMN_NAME_IMAGE_ID INTEGER, $COLUMN_NAME_DOSE TEXT, $COLUMN_NAME_DATE_START TEXT, " +
            "$COLUMN_NAME_DAYS INTEGER, $COLUMN_NAME_ACCEPT_DOSE INTEGER, $COLUMN_NAME_END INTEGER)"

    const val CREATE_TABLE_DOSE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME_DOSE " +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_MEDICATION_ID INTEGER, " +
            "$COLUMN_NAME_TITLE_DOSE TEXT, $COLUMN_NAME_IMAGE_ID_DOSE INTEGER, " +
            "$COLUMN_NAME_TIME TEXT, $COLUMN_NAME_AMOUNT INTEGER, $COLUMN_NAME_STRING_DOSE TEXT, " +
            "$COLUMN_NAME_DOSE_DONE INTEGER)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    const val DELETE_TABLE_DOSE = "DROP TABLE IF EXISTS $TABLE_NAME_DOSE"
}