package com.hecox.myvirtualpet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import kotlin.random.Random

class NameYourPet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_your_pet)

        var enterButton: Button = findViewById(R.id.enterNameButton)
        var nameText: EditText = findViewById(R.id.petName)

        var egg: ImageView = findViewById(R.id.egg)
        var eggChance = 0

//        var exp = 0
//        var hunger = 100
//        var happiness = 100
//        var health = 30
//        var level = 1

         fun ObjectAnimator.disableViewDuringAnimation(view: View) {

            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animator: Animator) {
                    view.isEnabled = false
                }

                override fun onAnimationEnd(animation: Animator) {
                    view.isEnabled = true
                }
            })
        }


     fun eggMoves() {
        val animator = ObjectAnimator.ofFloat(egg, View.ROTATION, 4f, -4f, 0f)
        animator.duration = 500
        animator.start()
    }


        val handler = Handler(Looper.getMainLooper())
        val progressRunnable = object : Runnable {
            override fun run() {
                eggChance = Random.nextInt(1, 7)

                if (eggChance == 1) {
                    eggMoves()
                }


                handler.postDelayed(this, 500)
            }
        }

    handler.post(progressRunnable)



        enterButton.setOnClickListener {

            val intent = Intent(this@NameYourPet, game::class.java)
            var name = nameText.text.toString()
            intent.putExtra("name", name)
            startActivity(intent)
            finish()
        }
    }
}

