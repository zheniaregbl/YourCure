package com.example.projectdraft1

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_time_dialog.view.*

class TimeDialogFragment(lineTime: LinearLayout) : DialogFragment() {
    private val tvTime: TextView = lineTime[0] as TextView
    private val tvAmount: TextView = lineTime[1] as TextView

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_time_dialog, container, false)

        rootView.tvTimeRedactor.text = tvTime.text

        rootView.btDialogCancel.setOnClickListener{
            dismiss()
        }

        rootView.btDialogDone.setOnClickListener {
            tvTime.text = rootView.tvTimeRedactor.text
            tvAmount.text = rootView.edAmount.text.toString() + " штук"

            dismiss()
        }

        rootView.tvTimeRedactor.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                rootView.tvTimeRedactor.text = SimpleDateFormat("HH:mm").format(cal.time)
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

        return rootView
    }
}