package com.example.projectdraft1.fragments

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.projectdraft1.Medication
import com.example.projectdraft1.adapters.MedicineAdapter
import com.example.projectdraft1.databinding.FragmentMedicineBinding
import com.example.projectdraft1.db.DBManager
import com.example.projectdraft1.dialogs_fragment.MedInfoDialogFragment
import com.google.android.material.tabs.TabLayout

class FragmentMedicine : Fragment(), MedicineAdapter.Listener {
    lateinit var binding: FragmentMedicineBinding
    lateinit var dbManager: DBManager
    lateinit var alarmManager: AlarmManager
    private var isActive = true
    private val adapter = MedicineAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedicineBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        dbManager = DBManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initElements()
    }

    override fun onResume() {
        super.onResume()

        dbManager.openDB()
        fillAdapter(isActive)
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.closeDB()
    }

    private fun initElements() = with(binding){
        rcViewMedication.layoutManager = GridLayoutManager(context, 2)
        rcViewMedication.adapter = adapter

        initTabLayout()
    }

    private fun initTabLayout() = with(binding){
        tlMedication.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> isActive = true

                    1 -> isActive = false
                }

                fillAdapter(isActive)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                return
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                return
            }

        })
    }

    private fun fillAdapter(isActive: Boolean) = with(binding){
        val medicationList: ArrayList<Medication> = if (isActive) {
            dbManager.readActiveMedication()
        } else {
            dbManager.readNonActiveMedication()
        }

        adapter.setListAdapter(medicationList)

        if (medicationList.isEmpty()) {
            animMedicine.visibility = View.VISIBLE
            tvNotOnceMedication.visibility = View.VISIBLE
        } else {
            animMedicine.visibility = View.GONE
            tvNotOnceMedication.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMedicine()
    }

    override fun onClick(medication: Medication) {
        val dialog = MedInfoDialogFragment(
            dbManager,
            medication,
            adapter,
            requireActivity().applicationContext,
            alarmManager
        )

        dialog.show(childFragmentManager, "infoMedicationDialog")
    }
}