package com.example.projectdraft1

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_time_dialog2.view.*

class TimeDialogFragment(lineTime: LinearLayout) : DialogFragment() {
    private val tvTime: TextView = lineTime[0] as TextView
    private val tvAmount: TextView = lineTime[1] as TextView
    private var counter = Counter(tvAmount.text.toString().substring(0, tvAmount.text.toString().length - 5).toInt(), 10, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_time_dialog2, container, false)

        rootView.tvTimeSelect.text = tvTime.text
        rootView.tvDoseSelect.text = tvAmount.text.toString().substring(0, tvAmount.text.toString().length - 5)

        rootView.btTimeCancel.setOnClickListener {
            dismiss()
        }

        rootView.btTimeDone.setOnClickListener {
            tvTime.text = rootView.tvTimeSelect.text
            tvAmount.text = rootView.tvDoseSelect.text.toString() + " штук"

            dismiss()
        }

        rootView.tvTimeSelect.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                rootView.tvTimeSelect.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(
                context,
                R.style.PickerTheme,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true)
                .show()
        }

        rootView.fabMinus.setOnClickListener {
            if (!counter.haveMinSize()) {
                counter--
            }

            rootView.tvDoseSelect.text = counter.toString()
        }

        rootView.fabPlus.setOnClickListener {
            if (!counter.haveMaxSize()) {
                counter++
            }

            rootView.tvDoseSelect.text = counter.toString()
        }

        return rootView
    }
}