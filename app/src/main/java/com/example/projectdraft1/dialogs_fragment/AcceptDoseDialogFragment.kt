package com.example.projectdraft1.dialogs_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.projectdraft1.MedicationAdapter
import com.example.projectdraft1.MedicationDose
import com.example.projectdraft1.R
import com.example.projectdraft1.ScheduleAlarm
import com.example.projectdraft1.db.DBManager
import kotlinx.android.synthetic.main.accept_medication_dialog.view.*

class AcceptDoseDialogFragment(
    _dbManager: DBManager,
    medicationDose: MedicationDose,
    private val adapter: MedicationAdapter,
    private val scheduleAlarm: ScheduleAlarm
) : DialogFragment() {
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
            dbManager.updateDoneDose(dose)
            adapter.removeItem(dose.doseId)

            var acceptDoses = 0
            val listMedication = dbManager.readActiveMedication()

            for (i in listMedication.indices){
                if (listMedication[i].medicationId == dose.medicationId){
                    acceptDoses = listMedication[i].acceptDose
                    break
                }
            }

            dbManager.updateAcceptMedDose(dose.medicationId.toString(), acceptDoses + 1)

            scheduleAlarm.setUniqueAlarm()

            dismiss()
        }

        rootView.btMissDose.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}