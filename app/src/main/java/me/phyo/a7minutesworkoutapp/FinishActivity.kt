package me.phyo.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.phyo.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import me.phyo.a7minutesworkoutapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.finishToolBar)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.finishToolBar?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.finishBtn?.setOnClickListener {
            finish()
        }

    }
}