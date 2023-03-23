package com.example.projectdraft1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectdraft1.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = MedicationAdapter()
    private var editLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                adapter.addMedication(it.data?.getSerializableExtra("medication") as Medication)
            }
        }
    }

    private fun init() = with(binding){
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter

        btAdd.setOnClickListener {
            editLauncher?.launch(Intent(this@MainActivity, EditorActivity::class.java))
        }
    }
}