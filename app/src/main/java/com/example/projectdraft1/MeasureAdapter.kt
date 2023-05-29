package com.example.projectdraft1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectdraft1.databinding.MeasureItemBinding
import com.example.projectdraft1.db.DBNameClass

class MeasureAdapter(private val type: String): RecyclerView.Adapter<MeasureAdapter.MeasureHolder>() {
    private var measureValueList = ArrayList<MeasureValue>()

    class MeasureHolder(item: View, private val type: String): RecyclerView.ViewHolder(item) {
        private val binding = MeasureItemBinding.bind(item)

        @SuppressLint("SetTextI18n")
        fun bind(measureValue: MeasureValue) = with(binding){
            if (measureValue.type == type) {
                when(type) {
                    DBNameClass.TYPE_PRESSURE -> {
                        tvMeasureValue.text = "${measureValue.firstValue.toInt()}/" +
                                "${measureValue.secondValue}"
                    }

                    DBNameClass.TYPE_WEIGHT -> {
                        tvMeasureValue.text = "${measureValue.firstValue}"
                    }

                    DBNameClass.TYPE_TEMPERATURE -> {
                        tvMeasureValue.text = "${measureValue.firstValue}"
                    }
                }

                tvMeasureDate.text = measureValue.dateMeasure
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MeasureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.measure_item,
            parent,
            false)

        return MeasureAdapter.MeasureHolder(view, type)
    }

    override fun onBindViewHolder(holder: MeasureAdapter.MeasureHolder, position: Int) {
        holder.bind(measureValueList[position])
    }

    override fun getItemCount(): Int {
        return measureValueList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMeasure(measureValue: MeasureValue){
        measureValueList.add(measureValue)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListAdapter(list: ArrayList<MeasureValue>){
        measureValueList = list

        notifyDataSetChanged()
    }
}