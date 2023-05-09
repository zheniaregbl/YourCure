package com.example.projectdraft1.activities

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.today -> {
                    openFragment(FragmentToday())
                }
                R.id.statistics -> {
                    openFragment(FragmentMeasurement())
                }
                R.id.pill -> {
                    openFragment(FragmentMedicine())
                }
                R.id.setting -> {
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