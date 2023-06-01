package com.example.projectdraft1.dialogs_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.projectdraft1.DosePerformance
import com.example.projectdraft1.Medication
import com.example.projectdraft1.MedicationDose
import com.example.projectdraft1.MedicineAdapter
import com.example.projectdraft1.R
import com.example.projectdraft1.db.DBManager
import com.example.projectdraft1.getDoseFromJson
import kotlinx.android.synthetic.main.fragment_info_medication.view.*

class MedInfoDialogFragment(
    private val dbManager: DBManager,
    private val element: Medication,
    private val adapter: MedicineAdapter
) : DialogFragment() {
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
        val rootView : View = inflater.inflate(R.layout.fragment_info_medication, container, false)
        val arrayNotifyText = arrayListOf(
            rootView.tvNotify1,
            rootView.tvNotify2,
            rootView.tvNotify3,
            rootView.tvNotify4,
            rootView.tvNotify5
        )

        fun initElements(item: Medication){
            rootView.tvInfoMedicationName.text = item.title

            if (item.days == 0) {
                rootView.tvInfoMedCountDays.text = "∞"
            } else {
                if (item.daysPass == 0) {
                    rootView.tvInfoMedCountDays.text = "0/${item.days} день"
                } else {
                    rootView.tvInfoMedCountDays.text = "${item.acceptDose / item.daysPass}" +
                            "/${item.days} день"
                }
            }

            rootView.tvStartDate.text = item.dateStart
        }

        initElements(element)

        val doseList: ArrayList<DosePerformance> = getDoseFromJson(element)

        for (i in 0 until doseList.size) {
            arrayNotifyText[i].text = "Время: ${doseList[i].time}\n" +
                    "Принять ${doseList[i].amount} ${doseList[i].stringDose}"

            arrayNotifyText[i].visibility = View.VISIBLE
        }

        rootView.imBtDelete.setOnClickListener {
            dbManager.deleteMedication(element.medicationId.toString())
            adapter.removeItem(element.medicationId)

            dismiss()
        }

        return rootView
    }
}