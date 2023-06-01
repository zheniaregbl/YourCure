package com.example.projectdraft1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.projectdraft1.R
import com.example.projectdraft1.SpinnerMedication
import kotlinx.android.synthetic.main.spinner_item.view.*

class MedicationArrayAdapter(context: Context, medicationList: List<SpinnerMedication>) : ArrayAdapter<SpinnerMedication>(context, 0, medicationList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup) : View{
        val medication = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        view.imMedication.setImageResource(medication!!.image)
        view.tvMedicationText.text = medication.name

        return view
    }
}