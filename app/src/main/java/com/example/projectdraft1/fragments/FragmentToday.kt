package com.example.projectdraft1.fragments

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdraft1.MedicationAdapter
import com.example.projectdraft1.MedicationDose
import com.example.projectdraft1.ScheduleAlarm
import com.example.projectdraft1.activities.EditorActivity
import com.example.projectdraft1.databinding.FragmentTodayBinding
import com.example.projectdraft1.db.DBManager
import com.example.projectdraft1.dialogs_fragment.AcceptDoseDialogFragment

class FragmentToday : Fragment(), MedicationAdapter.Listener {
    lateinit var binding: FragmentTodayBinding
    lateinit var dbManager: DBManager
    private val adapter = MedicationAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater)

        return binding.root
    }

    override fun onAttach(_context: Context) {
        super.onAttach(_context)
        dbManager = DBManager(_context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements()
    }

    override fun onResume() {
        super.onResume()

        dbManager.openDB()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDB()
    }

    private fun initElements() = with(binding){
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter

        btAddMedication.setOnClickListener {
            val intent = Intent(context, EditorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fillAdapter(){
        adapter.setListAdapter(dbManager.readActiveDose())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentToday()
    }

    override fun onClick(medicationDose: MedicationDose) {
        val dialog = AcceptDoseDialogFragment(dbManager, medicationDose)

        dialog.show(childFragmentManager, "acceptDoseDialog")
    }
}