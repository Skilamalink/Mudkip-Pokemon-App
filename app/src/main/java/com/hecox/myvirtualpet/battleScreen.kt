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
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.random.Random


class battleScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle_screen)

        var pause = false

        var newExp = 0

        val name = intent.getStringExtra("name")
        var health = intent.getIntExtra("health", 100)
        var happiness = intent.getIntExtra("happiness", 100)
        var hunger = intent.getIntExtra("hunger", 100)
        var level = intent.getIntExtra("level", 1)
        var exp = intent.getIntExtra("exp", 0)

        var wurmpleHealth = 100


        var mudkip: ImageView = findViewById(R.id.mudkipBack)
        var enemyPoke: ImageView = findViewById(R.id.wurmple)
        var whichPoke = 1
        var whichPokeName = "Wurmple"


        var mudkipHpBar: ProgressBar = findViewById(R.id.mudkipHealth)
        var wurmpleBar: ProgressBar = findViewById(R.id.wurmpleHealth)

        var runButton: Button = findViewById(R.id.runButton)
        var biteButton: Button = findViewById(R.id.biteButton)
        var tackleButton: Button = findViewById(R.id.tackleButton)
        var rockSmashButton: Button = findViewById(R.id.rockSmashButton)
        var waterGunButton: Button = findViewById(R.id.waterGunButton)

        var playersTurn = true

        var infoText: TextView = findViewById(R.id.topInfoTextView)
        var mudkipText: TextView = findViewById(R.id.MudkipText)
        var wurmpleText: TextView = findViewById(R.id.wurmpleText)

        var causeOfDeath = "has died a glorious death"


//        enemyPoke.draw

        whichPoke = Random.nextInt(1, 4)

        when (whichPoke) {
            1 -> enemyPoke.setImageResource(R.drawable.wurmple)
            2 -> enemyPoke.setImageResource(R.drawable.zigzagoon)
            3 -> enemyPoke.setImageResource(R.drawable.poochyena)
        }

        when (whichPoke) {
            1 -> whichPokeName = "Wurmple"
            2 -> whichPokeName = "Zigzagoon"
            3 -> whichPokeName = "Poochyena"
        }

        infoText.text = ("A wild " + whichPokeName + " had appeared!")



        wurmpleText.text = (whichPokeName + " -  LV 0" + Random.nextInt(1, (level + 1)) + "\nhp")
        mudkipText.text = (name + " -  LV " + level + "\nhp")

//        fun ObjectAnimator.disableViewDuringAnimation(view: View) {
//
//            addListener(object : AnimatorListenerAdapter() {
//
//                override fun onAnimationStart(animator: Animator) {
//                    view.isEnabled = false
//                }
//
//                override fun onAnimationEnd(animation: Animator) {
//                    view.isEnabled = true
//                }
//            })
//        }


        fun mudkipAttack() {
            val moveMudkipAnimator = ObjectAnimator.ofFloat(mudkip, View.TRANSLATION_X, 0f, 100f, 0f)
            moveMudkipAnimator.duration = 500
            moveMudkipAnimator.start()
        }

        fun enemyAttack() {
            val enemyAttackAnimator = ObjectAnimator.ofFloat(enemyPoke, View.TRANSLATION_X, 0f, -100f, 0f)
            enemyAttackAnimator.duration = 500
            enemyAttackAnimator.start()
        }

        fun enemyDies() {
            val enemyAttackAnimator = ObjectAnimator.ofFloat(enemyPoke, View.ALPHA, 1f, .7f)
            enemyAttackAnimator.duration = 400
            enemyAttackAnimator.start()
        }


        val handler = Handler(Looper.getMainLooper())
        val progressRunnable = object : Runnable {
            override fun run() {


                mudkipHpBar.progress = health
                wurmpleBar.progress = wurmpleHealth

                if (exp >= 100) {
                    level += (exp / 100)
                    exp %= 100
                }

                if (pause == false) {

                    if (wurmpleHealth <= 0) {

                        handler.postDelayed({
                            enemyDies()
                        }, 100)

                        newExp = Random.nextInt(100, 300)
                        exp += newExp

                        infoText.text = ("You won!\ngained " + newExp + " Experience!")

                        handler.postDelayed({
                            val intent = Intent(this@battleScreen, game::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("health", health)
                            intent.putExtra("exp", exp)
                            intent.putExtra("happiness", happiness)
                            intent.putExtra("hunger", hunger)
                            intent.putExtra("level", level)

                            startActivity(intent)
                            finish()
                        }, 1500)

                        pause = true

                    } else if (health <= 0) {
                        val intent = Intent(this@battleScreen, YourPetDied::class.java)
                        intent.putExtra("name", name)
                        intent.putExtra("causeOfDeath", causeOfDeath)
                        pause = true
                        startActivity(intent)
                        finish()

                    } else if (playersTurn == true) {

                        biteButton.setOnClickListener {

                            mudkipAttack()
                            infoText.text = (name + " used bite")
                            wurmpleHealth -= Random.nextInt(20, 24)
                            handler.postDelayed({
                            playersTurn = false
                            }, 600)

                        }
                        tackleButton.setOnClickListener {
                            mudkipAttack()
                            infoText.text = (name + " used tackle")
                            wurmpleHealth -= Random.nextInt(15, 23)
                            handler.postDelayed({
                            playersTurn = false
                            }, 600)

                        }

                        rockSmashButton.setOnClickListener {
                            mudkipAttack()
                            infoText.text = (name + " used rock smash")
                            wurmpleHealth -= Random.nextInt(24, 56)
                            handler.postDelayed({
                            playersTurn = false
                            }, 600)

                        }

                        waterGunButton.setOnClickListener {
                            mudkipAttack()
                            infoText.text = (name + " used water gun")
                            wurmpleHealth -= Random.nextInt(24, 50)
                            handler.postDelayed({
                            playersTurn = false
                            }, 600)

                        }


                    } else {

                        //tackle then delay

                        playersTurn = true
                        handler.postDelayed({
                            infoText.text = whichPokeName + " used tackle"
                            health -= Random.nextInt(10, 30)
//                        infoText.text = ""

                        enemyAttack()
                        }, 600)


                    }
                    handler.postDelayed(this, 100)
                }
            }
        }


        handler.post(progressRunnable)





        runButton.setOnClickListener {
            val intent = Intent(this@battleScreen, game::class.java)
            intent.putExtra("name", name)
            intent.putExtra("health", health)
            intent.putExtra("exp", exp)
            intent.putExtra("happiness", happiness)
            intent.putExtra("hunger", hunger)
            intent.putExtra("level", level)
            startActivity(intent)
            pause = true
            finish()
        }
    }
}


//        battleButton.setOnClickListener {
//            val intent = Intent(this@game, battleScreen::class.java)
//            intent.putExtra("name", thePetNameForText)
//            intent.putExtra("health", health)
//            intent.putExtra("exp", exp)
//            intent.putExtra("happiness", happiness)
//            intent.putExtra("hunger", hunger)
//            intent.putExtra("level", level)
//            startActivity(intent)
//            finish()
//        }

