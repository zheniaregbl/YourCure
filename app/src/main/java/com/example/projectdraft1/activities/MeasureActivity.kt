package com.example.projectdraft1.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.projectdraft1.InputFilterFloat
import com.example.projectdraft1.InputFilterMinMax
import com.example.projectdraft1.R
import com.example.projectdraft1.databinding.ActivityMeasureBinding
import com.example.projectdraft1.db.DBNameClass

class MeasureActivity : AppCompatActivity() {
    lateinit var binding: ActivityMeasureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() = with(binding){
        when(intent.getStringExtra("typeMeasure")){
            DBNameClass.TYPE_PRESSURE -> {
                supportActionBar!!.title = resources
                    .getString(R.string.pressure)

                tvMeasureInfo3.visibility = View.VISIBLE
                fabMinusMeasure2.visibility = View.VISIBLE
                edBottomValue.visibility = View.VISIBLE
                fabPlusMeasure2.visibility = View.VISIBLE
                btAddMeasure2.visibility = View.VISIBLE

                btAddMeasure1.visibility = View.GONE

                edTopValue.inputType = 4098
                edTopValue.filters = arrayOf(InputFilterMinMax(1, 300))
                edBottomValue.filters = arrayOf(InputFilterMinMax(1, 300))

                edTopValue.setText("120")
                edBottomValue.setText("80")
            }

            DBNameClass.TYPE_WEIGHT -> {
                supportActionBar!!.title = resources
                    .getString(R.string.weight)

                tvMeasureInfo2.text = resources.getString(R.string.topMeasure2)
                edTopValue.filters = arrayOf(InputFilterFloat(1f, 200f))

                edTopValue.setText("74.5")
            }

            DBNameClass.TYPE_TEMPERATURE -> {
                supportActionBar!!.title = resources
                    .getString(R.string.temperature)

                tvMeasureInfo2.text = resources.getString(R.string.topMeasure3)
                edTopValue.filters = arrayOf(InputFilterFloat(1f, 42f))

                edTopValue.setText("36.6")
            }
        }
    }
}