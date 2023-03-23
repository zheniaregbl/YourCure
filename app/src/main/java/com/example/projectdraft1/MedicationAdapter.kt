package com.example.projectdraft1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdraft1.databinding.MedicationItemBinding

class MedicationAdapter: RecyclerView.Adapter<MedicationAdapter.MedicationHolder>() {
    private val medicationList = ArrayList<Medication>()

    class MedicationHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = MedicationItemBinding.bind(item)

        fun bind(medication: Medication) = with(binding){
            imageMedication.setImageResource(medication.imageId)
            tvTitle.text = medication.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_item,
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