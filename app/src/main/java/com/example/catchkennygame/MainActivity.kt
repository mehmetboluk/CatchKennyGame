package com.example.catchkennygame

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var number = 0
    val viewArray = ArrayList<ImageView>()
    val handler = Handler()
    var runnable = Runnable {  }
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("com.example.catchkennygame", Context.MODE_PRIVATE)
        val highScoreFromSharedPreferences = sharedPreferences.getInt("highScore", 0)

        tvHighScore.text = "High Score: $highScoreFromSharedPreferences"


        viewArray.add(iv0)
        viewArray.add(iv1)
        viewArray.add(iv2)
        viewArray.add(iv3)
        viewArray.add(iv4)
        viewArray.add(iv5)
        viewArray.add(iv6)
        viewArray.add(iv7)
        viewArray.add(iv8)

        hideArray()



       object : CountDownTimer(15500,1000){
           override fun onTick(p0: Long) {
               tvTime.text = "Time : ${p0/1000}"



           }

           override fun onFinish() {
               tvTime.text = "Time: 0"

               val alert = AlertDialog.Builder(this@MainActivity)
               alert.setTitle("Game Over")
               alert.setMessage("Do you want to play?")
               alert.setPositiveButton("Yes"){dialog, which ->
                   val intent = intent
                   finish()
                   startActivity(intent)
               }
               alert.setNegativeButton("No"){dialog, which ->
                   Toast.makeText(this@MainActivity, "Game over!", Toast.LENGTH_SHORT).show()
               }
               alert.show()

               handler.removeCallbacks(runnable)
               for (i in viewArray){
                   i.visibility = View.INVISIBLE
               }

               if(number > highScoreFromSharedPreferences) {
                   sharedPreferences.edit().putInt("highScore", number).apply()

                   tvHighScore.text = "High Score: $highScoreFromSharedPreferences"
               }
           }
       }.start()
    }

    fun count(view : View){

        number++
        tvScore.text = "Your Score: $number"

    }

    fun hideArray(){

        runnable = object : Runnable{
            override fun run() {
                for (i in viewArray){
                    i.visibility = View.INVISIBLE
                }
                val random = Random()
                val index = random.nextInt(9)
                viewArray[index].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)
            }

        }
        handler.post(runnable)


    }



}