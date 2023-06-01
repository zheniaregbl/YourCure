package com.example.projectdraft1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdraft1.databinding.MedicineItemBinding

class MedicineAdapter(private val listener: Listener): RecyclerView.Adapter<MedicineAdapter.MedicineHolder>() {
    private var medicationList = ArrayList<Medication>()

    class MedicineHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MedicineItemBinding.bind(item)

        @SuppressLint("SetTextI18n")
        fun bind(medication: Medication, listener: Listener) = with(binding){
            imageMedicine.setImageResource(medication.imageId)
            tvMedicineInfo.text = medication.title

            if (medication.days == 0) {
                tvMedicineInfo2.textSize = 18f
                tvMedicineInfo2.text = "∞"
            } else {
                if (medication.daysPass == 0){
                    tvMedicineInfo2.text = "0/${medication.days}"
                } else {
                    tvMedicineInfo2.text = "${medication.acceptDose / medication.daysPass}/${medication.days}"
                }
            }

            itemView.setOnClickListener {
                listener.onClick(medication)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medicine_item,
            parent,
            false)

        return MedicineHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        holder.bind(medicationList[position], listener)
    }

    override fun getItemCount(): Int {
        return medicationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListAdapter(list: ArrayList<Medication>){
        medicationList = list

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(id: Int){
        for (i in medicationList.indices){
            if (medicationList[i].medicationId == id){
                medicationList.removeAt(i)
                break
            }
        }

        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(medication: Medication)
    }
}