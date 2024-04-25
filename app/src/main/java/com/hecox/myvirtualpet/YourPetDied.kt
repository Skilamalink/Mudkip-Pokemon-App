package com.hecox.myvirtualpet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class YourPetDied : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_pet_died)

        var restartButton: Button = findViewById(R.id.restartGameButton)
        var rip: TextView = findViewById(R.id.RIPtext)
        val thePetNameForText = intent.getStringExtra("name")
        val causeOfDeath = intent.getStringExtra("causeOfDeath")

        restartButton.text = ("Play again?")
        rip.text = ("\nRIP\n" + thePetNameForText + " " + causeOfDeath)

        restartButton.setOnClickListener {
            val intent = Intent(this@YourPetDied, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


    }
}