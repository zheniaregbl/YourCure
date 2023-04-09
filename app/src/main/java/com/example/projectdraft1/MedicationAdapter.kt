package com.example.projectdraft1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdraft1.databinding.MedicationItem2Binding

class MedicationAdapter: RecyclerView.Adapter<MedicationAdapter.MedicationHolder>() {
    private val medicationList = ArrayList<Medication>()

    class MedicationHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MedicationItem2Binding.bind(item)

        @SuppressLint("SetTextI18n")
        fun bind(medication: Medication) = with(binding){
            imagePill.setImageResource(medication.imageId)
            tvNamePill.text = medication.title
            tvDosePill.text = "${medication.dose} таблетка(-и/-ок)"
            tvDoseTime.text = medication.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_item2,
            parent,
            false)

        return MedicationHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationHolder, position: Int) {
        holder.bind(medicationList[position])
    }

    override fun getItemCount(): Int {
        return medicationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMedication(medication: Medication){
        medicationList.add(medication)
        notifyDataSetChanged()
    }
}