package com.example.projectdraft1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectdraft1.MeasureValue
import com.example.projectdraft1.R
import com.example.projectdraft1.databinding.FragmentMeasurementBinding

class FragmentMeasurement : Fragment() {
    lateinit var binding: FragmentMeasurementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeasurementBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMeasurement()
    }
}