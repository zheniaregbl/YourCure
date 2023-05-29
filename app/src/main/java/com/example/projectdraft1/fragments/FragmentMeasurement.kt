package com.example.projectdraft1.fragments

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.projectdraft1.db.DBManager
import com.example.projectdraft1.db.DBNameClass

class FragmentMeasurement : Fragment() {
    lateinit var binding: FragmentMeasurementBinding
    lateinit var dbManager: DBManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeasurementBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dbManager = DBManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements()
    }

    override fun onResume() {
        super.onResume()

        dbManager.openDB()
        setLastValue()
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDB()
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

    @SuppressLint("SetTextI18n")
    private fun setLastValue() = with(binding){
        val pressure = dbManager.readLastMeasure(DBNameClass.TYPE_PRESSURE)
        val weight = dbManager.readLastMeasure(DBNameClass.TYPE_WEIGHT)
        val temperature = dbManager.readLastMeasure(DBNameClass.TYPE_TEMPERATURE)

        if (pressure.firstValue == 0f) {
            tvPressureValue.text = "-/-"
        } else {
            tvPressureValue.text = "${pressure.firstValue.toInt()}/${pressure.secondValue}"
        }

        if (weight.firstValue == 0f) {
            tvWeightValue.text = "-"
        } else {
            tvWeightValue.text = weight.firstValue.toString()
        }

        if (temperature.firstValue == 0f) {
            tvTemperatureValue.text = "-"
        } else {
            tvTemperatureValue.text = temperature.firstValue.toString()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMeasurement()
    }
}