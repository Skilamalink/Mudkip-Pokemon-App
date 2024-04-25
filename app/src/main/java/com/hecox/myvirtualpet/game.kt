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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random


class game : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

//        var exp = 0
//        var hunger = 100
//        var happiness = 100
//        var health = 30
//        var level = 1

        var pause = false

//        val danger2 = getColor(R.color.danger)
//        val danger = resources.getColor(R.color.danger)
//        val safe = resources.getColor(R.color.safe)
//        val yellow = resources.getColor(R.color.yellow)

      var expBar: ProgressBar = findViewById(R.id.progressBarExp)
        var hungerBar: ProgressBar = findViewById(R.id.progressBarHunger)
        var happinessBar: ProgressBar = findViewById(R.id.progressBarHappiness)
        var healthBar: ProgressBar = findViewById(R.id.progressBarHealth)

      var battleButton: Button = findViewById(R.id.battleButton1)
        var feedButton: Button = findViewById(R.id.feedButton2)
        var petButton: Button = findViewById(R.id.petButton3)
        var healthButton: Button = findViewById(R.id.healButton)
        var pauseButton: Button = findViewById(R.id.pauseButton)

        var grayScreen: ImageView = findViewById(R.id.grayScreen)
        var mudkip: ImageView = findViewById(R.id.mudkip)
        var eyes: ImageView = findViewById(R.id.mudkipEyes)
        var bandages: ImageView = findViewById(R.id.bandages)
        var hand: ImageView = findViewById(R.id.hand)
        var hp1: ImageView = findViewById(R.id.heal1)
        var hp2: ImageView = findViewById(R.id.heal2)

        var textViewLevel: TextView = findViewById(R.id.textViewLevel)
        var petName: TextView = findViewById(R.id.petNameTextView)

        val name = intent.getStringExtra("name")
        var health = intent.getIntExtra("health", 100)
        var happiness = intent.getIntExtra("happiness", 100)
        var hunger = intent.getIntExtra("hunger", 100)
        var level = intent.getIntExtra("level", 1)
        var exp = intent.getIntExtra("exp", 0)

        var causeOfDeath = "has died a glorious death"

        var currentEyes = R.drawable.eyesnormal

        var eyeChance = 1

        petName.text = name



//        battleButton.setOnClickListener {
//            exp += 10
//            pause = true
//        }

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


        fun healFloat() {
            val animatorriseHp1 = ObjectAnimator.ofFloat(hp1, View.TRANSLATION_Y, 0f, -100f)
            animatorriseHp1.duration = 500
            animatorriseHp1.start()
            val animatordissapearHp1 = ObjectAnimator.ofFloat(hp1, View.ALPHA, 1f, 0.25f, 0f, 0f, 1f)
            animatordissapearHp1.duration = 1000
            animatordissapearHp1.start()
            val animatorriseHp2 = ObjectAnimator.ofFloat(hp2, View.TRANSLATION_Y, 0f, -100f)
            animatorriseHp2.duration = 600
            animatorriseHp2.start()
            val animatordissapearHp2 = ObjectAnimator.ofFloat(hp2, View.ALPHA, 1f, 0f, 0f, 1f)
            animatordissapearHp2.duration = 800
            animatordissapearHp2.start()
        }





        val handler = Handler(Looper.getMainLooper())
        val progressRunnable = object : Runnable {



                override fun run() {

                    //blink!!!
                    eyeChance = Random.nextInt(1, 10)

                    if (eyeChance == 1) {

                        eyes.setImageResource(R.drawable.eyesclosed)

                        handler.postDelayed({
                        eyes.setImageResource(currentEyes)
                        }, 300)

                    } else {
                        eyes.setImageResource(currentEyes)
                    }


                    expBar.progress = exp
                    healthBar.progress = health


                    if ((health < 40) || (happiness < 40) || (hunger < 40)) {
                        currentEyes = R.drawable.eyessad
                    } else {
                        currentEyes = R.drawable.eyesnormal
                    }


                    if (pause == false) {
                    if (hunger > 100) {
                        hunger = 100
                    }
                    if (happiness > 100) {
                        happiness = 100
                    }

                    if (health > 100) {
                        health = 100
                    }


                        if (health < 50) {
                            bandages.visibility = VISIBLE

                        } else {
                            bandages.visibility = GONE
                        }

                    if (hunger > 0 && happiness > 0) {
                        hungerBar.progress = hunger
                        happinessBar.progress = happiness

                        hunger -= 1
                        happiness -= 2

                        textViewLevel.text = ("Level " + level)



//                    if (progress2 < 50) {
//                        progressBar2.indeterminateTintMode = danger.toColor()
//                    }


                        handler.postDelayed(this, 200)
                    } else {
                        if (happiness == 0) {
                            causeOfDeath = "died of sadness"
                        } else if (hunger == 0) {
                            causeOfDeath = "staved to death"
                        }
                        val intent = Intent(this@game, YourPetDied::class.java)
                        intent.putExtra("name", name)
                        intent.putExtra("causeOfDeath", causeOfDeath)
                        startActivity(intent)
                        finish()
                    }


                }

            } // end of progress bar
        }


            handler.post(progressRunnable)


            battleButton.setOnClickListener {
                if (pause == false) {
                    val intent = Intent(this@game, battleScreen::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("health", health)
                    intent.putExtra("exp", exp)
                    intent.putExtra("happiness", happiness)
                    intent.putExtra("hunger", hunger)
                    intent.putExtra("level", level)
                    pause = true
                    startActivity(intent)
                    finish()
                }
            }

            feedButton.setOnClickListener {
                if (pause == false) {

                hunger += 10
                mudkip.setImageResource(R.drawable.mudkiphappy)
                handler.postDelayed({
                    mudkip.setImageResource(R.drawable.mudkipnormal)
                }, 300)
            }
            }
            petButton.setOnClickListener {
                if (pause == false) {
                happiness += 30

                hand.visibility = VISIBLE
                handler.postDelayed({
                    hand.visibility = GONE
                }, 400)
            }

            }
            healthButton.setOnClickListener {
                if (pause == false) {
                    healFloat()
                    health += 40
                    hp1.visibility = VISIBLE
                    hp2.visibility = VISIBLE
                    handler.postDelayed({
                        hp1.visibility = GONE
                        hp2.visibility = GONE
                    }, 400)
                }

        }


        pauseButton.setOnClickListener {
            pause = !pause
            if (pause == false) {
                grayScreen.visibility = GONE
                handler.post(progressRunnable)
            } else {
                grayScreen.visibility = VISIBLE
            }


        }



    }



}


