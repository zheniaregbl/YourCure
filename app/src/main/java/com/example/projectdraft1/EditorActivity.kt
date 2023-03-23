package com.example.projectdraft1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.example.projectdraft1.databinding.ActivityEditorBinding
import kotlinx.android.synthetic.main.activity_editor.*
import java.util.*
import kotlin.time.Duration.Companion.days

class EditorActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditorBinding
    private var selectedItemImage = R.drawable.pill_1
    private val listImage = listOf(
        R.drawable.pill_1,
        R.drawable.pill_2,
        R.drawable.pill_3,
        R.drawable.pill_4,
        R.drawable.pill_5,
        R.drawable.pill_6,
        R.drawable.pill_7
    )
    private var listLineTime = emptyArray<LinearLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Locale.ROOT

        initElements()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initElements() = with(binding){
        btAddEditDone.setOnClickListener {
            val medication = Medication(selectedItemImage, edEdit.text.toString())
            val editIntent = Intent().apply {
                putExtra("medication", medication)
            }

            setResult(RESULT_OK, editIntent)
            finish()
        }

        btAddEditName.setOnClickListener {
            btAddEditName.visibility = View.GONE
            TransitionManager.beginDelayedTransition(scrollView2, AutoTransition())
            layoutChooseTime.visibility = View.VISIBLE
            btAddEditTime.visibility = View.VISIBLE
        }

        btAddEditTime.setOnClickListener {
            btAddEditTime.visibility = View.GONE
            TransitionManager.beginDelayedTransition(scrollView2, AutoTransition())
            layoutSchedule.visibility = View.VISIBLE
            btAddEditDone.visibility = View.VISIBLE
        }

        listLineTime = arrayOf(
            linTime1,
            linTime2,
            linTime3,
            linTime4,
            linTime5
        )

        tvDatePicker.text = SimpleDateFormat("dd.MM").format(System.currentTimeMillis())

        tvDatePicker.setOnClickListener {
            val cal = Calendar.getInstance()

            val dialog = DatePickerDialog(this@EditorActivity,
                R.style.PickerTheme,
                {_, year, month, day_of_month ->
                cal[Calendar.YEAR] = year
                cal[Calendar.MONTH] = month + 1
                cal[Calendar.DAY_OF_MONTH] = day_of_month

                tvDatePicker.text = SimpleDateFormat("dd.MM").format(cal.time)},
                cal[Calendar.YEAR],
                cal[Calendar.MONTH],
                cal[Calendar.DAY_OF_MONTH])

            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.datePicker.maxDate = System.currentTimeMillis() + 31536000000

            dialog.show()
        }

        radioGroupLonger.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbRegular -> tvAmountDay.visibility = View.GONE
                R.id.rbAmountDays -> {
                    val dialog = DaysAmountDialogFragment(layoutSchedule)

                    tvAmountDay.visibility = View.VISIBLE

                    dialog.show(supportFragmentManager, "daysAmountDialog")
                }
            }
        }

        radioGroupDay.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbEveryDay -> tvListDays.visibility = View.GONE
                R.id.rbCurrentDay -> {
                    val dialog = WeekDialogFragment(layoutSchedule)

                    tvListDays.visibility = View.VISIBLE

                    dialog.show(supportFragmentManager, "daysOfWeekDialog")
                }
            }
        }

        setupMedicationSpinner()
        setupDaysAmountSpinner()
        setOnClickLineTime(listLineTime)
    }

    private fun setupMedicationSpinner() = with(binding){
        val adapter = MedicationArrayAdapter(this@EditorActivity, Medications.list!!)

        spinnerMedication.adapter = adapter

        spinnerMedication.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemImage = listImage[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing to do
            }
        }
    }

    private fun setupDaysAmountSpinner() = with(binding){
        val adapter = ArrayAdapter.createFromResource(
            this@EditorActivity,
        R.array.amount_days_list,
        android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        daysAmountSpinner.adapter = adapter

        daysAmountSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> {
                        linTime2.visibility = View.GONE
                        linTime3.visibility = View.GONE
                        linTime4.visibility = View.GONE
                        linTime5.visibility = View.GONE
                    }

                    1 -> {
                        linTime2.visibility = View.VISIBLE
                        linTime3.visibility = View.GONE
                        linTime4.visibility = View.GONE
                        linTime5.visibility = View.GONE
                    }

                    2 -> {
                        linTime2.visibility = View.VISIBLE
                        linTime3.visibility = View.VISIBLE
                        linTime4.visibility = View.GONE
                        linTime5.visibility = View.GONE
                    }

                    3 -> {
                        linTime2.visibility = View.VISIBLE
                        linTime3.visibility = View.VISIBLE
                        linTime4.visibility = View.VISIBLE
                        linTime5.visibility = View.GONE
                    }

                    4 -> {
                        linTime2.visibility = View.VISIBLE
                        linTime3.visibility = View.VISIBLE
                        linTime4.visibility = View.VISIBLE
                        linTime5.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing to do
            }
        }
    }

    private fun setOnClickLineTime(listLineTime : Array<LinearLayout>){
        for(item in listLineTime){
            item.setOnClickListener {
                val dialog = TimeDialogFragment(item)

                dialog.show(supportFragmentManager, "timeDialog")
            }
        }
    }
}