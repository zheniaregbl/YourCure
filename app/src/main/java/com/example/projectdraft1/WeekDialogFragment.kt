package com.example.projectdraft1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import kotlinx.android.synthetic.main.fragment_day_week_dialog.*
import kotlinx.android.synthetic.main.fragment_day_week_dialog.view.*

class WeekDialogFragment(layout : LinearLayout) : DialogFragment() {
    private val tvDaysWeek = layout[9] as TextView
    private val listDays = ArrayList<String>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_day_week_dialog, container, false)

        rootView.btDWeekDone.setOnClickListener {
            if(chBox1.isChecked)
                listDays.add("пн")
            if(chBox2.isChecked)
                listDays.add("вт")
            if(chBox3.isChecked)
                listDays.add("ср")
            if(chBox4.isChecked)
                listDays.add("чт")
            if(chBox5.isChecked)
                listDays.add("пт")
            if(chBox6.isChecked)
                listDays.add("сб")
            if(chBox7.isChecked)
                listDays.add("вс")

            tvDaysWeek.text = ""

            for(item in listDays)
                tvDaysWeek.text = tvDaysWeek.text as String + "$item "

            dismiss()
        }

        rootView.btDWeekCancel.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}