package com.example.projectdraft1.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.projectdraft1.MedicationDose
import org.json.JSONObject

class DBManager(context: Context) {
    private val dbHelper = DBHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB(){
        db = dbHelper.writableDatabase
    }

    fun insertToDB(
        title: String,
        imageID: Int,
        dose: String,
        dateStart: String,
        regular: Int,
        days: Int,
        everyDay: Int,
        daysWeek: String
    ){
        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_TITLE, title)
            put(DBNameClass.COLUMN_NAME_IMAGE_ID, imageID)
            put(DBNameClass.COLUMN_NAME_DOSE, dose)
            put(DBNameClass.COLUMN_NAME_DATE_START, dateStart)
            put(DBNameClass.COLUMN_NAME_REGULAR, regular)
            put(DBNameClass.COLUMN_NAME_DAYS, days)
            put(DBNameClass.COLUMN_NAME_ACCEPT_DAYS, 0)
            put(DBNameClass.COLUMN_NAME_EVERY_DAY, everyDay)
            put(DBNameClass.COLUMN_NAME_DAY_WEEK, daysWeek)
            put(DBNameClass.COLUMN_NAME_END, 0)
        }

        db?.insert(DBNameClass.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun readDataDB() : ArrayList<MedicationDose>{
        val dataList = ArrayList<MedicationDose>()

        val cursor = db?.query(
            DBNameClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )


        while(cursor?.moveToNext()!!){
            val stringJson = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DOSE))
            val obj = JSONObject(stringJson)
            val doseArray = obj.getJSONArray("dose")

            if(doseArray.length() > 1){
                for (i in 0 until doseArray.length()){
                    val dose = doseArray.getJSONObject(i)

                    val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                    val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE))
                    val imageID = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID))
                    val time = dose.getString("time")
                    val amount = dose.getInt("amount")

                    dataList.add(MedicationDose(id, title, imageID, time, amount))
                }
            } else {
                val dose = doseArray.getJSONObject(0)

                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE))
                val imageID = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID))
                val time = dose.getString("time")
                val amount = dose.getInt("amount")

                dataList.add(MedicationDose(id, title, imageID, time, amount))
            }
        }

        cursor.close()

        return dataList
    }

    fun closeDB(){
        dbHelper.close()
    }
}