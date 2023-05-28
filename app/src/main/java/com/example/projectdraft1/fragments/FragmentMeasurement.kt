package com.example.projectdraft1.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectdraft1.MeasureValue
import com.example.projectdraft1.R
import com.example.projectdraft1.activities.MeasureActivity
import com.example.projectdraft1.databinding.FragmentMeasurementBinding
import com.example.projectdraft1.db.DBNameClass

class FragmentMeasurement : Fragment() {
    lateinit var binding: FragmentMeasurementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeasurementBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements()
    }

    private fun initElements() = with(binding){
        cardPressure.setOnClickListener {
            val intent = Intent(context, MeasureActivity::class.java)

            intent.putExtra("typeMeasure", DBNameClass.TYPE_PRESSURE)
            startActivity(intent)
        }

        cardWeight.setOnClickListener {
            val intent = Intent(context, MeasureActivity::class.java)

            intent.putExtra("typeMeasure", DBNameClass.TYPE_WEIGHT)
            startActivity(intent)
        }

        cardTemperature.setOnClickListener {
            val intent = Intent(context, MeasureActivity::class.java)

            intent.putExtra("typeMeasure", DBNameClass.TYPE_TEMPERATURE)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMeasurement()
    }
}