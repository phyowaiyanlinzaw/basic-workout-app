package me.phyo.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import me.phyo.a7minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        const val Myanmar_BMI_VIEW = "MYANMAR_BMI_VIEW"
        const val WESTERN_BMI_VIEW = "WESTERN_BMI_VIEW"
    }

    private var currentVisibleView: String = Myanmar_BMI_VIEW

    private var binding : ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.bmiToolBar)

        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        binding?.bmiToolBar?.setNavigationOnClickListener {
            onBackPressed()
        }

        showMyanmarBMIView()

        binding?.radioGroupBMI?.setOnCheckedChangeListener{_,radioID:Int ->
            if (radioID==R.id.westernBMIWay){
                showWesternBMIView()
            }else{
                showMyanmarBMIView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            calculateBMI()
        }

    }

    private fun showMyanmarBMIView(){
        currentVisibleView = Myanmar_BMI_VIEW

        binding?.tilWesternUnitHeight?.visibility = View.INVISIBLE
        binding?.tilWesternUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMyanmarUnitWeight?.visibility = View.VISIBLE
        binding?.tilMyanmarUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tilMyanmarUnitHeightInches?.visibility = View.VISIBLE

        binding?.etMyanmarUnitHeightFeet?.text!!.clear()
        binding?.etMyanmarUnitHeightInches?.text!!.clear()
        binding?.etMyanmarUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

    }

    private fun showWesternBMIView(){
        currentVisibleView = WESTERN_BMI_VIEW

        binding?.tilWesternUnitHeight?.visibility = View.VISIBLE
        binding?.tilWesternUnitWeight?.visibility = View.VISIBLE
        binding?.tilMyanmarUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMyanmarUnitHeightFeet?.visibility = View.INVISIBLE
        binding?.tilMyanmarUnitHeightInches?.visibility = View.INVISIBLE

        binding?.etWesternUnitHeight?.text!!.clear()
        binding?.etWesternUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE

    }

    private fun displayBMI(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue // Value is set to TextView
        binding?.tvBMIType?.text = bmiLabel // Label is set to TextView
        binding?.tvBMIDescription?.text = bmiDescription // Description is set to TextView
    }

    private fun validateMyanmarBMI():Boolean{
        var validator = true

        if(binding?.etMyanmarUnitWeight?.text.toString().isEmpty()){
            validator = false
        }else if (binding?.etMyanmarUnitHeightFeet?.text.toString().isEmpty()){
            validator = false
        }else if (binding?.etMyanmarUnitHeightInches?.text.toString().isEmpty()){
            validator = false
        }

        return validator
    }

    private fun validateWesternBMI():Boolean{
        var validator = true

        if(binding?.etWesternUnitWeight?.text.toString().isEmpty()){
            validator = false
        }else if (binding?.etWesternUnitHeight?.text.toString().isEmpty()){
            validator = false
        }

        return validator
    }

    private fun calculateBMI() {
        if (currentVisibleView== Myanmar_BMI_VIEW) {
            if (validateMyanmarBMI()) {
                var weightValue = binding?.etMyanmarUnitWeight?.text.toString().toFloat()
                var heightValue = (binding?.etMyanmarUnitHeightFeet?.text.toString()
                    .toFloat() * 12) + binding?.etMyanmarUnitHeightInches?.text.toString().toFloat()
                var bmi = (weightValue * 703) / (heightValue * heightValue)
                displayBMI(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            if (validateWesternBMI()){
                var weightValue = binding?.etWesternUnitWeight?.text.toString().toFloat()
                var heightValue = binding?.etWesternUnitHeight?.text.toString().toFloat()/100
                var bmi = weightValue / (heightValue*heightValue)
                displayBMI(bmi)
            }else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}