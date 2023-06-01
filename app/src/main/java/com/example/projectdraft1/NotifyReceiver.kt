package com.example.projectdraft1

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.projectdraft1.activities.MainActivity
import com.example.projectdraft1.db.DBManager
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"
const val idExtra = "idExtra"

class NotifyReceiver : BroadcastReceiver() {

    @SuppressLint("NotificationPermission", "SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        fun passDayMedication(dbManager: DBManager){
            val medicationList = dbManager.readActiveMedication()
            val curDateString = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())

            val curDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDate.parse(curDateString)
            } else {
                TODO("VERSION.SDK_INT < O")
            }

            for (i in medicationList.indices){
                val medication = medicationList[i]

                val dateStart = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.parse(medication.dateStart)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }

                Log.d("tag123", medication.daysPass.toString())

                if ((medication.days != 0) and (dateStart < curDate)){
                    dbManager.updateMedDaysPass(
                        medication.medicationId.toString(),
                        medication.daysPass + 1
                    )
                }
            }
        }

        fun endNotActiveMedication(dbManager: DBManager){
            val medicationList = dbManager.readActiveMedication()

            for (i in medicationList.indices){
                val medication = medicationList[i]

                if ((medication.days == medication.daysPass) and (medication.days != 0)){
                    dbManager.updateEndMedication(medication.medicationId.toString())
                }
            }
        }

        val dbManager = DBManager(context)
        dbManager.openDB()

        if(intent.action.equals("medicationAlarm")){
            dbManager.updateNotifyDose(intent.getStringExtra(idExtra)!!)

            Log.d("tag123", SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date()))

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.mipmap.ic_application_foreground)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setContentIntent(
                    PendingIntent.getActivity(
                        context,
                        0,
                        Intent(context, MainActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build()

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.notify((1..Int.MAX_VALUE).random(), notification)

            val doseList = dbManager.readNoNotifyDose()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val scheduleAlarm = ScheduleAlarm(context, alarmManager)

            Log.d("tag123", doseList.size.toString())

            if (doseList.size != 0) {
                doseList.sort()

                val dose = doseList[0]

                scheduleAlarm.setTimeAlarm(
                    dose.doseId,
                    dose.time.substring(0, 2).toInt(),
                    dose.time.substring(3, dose.time.length).toInt(),
                    dose.title,
                    "Примите лекарство ${dose.title}"
                )
            } else {
                scheduleAlarm.setUniqueAlarm()
                Log.d("tag123", "saveAlarm")
            }
        }
        else if (intent.action.equals("uniqueAlarm")){
            passDayMedication(dbManager)
            endNotActiveMedication(dbManager)

            dbManager.deleteAllDose()

            val medList = dbManager.readActiveMedication()
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val scheduleAlarm = ScheduleAlarm(context, alarmManager)

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

                scheduleAlarm.setTimeAlarm(id, hour, minute, doseList[0].title, "Примите лекарство ${doseList[0].title}")
                Log.d("tag123", "alarmManager created")
            } else {
                scheduleAlarm.setUniqueAlarm()
            }
        }

        dbManager.closeDB()
    }
}