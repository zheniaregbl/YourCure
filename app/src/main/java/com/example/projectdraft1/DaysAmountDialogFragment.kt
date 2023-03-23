package com.example.projectdraft1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.fragment_days_dialog.view.*

class DaysAmountDialogFragment(layout : LinearLayout) : DialogFragment() {
    private val tvAmount : TextView = layout[5] as TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_days_dialog, container, false)

        rootView.btDaysDialogDone.setOnClickListener {
            tvAmount.text =  rootView.edAmountDays.text.toString() + " дней"

            dismiss()
        }

        rootView.btDaysDialogCancel.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}