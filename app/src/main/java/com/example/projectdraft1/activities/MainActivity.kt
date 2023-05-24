package com.example.projectdraft1.activities

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectdraft1.fragments.FragmentMeasurement
import com.example.projectdraft1.fragments.FragmentSettings
import com.example.projectdraft1.fragments.FragmentToday
import com.example.projectdraft1.R
import com.example.projectdraft1.ScheduleAlarm
import com.example.projectdraft1.channelID
import com.example.projectdraft1.databinding.ActivityMainBinding
import com.example.projectdraft1.db.DBManager
import com.example.projectdraft1.fragments.FragmentMedicine

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val dbManager = DBManager(this)

    @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val scheduleAlarm = ScheduleAlarm(applicationContext, alarmManager)
        scheduleAlarm.setUniqueAlarm()

        supportActionBar!!.title = resources.getString(R.string.today)

        // слушатель нажатий на нижнюю панель навигации
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.today -> {
                    supportActionBar!!.title = resources.getString(R.string.today)
                    openFragment(FragmentToday())
                }
                R.id.statistics -> {
                    supportActionBar!!.title = resources.getString(R.string.measurement)
                    openFragment(FragmentMeasurement())
                }
                R.id.pill -> {
                    supportActionBar!!.title = resources.getString(R.string.medicine)
                    openFragment(FragmentMedicine())
                }
                R.id.setting -> {
                    supportActionBar!!.title = resources.getString(R.string.settings)
                    openFragment(FragmentSettings())
                }
            }

            true
        }

        openFragment(FragmentToday())
        createNotificationChannel()
    }

    override fun onResume() {
        super.onResume()

        dbManager.openDB()

        if (dbManager.readActiveDose().isEmpty()) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val scheduleAlarm = ScheduleAlarm(applicationContext, alarmManager)

            scheduleAlarm.setUniqueAlarm()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDB()
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction().replace(R.id.placeHolder, fragment)
            .commit()
    }

    // создание канала уведомлений
    @SuppressLint("NewApi")
    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .build()

        val channel = NotificationChannel(channelID, name, importance)

        channel.description = desc
        channel.setSound(
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
            audioAttributes
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}