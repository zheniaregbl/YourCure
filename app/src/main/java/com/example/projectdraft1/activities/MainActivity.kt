package com.example.projectdraft1.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectdraft1.fragments.FragmentMeasurement
import com.example.projectdraft1.fragments.FragmentSettings
import com.example.projectdraft1.fragments.FragmentToday
import com.example.projectdraft1.R
import com.example.projectdraft1.databinding.ActivityMainBinding
import com.example.projectdraft1.fragments.FragmentMedicine

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = resources.getString(R.string.today)

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
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction().replace(R.id.placeHolder, fragment)
            .commit()
    }
}