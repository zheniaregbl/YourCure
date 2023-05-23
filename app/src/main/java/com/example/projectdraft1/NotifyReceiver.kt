package com.example.projectdraft1

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.projectdraft1.db.DBManager
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date

const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class NotifyReceiver : BroadcastReceiver() {

    @SuppressLint("NotificationPermission", "SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        val dbManager = DBManager(context)
        dbManager.openDB()

        if(intent.action.equals("medicationAlarm")){
            Log.d("tag123", SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date()))

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build()

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify((0..Int.MAX_VALUE).random(), notification)
        }
        else if (intent.action.equals("uniqueAlarm")){
            dbManager.deleteAllDose()

            val medList = dbManager.readActiveMedication()

            if (medList.isNotEmpty()){
                medList.forEach {
                    val stringJson = it.dose
                    val obj = JSONObject(stringJson)
                    val doseArray = obj.getJSONArray("dose")
                    for (i in 0 until doseArray.length()){
                        val dose = doseArray.getJSONObject(i)
                        val time = dose.getString("time")
                        val amount = dose.getInt("amount")
                        val stringDose = dose.getString("stringDose")

                        dbManager.insertDose(
                            it.medicationId,
                            it.title,
                            it.imageId,
                            time,
                            amount,
                            stringDose
                        )
                    }
                }

                val doseList = dbManager.readDose()

                val id = doseList[0].doseId
                val hour = doseList[0].time.substring(0, 2).toInt()
                val minute = doseList[0].time.substring(3, doseList[0].time.length).toInt()
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val scheduleAlarm = ScheduleAlarm(context, alarmManager)
                scheduleAlarm.setTimeAlarm(id, hour, minute, doseList[0].title, "Примите лекарство ${doseList[0].title}")
                Log.d("tag123", "alarmManager created")
            }
        }

        dbManager.closeDB()
    }
}