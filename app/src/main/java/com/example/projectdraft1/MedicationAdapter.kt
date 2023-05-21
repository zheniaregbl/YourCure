package com.example.projectdraft1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdraft1.databinding.MedicationItem2Binding

class MedicationAdapter(val listener: Listener): RecyclerView.Adapter<MedicationAdapter.MedicationHolder>() {
    private var medicationDoseList = ArrayList<MedicationDose>()

    class MedicationHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MedicationItem2Binding.bind(item)
        var itemDose: MedicationDose? = null

        @SuppressLint("SetTextI18n")
        fun bind(dose: MedicationDose, listener: Listener) = with(binding){
            imagePill.setImageResource(dose.imageId)
            tvNamePill.text = dose.title
            tvDosePill.text = "${dose.amount} ${dose.stringDose}"
            tvDoseTime.text = dose.time
            itemDose = dose

            itemView.setOnClickListener {
                listener.onClick(itemDose!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medication_item2,
            parent,
            false)

        return MedicationHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationHolder, position: Int) {
        holder.bind(medicationDoseList[position], listener)
    }

    override fun getItemCount(): Int {
        return medicationDoseList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMedication(medication: MedicationDose){
        medicationDoseList.add(medication)
        notifyDataSetChanged()
    }

    fun getListAdapter(): ArrayList<MedicationDose>{
        return medicationDoseList
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListAdapter(list: ArrayList<MedicationDose>){
        medicationDoseList = list
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(medicationDose: MedicationDose)
    }
}