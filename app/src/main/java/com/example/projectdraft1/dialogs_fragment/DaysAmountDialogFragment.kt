package com.example.projectdraft1.dialogs_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.example.projectdraft1.Counter
import com.example.projectdraft1.R
import kotlinx.android.synthetic.main.fragment_days_dialog2.view.*

class DaysAmountDialogFragment(layout : LinearLayout) : DialogFragment() {
    private val tvAmount : TextView = layout[5] as TextView
    private var counter = Counter(tvAmount.text.toString().substring(0, tvAmount.text.toString().length - 5).toInt(), 365, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_transparent)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_days_dialog2, container, false)

        rootView.tvAmountDaysSelect.text = tvAmount.text.toString().substring(0, tvAmount.text.toString().length - 5)

        rootView.btDaysCancel.setOnClickListener {
            dismiss()
        }

        rootView.btDaysDone.setOnClickListener {
            tvAmount.text = rootView.tvAmountDaysSelect.text.toString() + " дней"

            dismiss()
        }

        rootView.fabMinusDay.setOnClickListener {
            if (!counter.haveMinSize()) {
                counter--
            }

            rootView.tvAmountDaysSelect.text = counter.toString()
        }

        rootView.fabPlusDay.setOnClickListener {
            if (!counter.haveMaxSize()) {
                counter++
            }

            rootView.tvAmountDaysSelect.text = counter.toString()
        }

        return rootView
    }
}