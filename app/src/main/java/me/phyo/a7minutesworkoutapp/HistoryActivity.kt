package me.phyo.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.phyo.a7minutesworkoutapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private var binding : ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.historyToolBar)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.historyToolBar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}