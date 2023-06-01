package com.example.projectdraft1.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdraft1.InputFilterFloat
import com.example.projectdraft1.InputFilterMinMax
import com.example.projectdraft1.adapters.MeasureAdapter
import com.example.projectdraft1.R
import com.example.projectdraft1.databinding.ActivityMeasureBinding
import com.example.projectdraft1.db.DBManager
import com.example.projectdraft1.db.DBNameClass
import java.text.SimpleDateFormat
import java.time.LocalDate

class MeasureActivity : AppCompatActivity() {
    lateinit var binding: ActivityMeasureBinding
    lateinit var adapter: MeasureAdapter
    private val dbManager = DBManager(this)
    private var isCardHidden = false

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()

        dbManager.openDB()
        fillAdapter(intent.getStringExtra("typeMeasure").toString())
        showAnimLottie()
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDB()
    }

    @SuppressLint("NewApi", "SetTextI18n", "SimpleDateFormat")
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

                initPressureButtons()
                initAdapter(DBNameClass.TYPE_PRESSURE)

                btAddMeasure2.setOnClickListener {
                    if ((edTopValue.text.toString() != "") and
                        (edBottomValue.text.toString() != "")) {
                        dbManager.insertMeasure(
                            edTopValue.text.toString().toFloat(),
                            edBottomValue.text.toString().toInt(),
                            SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),
                            DBNameClass.TYPE_PRESSURE
                        )

                        fillAdapter(DBNameClass.TYPE_PRESSURE)
                    } else {
                        Toast.makeText(
                            this@MeasureActivity,
                            "Одно из полей ввода пустое",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    showAnimLottie()
                }
            }

            DBNameClass.TYPE_WEIGHT -> {
                supportActionBar!!.title = resources
                    .getString(R.string.weight)

                tvMeasureInfo2.text = resources.getString(R.string.topMeasure2)
                edTopValue.filters = arrayOf(InputFilterFloat(1f, 300f))

                edTopValue.setText("74.5")

                initWeightButtons()
                initAdapter(DBNameClass.TYPE_WEIGHT)

                btAddMeasure1.setOnClickListener {
                    if (edTopValue.text.toString() != "") {
                        dbManager.insertMeasure(
                            edTopValue.text.toString().toFloat(),
                            null,
                            SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),
                            DBNameClass.TYPE_WEIGHT
                        )

                        fillAdapter(DBNameClass.TYPE_WEIGHT)
                    } else {
                        Toast.makeText(
                            this@MeasureActivity,
                            "Поле ввода пустое",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    showAnimLottie()
                }
            }

            DBNameClass.TYPE_TEMPERATURE -> {
                supportActionBar!!.title = resources
                    .getString(R.string.temperature)

                tvMeasureInfo2.text = resources.getString(R.string.topMeasure3)
                edTopValue.filters = arrayOf(InputFilterFloat(1f, 42f))

                edTopValue.setText("36.6")

                initTemperatureButtons()
                initAdapter(DBNameClass.TYPE_TEMPERATURE)

                btAddMeasure1.setOnClickListener {
                    if (edTopValue.text.toString() != "") {
                        dbManager.insertMeasure(
                            edTopValue.text.toString().toFloat(),
                            null,
                            SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),
                            DBNameClass.TYPE_TEMPERATURE
                        )

                        fillAdapter(DBNameClass.TYPE_TEMPERATURE)
                    } else {
                        Toast.makeText(
                            this@MeasureActivity,
                            "Поле ввода пустое",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    showAnimLottie()
                }
            }
        }

        edTopValue.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus and (edTopValue.text.toString() == "")) {
                edTopValue.setText("1")
            }
        }

        edBottomValue.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus and (edBottomValue.text.toString() == "")) {
                edBottomValue.setText("1")
            }
        }

        tvHideCard.setOnClickListener {
            if (!isCardHidden) {
                cardEditMeasure.visibility = View.GONE
                tvHideCard.text = resources.getString(R.string.textReveal)
            } else {
                cardEditMeasure.visibility = View.VISIBLE
                tvHideCard.text = resources.getString(R.string.textHide)
            }

            isCardHidden = !isCardHidden
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initPressureButtons() = with(binding){
        fabMinusMeasure.setOnClickListener {
            if (edTopValue.text.toString() != "") {
                val num = edTopValue.text.toString().toInt()

                if (num >= 2) {
                    edTopValue.setText((num - 1).toString())
                }
            }
        }

        fabPlusMeasure.setOnClickListener {
            if (edTopValue.text.toString() != "") {
                val num = edTopValue.text.toString().toInt()

                if (num <= 299) {
                    edTopValue.setText((num + 1).toString())
                }
            }
        }

        fabMinusMeasure2.setOnClickListener {
            if (edBottomValue.text.toString() != "") {
                val num = edBottomValue.text.toString().toInt()

                if (num >= 2) {
                    edBottomValue.setText((num - 1).toString())
                }
            }
        }

        fabPlusMeasure2.setOnClickListener {
            if (edBottomValue.text.toString() != "") {
                val num = edBottomValue.text.toString().toInt()

                if (num <= 299) {
                    edBottomValue.setText((num + 1).toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initWeightButtons() = with(binding){
        fabMinusMeasure.setOnClickListener {
            if (edTopValue.text.toString() != "") {
                val num = edTopValue.text.toString().toFloat()

                if (num >= 1.5f) {
                    edTopValue.setText((num - 0.5f).toString())
                }
            }
        }

        fabPlusMeasure.setOnClickListener {
            if (edTopValue.text.toString() != "") {
                val num = edTopValue.text.toString().toFloat()

                if (num <= 299.5f) {
                    edTopValue.setText((num + 0.5f).toString())
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTemperatureButtons() = with(binding){
        fabMinusMeasure.setOnClickListener {
            if (edTopValue.text.toString() != "") {
                val num = edTopValue.text.toString().toFloat()

                if (num >= 1.5f) {
                    edTopValue.setText((num - 0.5f).toString())
                }
            }
        }

        fabPlusMeasure.setOnClickListener {
            if (edTopValue.text.toString() != "") {
                val num = edTopValue.text.toString().toFloat()

                if (num <= 41.5f) {
                    edTopValue.setText((num + 0.5f).toString())
                }
            }
        }
    }

    private fun initAdapter(type: String) = with(binding){
        adapter = MeasureAdapter(type)

        rcViewMeasure.layoutManager = LinearLayoutManager(this@MeasureActivity)
        rcViewMeasure.adapter = adapter
    }


    @SuppressLint("NewApi")
    private fun fillAdapter(type: String){
        val measureList = dbManager.readMeasure(type)

        if (measureList.isNotEmpty()) {
            measureList.sortBy { LocalDate.parse(it.dateMeasure) }
            adapter.setListAdapter(measureList)
        }
    }

    private fun showAnimLottie() = with(binding){
        if (adapter.getListAdapter().isEmpty()) {
            animMeasure.visibility = View.VISIBLE
            tvNotOnceMeasure.visibility = View.VISIBLE
        } else {
            animMeasure.visibility = View.GONE
            tvNotOnceMeasure.visibility = View.GONE
        }
    }
}