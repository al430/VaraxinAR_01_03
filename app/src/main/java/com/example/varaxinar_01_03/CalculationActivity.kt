package com.example.varaxinar_01_03

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CalculationActivity : AppCompatActivity() {

    private lateinit var spinnerApartmentType: AdapterView<*>
    private lateinit var etMeters: TextInputEditText
    private lateinit var tvResultField: TextView
    private var selectedApartmentType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)

        val ivBack = findViewById<android.widget.ImageView>(R.id.ivBack)
        val tvBackText = findViewById<TextView>(R.id.tvBackText)
        val spinner = findViewById<android.widget.Spinner>(R.id.spinnerApartmentType)
        spinnerApartmentType = spinner
        etMeters = findViewById(R.id.etMeters)
        val btnCalculate = findViewById<android.widget.Button>(R.id.btnCalculate)
        tvResultField = findViewById(R.id.tvResultField)

        val apartmentTypes = arrayOf(
            "1. 1-о комнатная квартира",
            "2. 2-х комнатная квартира",
            "3. 3-х комнатная квартира",
            "4. Студия"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, apartmentTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedApartmentType = apartmentTypes[position].substring(3) // Remove "1. ", "2. ", etc.
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedApartmentType = ""
            }
        }

        ivBack.setOnClickListener {
            finish()
        }

        tvBackText.setOnClickListener {
            finish()
        }

        btnCalculate.setOnClickListener {
            calculateResult()
        }
    }

    private fun calculateResult() {
        val metersText = etMeters.text.toString()

        if (metersText.isEmpty()) {
            Toast.makeText(this, "Введите количество метров", Toast.LENGTH_SHORT).show()
            return
        }

        val meters = metersText.toDoubleOrNull()
        if (meters == null || meters <= 0) {
            Toast.makeText(this, "Введите корректное количество метров", Toast.LENGTH_SHORT).show()
            return
        }

        val meterCost = 100.0 // Base cost per meter in thousands of rubles
        val result: Double

        result = when (selectedApartmentType) {
            "1-о комнатная квартира" -> meterCost * meters * 1.4
            "2-х комнатная квартира" -> meterCost * meters
            "3-х комнатная квартира" -> meterCost * meters * 0.8
            "Студия" -> meterCost * meters * 1.1
            else -> {
                Toast.makeText(this, "Выберите тип квартиры", Toast.LENGTH_SHORT).show()
                return
            }
        }

        val resultText = String.format("%.2f тыс. руб.", result)

        // Display result in the result field
        tvResultField.text = resultText

        // Navigate to ResultActivity and pass data
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("meters", metersText)
        intent.putExtra("result", resultText)
        intent.putExtra("apartmentType", selectedApartmentType)
        startActivity(intent)
    }
}
