package com.example.varaxinar_01_03

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvMetersField = findViewById<TextView>(R.id.tvMetersField)
        val tvResultField = findViewById<TextView>(R.id.tvResultField)
        val btnRegistration = findViewById<Button>(R.id.btnRegistration)

        // Get data from intent
        val meters = intent.getStringExtra("meters") ?: ""
        val result = intent.getStringExtra("result") ?: ""
        val apartmentType = intent.getStringExtra("apartmentType") ?: ""

        // Display data - replace labels with actual values
        tvMetersField.text = "$meters м ($apartmentType)"
        tvResultField.text = result

        // Navigation
        btnRegistration.setOnClickListener {
            // Go back to main (login) screen
            val intent = android.content.Intent(this, MainActivity::class.java)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or android.content.Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
