package com.example.projectdraft1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdraft1.databinding.FragmentTodayBinding

class FragmentToday : Fragment() {
    lateinit var binding: FragmentTodayBinding
    private val adapter = MedicationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements()

        adapter.addMedication(Medication(R.drawable.pill_1, "Лекарство 1", 1, "9:00"))
        adapter.addMedication(Medication(R.drawable.pill_2, "Лекарство 1", 1, "14:00"))
        adapter.addMedication(Medication(R.drawable.pill_3, "Лекарство 1", 1, "21:00"))
    }

    private fun initElements() = with(binding){
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentToday()
    }
}