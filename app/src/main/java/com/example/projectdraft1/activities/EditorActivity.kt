package com.example.projectdraft1.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import com.example.projectdraft1.dialogs_fragment.DaysAmountDialogFragment
import com.example.projectdraft1.MedicationArrayAdapter
import com.example.projectdraft1.Medications
import com.example.projectdraft1.R
import com.example.projectdraft1.dialogs_fragment.TimeDialogFragment
import com.example.projectdraft1.dialogs_fragment.WeekDialogFragment
import com.example.projectdraft1.databinding.ActivityEditorBinding
import com.example.projectdraft1.db.DBManager
import com.google.android.material.shape.CornerFamily
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

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
    private val dbManager = DBManager(this)
    private var amountDays = false
    private var days = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.title = resources.getString(R.string.editor_medicine)

        super.onCreate(savedInstanceState)
        binding = ActivityEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Locale.ROOT

        initElements()
    }

    override fun onResume() {
        super.onResume()

        dbManager.openDB()
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDB()
    }

    private fun formationDose(listLineTime : Array<LinearLayout>, stringDose : String): String{ //!!!
        val obj = JSONObject()
        val arrayObj = JSONArray()

        for(item in listLineTime){
            if(item.visibility == View.VISIBLE){
                val time = item[0] as TextView
                val amount = item[1] as TextView

                arrayObj.put(JSONObject()
                    .put("time", time.text.toString())
                    .put("amount", amount.text.toString().substring(8, amount.text.toString().length).toInt())
                    .put("stringDose", stringDose)
                )
            }
        }

        obj.put("dose", arrayObj)

        return obj.toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initElements() = with(binding){
        cardStart.shapeAppearanceModel = cardStart.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED, 32f)
            .setTopRightCorner(CornerFamily.ROUNDED, 32f)
            .setBottomLeftCorner(CornerFamily.ROUNDED, 32f)
            .setBottomRightCorner(CornerFamily.ROUNDED, 32f)
            .build()

        listLineTime = arrayOf(
            linTime1,
            linTime2,
            linTime3,
            linTime4,
            linTime5
        )

        btAddEditDone.setOnClickListener {
            if (amountDays) {
                days = tvAmountDay.text.toString()
                    .substring(0, tvAmountDay.text.toString().length - 5)
                    .toInt()
            }

            if (edEdit.text.isNotEmpty()) {
                dbManager.insertMedication(
                    edEdit.text.toString(),
                    selectedItemImage,
                    formationDose(listLineTime, spinnerMedicationDose.selectedItem.toString()),
                    tvDatePicker.text.toString(),
                    days
                )

                dbManager.closeDB()

                finish()
            } else {
                Toast.makeText(
                    this@EditorActivity,
                    "Вы не заполнили название лекарства",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btAddEditName.setOnClickListener {
            btAddEditName.visibility = View.GONE
            TransitionManager.beginDelayedTransition(scrollView2, AutoTransition())
            layoutChooseTime.visibility = View.VISIBLE
            btAddEditTime.visibility = View.VISIBLE
            cardStart.shapeAppearanceModel = cardStart.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 32f)
                .setTopRightCorner(CornerFamily.ROUNDED, 32f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                .build()

            cardTime.shapeAppearanceModel = cardStart.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 32f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 32f)
                .build()
        }

        btAddEditTime.setOnClickListener {
            btAddEditTime.visibility = View.GONE
            TransitionManager.beginDelayedTransition(scrollView2, AutoTransition())
            layoutSchedule.visibility = View.VISIBLE
            btAddEditDone.visibility = View.VISIBLE

            cardStart.shapeAppearanceModel = cardStart.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 32f)
                .setTopRightCorner(CornerFamily.ROUNDED, 32f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                .build()

            cardTime.shapeAppearanceModel = cardStart.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 0f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 0f)
                .build()

            cardDays.shapeAppearanceModel = cardStart.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 0f)
                .setTopRightCorner(CornerFamily.ROUNDED, 0f)
                .setBottomLeftCorner(CornerFamily.ROUNDED, 32f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 32f)
                .build()
        }

        tvDatePicker.text = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())

        tvDatePicker.setOnClickListener {
            val cal = Calendar.getInstance()

            val dialog = DatePickerDialog(this@EditorActivity,
                R.style.PickerTheme,
                {_, year, month, day_of_month ->
                cal[Calendar.YEAR] = year
                cal[Calendar.MONTH] = month + 1
                cal[Calendar.DAY_OF_MONTH] = day_of_month

                tvDatePicker.text = SimpleDateFormat("yyyy-MM-dd").format(cal.time)},
                cal[Calendar.YEAR],
                cal[Calendar.MONTH],
                cal[Calendar.DAY_OF_MONTH])

            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.datePicker.maxDate = System.currentTimeMillis() + 31536000000

            dialog.show()
        }

        radioGroupLonger.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rbRegular -> {
                    amountDays = false

                    tvAmountDay.visibility = View.GONE
                }
                R.id.rbAmountDays -> {
                    amountDays = true

                    val dialog = DaysAmountDialogFragment(layoutSchedule)

                    tvAmountDay.visibility = View.VISIBLE

                    dialog.show(supportFragmentManager, "daysAmountDialog")
                }
            }
        }

        tvAmountDay.setOnClickListener {
            val dialog = DaysAmountDialogFragment(layoutSchedule)

            dialog.show(supportFragmentManager, "daysAmountDialog")
        }

        setupMedicationSpinner()
        setupMedicationDoseSpinner(R.array.dose_string_array)
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
                when(position){
                    0 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array)
                    }

                    1 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array2)
                    }

                    2 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array3)
                    }

                    3 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array4)
                    }

                    4 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array5)
                    }

                    5 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array6)
                    }

                    6 -> {
                        setupMedicationDoseSpinner(R.array.dose_string_array5)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing to do
            }
        }
    }

    private fun setupMedicationDoseSpinner(resourceStringArray: Int) = with(binding){
        val adapter = ArrayAdapter.createFromResource(
            this@EditorActivity,
            resourceStringArray,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerMedicationDose.adapter = adapter
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