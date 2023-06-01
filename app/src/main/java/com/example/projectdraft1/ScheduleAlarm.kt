package com.example.projectdraft1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.text.format.DateFormat
import com.example.projectdraft1.db.DBManager
import java.util.Calendar
import java.util.Date

class ScheduleAlarm(
    val context: Context,
    private val alarmManager: AlarmManager
    ) {
    private val yearCur = Calendar.getInstance().get(Calendar.YEAR)
    private val monthCur = Calendar.getInstance().get(Calendar.MONTH)
    private val dayCur = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    private val dbManager = DBManager(context)

    fun setTimeAlarm(id: Int, hour: Int, minute: Int, title: String, message: String) {
        val intent = Intent(context, NotifyReceiver::class.java)

        intent.action = "medicationAlarm"
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        intent.putExtra(idExtra, id.toString())

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            100,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmM = alarmManager
        val time = createTime(hour, minute)

        alarmM.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

        Log.d("tag123", "$hour : $minute")
        Log.d("tag123", "done")
    }

    fun setUniqueAlarm() {
        dbManager.openDB()

        if (dbManager.readNoNotifyDose().isEmpty() and dbManager.readActiveMedication().isNotEmpty()) {
            val intent = Intent(context, NotifyReceiver::class.java)

            intent.action = "uniqueAlarm"
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                11,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmM = alarmManager
            var timeAlarm = createTime(0, 0)

            timeAlarm += 86400000

            alarmM.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeAlarm,
                pendingIntent
            )

            Log.d("tag123", "setUnique")
        }

        dbManager.closeDB()
    }

    private fun createTime(hour: Int, minute: Int): Long {
        val cal = Calendar.getInstance()
        cal.set(yearCur, monthCur, dayCur, hour, minute)

        return cal.timeInMillis
    }
}