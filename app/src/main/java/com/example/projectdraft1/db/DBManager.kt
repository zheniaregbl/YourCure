package com.example.projectdraft1.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.projectdraft1.MeasureValue
import com.example.projectdraft1.Medication
import com.example.projectdraft1.MedicationDose
import org.json.JSONObject

// класс для взаимодействия с записями внутри таблиц базы данных
class DBManager(context: Context) {
    private val dbHelper = DBHelper(context)
    var db: SQLiteDatabase? = null

    // открытие базы данных
    fun openDB(){
        db = dbHelper.writableDatabase
    }

    // запись лекарства в таблицу базы данных
    fun insertMedication(
        title: String,
        imageID: Int,
        dose: String,
        dateStart: String,
        days: Int
    ){
        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_TITLE, title)
            put(DBNameClass.COLUMN_NAME_IMAGE_ID, imageID)
            put(DBNameClass.COLUMN_NAME_DOSE, dose)
            put(DBNameClass.COLUMN_NAME_DATE_START, dateStart)
            put(DBNameClass.COLUMN_NAME_DAYS, days)
            put(DBNameClass.COLUMN_NAME_DAYS_PASS, 0)
            put(DBNameClass.COLUMN_NAME_ACCEPT_DOSE, 0)
            put(DBNameClass.COLUMN_NAME_END, 0)
        }

        db?.insert(DBNameClass.TABLE_NAME, null, values)
    }

    // запись дозы лекарства в таблицу базы данных
    fun insertDose(
        medicationId: Int,
        title: String,
        imageId: Int,
        time: String,
        amount: Int,
        stringDose: String
    ){
        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_MEDICATION_ID, medicationId)
            put(DBNameClass.COLUMN_NAME_TITLE_DOSE, title)
            put(DBNameClass.COLUMN_NAME_IMAGE_ID_DOSE, imageId)
            put(DBNameClass.COLUMN_NAME_TIME, time)
            put(DBNameClass.COLUMN_NAME_AMOUNT, amount)
            put(DBNameClass.COLUMN_NAME_STRING_DOSE, stringDose)
            put(DBNameClass.COLUMN_NAME_DOSE_DONE, 0)
            put(DBNameClass.COLUMN_NAME_DOSE_NOTIFY, 0)
        }

        db?.insert(DBNameClass.TABLE_NAME_DOSE, null, values)
    }

    fun insertPressure(topValue: Int, bottomValue: Int, date: String){
        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_TOP, topValue)
            put(DBNameClass.COLUMN_NAME_BOTTOM, bottomValue)
            put(DBNameClass.COLUMN_NAME_DATE_PRESSURE, date)
        }

        db?.insert(DBNameClass.TABLE_NAME_PRESSURE, null, values)
    }

    fun insertWeight(value: Float, date: String){
        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_VALUE_WEIGHT, value)
            put(DBNameClass.COLUMN_NAME_DATE_WEIGHT, date)
        }

        db?.insert(DBNameClass.TABLE_NAME_WEIGHT, null, values)
    }

    fun insertTemperature(value: Float, date: String){
        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_VALUE_TEMPERATURE, value)
            put(DBNameClass.COLUMN_NAME_DATE_TEMPERATURE, date)
        }

        db?.insert(DBNameClass.TABLE_NAME_WEIGHT, null, values)
    }

    // получение текущих лекарств
    @SuppressLint("Range")
    fun readActiveMedication() : ArrayList<Medication>{
        val dataList = ArrayList<Medication>()

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
            val days = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DAYS))
            val daysPass = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DAYS_PASS))
            val dateStart = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DATE_START))
            val acceptDose = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_ACCEPT_DOSE))
            val stringJson = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DOSE))
            /*val countDose = JSONObject(stringJson).getJSONArray("dose").length()

            (days == 0) or (acceptDose < days * countDose)*/

            val end = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_END))

            if (end == 0){
                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE))
                val imageID = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID))

                dataList.add(
                    Medication(
                        id,
                        imageID,
                        title,
                        dateStart,
                        stringJson,
                        days,
                        daysPass,
                        acceptDose
                    )
                )
            }
        }

        cursor.close()

        return dataList
    }

    // получение всех лекарств
    @SuppressLint("Range")
    fun readAllMedication() : ArrayList<Medication>{
        val dataList = ArrayList<Medication>()

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
            val days = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DAYS))
            val daysPass = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DAYS_PASS))
            val dateStart = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DATE_START))
            val acceptDose = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_ACCEPT_DOSE))
            val stringJson = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DOSE))
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE))
            val imageID = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID))

            dataList.add(
                Medication(
                    id,
                    imageID,
                    title,
                    dateStart,
                    stringJson,
                    days,
                    daysPass,
                    acceptDose
                )
            )
        }

        cursor.close()

        return dataList
    }

    // получение всех доз лекарств
    @SuppressLint("Range", "Recycle")
    fun readDose() : ArrayList<MedicationDose> {
        val dataList = ArrayList<MedicationDose>()

        val cursor = db?.query(
            DBNameClass.TABLE_NAME_DOSE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while(cursor?.moveToNext()!!){
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val medicationId = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_MEDICATION_ID))
            val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE_DOSE))
            val imageId = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID_DOSE))
            val time = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TIME))
            val amount = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_AMOUNT))
            val stringDose = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_STRING_DOSE))

            dataList.add(MedicationDose(id, medicationId, title, imageId, time, amount, stringDose))
        }

        return dataList
    }

    // получение текущих доз лекарств
    @SuppressLint("Range", "Recycle")
    fun readActiveDose() : ArrayList<MedicationDose> {
        val dataList = ArrayList<MedicationDose>()

        val cursor = db?.query(
            DBNameClass.TABLE_NAME_DOSE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while(cursor?.moveToNext()!!){
            val done = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DOSE_DONE))

            if (done == 0){
                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val medicationId = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_MEDICATION_ID))
                val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE_DOSE))
                val imageId = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID_DOSE))
                val time = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TIME))
                val amount = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_AMOUNT))
                val stringDose = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_STRING_DOSE))

                dataList.add(MedicationDose(id, medicationId, title, imageId, time, amount, stringDose))
            }
        }

        return dataList
    }

    // получение доз, о которых не было уведомлений
    @SuppressLint("Recycle", "Range")
    fun readNoNotifyDose() : ArrayList<MedicationDose> {
        val dataList = ArrayList<MedicationDose>()

        val cursor = db?.query(
            DBNameClass.TABLE_NAME_DOSE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while(cursor?.moveToNext()!!) {
            val notify = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DOSE_NOTIFY))

            if (notify == 0) {
                val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                val medicationId = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_MEDICATION_ID))
                val title = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TITLE_DOSE))
                val imageId = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_IMAGE_ID_DOSE))
                val time = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TIME))
                val amount = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_AMOUNT))
                val stringDose = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_STRING_DOSE))

                dataList.add(MedicationDose(id, medicationId, title, imageId, time, amount, stringDose))
            }
        }

        return dataList
    }

    @SuppressLint("Range", "Recycle")
    fun readPressureValue() : ArrayList<MeasureValue> {
        val dataList = ArrayList<MeasureValue>()

        val cursor = db?.query(
            DBNameClass.TABLE_NAME_PRESSURE,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while(cursor?.moveToNext()!!) {
            val firstValue = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_TOP))
            val secondValue = cursor.getInt(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_BOTTOM))
            val date = cursor.getString(cursor.getColumnIndex(DBNameClass.COLUMN_NAME_DATE_PRESSURE))

            dataList.add(MeasureValue(firstValue, secondValue, date))
        }

        return dataList
    }

    // удаление дозы лекарства по id
    fun deleteDose(id: String){
        val selection = BaseColumns._ID + "=$id"

        db?.delete(DBNameClass.TABLE_NAME_DOSE, selection, null)
    }

    // удаление всех доз лекарств
    fun deleteAllDose() {
        db?.delete(DBNameClass.TABLE_NAME_DOSE, null, null)
    }

    fun updateEndMedication(id: String){
        val selection = BaseColumns._ID + "=${id}"

        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_END, 1)
        }

        db?.update(DBNameClass.TABLE_NAME, values, selection, null)
    }

    fun updateMedDaysPass(id: String, daysPass: Int){
        val selection = BaseColumns._ID + "=${id}"

        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_DAYS_PASS, daysPass)
        }

        db?.update(DBNameClass.TABLE_NAME, values, selection, null)
    }

    fun updateAcceptMedDose(id: String, acceptDoses: Int){
        val selection = BaseColumns._ID + "=${id}"

        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_ACCEPT_DOSE, acceptDoses)
        }

        db?.update(DBNameClass.TABLE_NAME, values, selection, null)
    }

    // обновление дозы лекарства (при принятии лекарства)
    fun updateDoneDose(dose: MedicationDose) {
        val selection = BaseColumns._ID + "=${dose.doseId}"

        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_MEDICATION_ID, dose.medicationId)
            put(DBNameClass.COLUMN_NAME_TITLE_DOSE, dose.title)
            put(DBNameClass.COLUMN_NAME_IMAGE_ID_DOSE, dose.imageId)
            put(DBNameClass.COLUMN_NAME_TIME, dose.time)
            put(DBNameClass.COLUMN_NAME_AMOUNT, dose.amount)
            put(DBNameClass.COLUMN_NAME_STRING_DOSE, dose.stringDose)
            put(DBNameClass.COLUMN_NAME_DOSE_DONE, 1)
        }

        db?.update(DBNameClass.TABLE_NAME_DOSE, values, selection, null)
    }

    // обновление дозы лекарства (при получении уведомления)
    fun updateNotifyDose(id: String){
        val selection = BaseColumns._ID + "=${id}"

        val values = ContentValues().apply {
            put(DBNameClass.COLUMN_NAME_DOSE_NOTIFY, 1)
        }

        db?.update(DBNameClass.TABLE_NAME_DOSE, values, selection, null)
    }

    fun closeDB(){
        dbHelper.close()
    }
}