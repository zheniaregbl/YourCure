package com.example.projectdraft1.dialogs_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.projectdraft1.MedicationDose
import com.example.projectdraft1.R
import com.example.projectdraft1.db.DBManager
import kotlinx.android.synthetic.main.accept_medication_dialog.view.*

class AcceptDoseDialogFragment(_dbManager: DBManager, medicationDose: MedicationDose) : DialogFragment() {
    private val dose = medicationDose
    private val dbManager = _dbManager

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
        val rootView : View = inflater.inflate(R.layout.accept_medication_dialog, container, false)

        rootView.acceptDoseImage.setImageResource(dose.imageId)
        rootView.tvTitleAcceptDose.text = dose.title
        rootView.tvAcceptDoseInfo.text = "Принять ${dose.amount} ${dose.stringDose}\n" +
                "Назначено на ${dose.time}"

        rootView.btAcceptDose.setOnClickListener {
            dbManager.updateDose(dose)

            dismiss()
        }

        rootView.btMissDose.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}