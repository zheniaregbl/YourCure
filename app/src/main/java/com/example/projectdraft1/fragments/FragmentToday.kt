package com.example.projectdraft1.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdraft1.MedicationAdapter
import com.example.projectdraft1.activities.EditorActivity
import com.example.projectdraft1.databinding.FragmentTodayBinding
import com.example.projectdraft1.db.DBManager

class FragmentToday : Fragment() {
    lateinit var binding: FragmentTodayBinding
    private val adapter = MedicationAdapter()
    lateinit var dbManager: DBManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dbManager = DBManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements()
    }

    override fun onResume() {
        super.onResume()

        dbManager.openDB()
        fillAdapter()
        Log.d("tag123", "onResume")
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
        adapter.setListAdapter(dbManager.readDataDB())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentToday()
    }
}