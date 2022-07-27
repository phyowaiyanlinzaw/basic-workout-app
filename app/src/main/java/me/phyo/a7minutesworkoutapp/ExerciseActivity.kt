package me.phyo.a7minutesworkoutapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.phyo.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExerciseBinding? = null
    private var restTimer : CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercise = -1
    private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null
    private var exerciseAdapter : ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.exerciseToolBar)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.exerciseToolBar?.setNavigationOnClickListener {
            onBackPressed()
        }

        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExercisesList()

        setUpRestTimer()
        setUpAdapter()
    }

    private fun setUpAdapter(){
        binding?.rvExercise?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExercise?.adapter = exerciseAdapter
    }

    private fun setUpRestTimer(){

        try {
            val soundUri = Uri.parse("android.resource://me.phyo.a7minutesworkoutapp/"+R.raw.yoo)
            player = MediaPlayer.create(applicationContext,soundUri)
            player?.isLooping=false
            player?.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        binding?.FlRestview?.visibility = View.VISIBLE
        binding?.textView?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.ExerciseFL?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercise+1].getName()

        setRestProgress()
    }

    private fun setUpExerciseTimer(){
        binding?.FlRestview?.visibility= View.INVISIBLE
        binding?.textView?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.ExerciseFL?.visibility=View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercise].getName())

        binding?.ivImage?.setImageResource(exerciseList!![currentExercise].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercise].getName()

        setExerciseProgress()
    }

    private fun setRestProgress(){

        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(11000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 11 - restProgress
                binding?.tvTimer?.text = (11-restProgress).toString()
            }

            override fun onFinish() {
                currentExercise++
                exerciseList!![currentExercise].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()
                setUpExerciseTimer()
            }

        }.start()
    }

    private fun setExerciseProgress(){
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercise].setIsCompleted(true)
                exerciseAdapter?.notifyDataSetChanged()
                if (currentExercise<exerciseList?.size!! - 1){
                    setUpRestTimer()
                }else{
                var intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    finish()
                    startActivity(intent)
            }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        if (tts!=null){
            tts?.stop()
            tts?.shutdown()
        }

        if (player!=null){
            player?.stop()
        }

        binding=null
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            var result = tts?.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this@ExerciseActivity, "Language is Not Supported.", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            Toast.makeText(this@ExerciseActivity, "Initialization Failed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}