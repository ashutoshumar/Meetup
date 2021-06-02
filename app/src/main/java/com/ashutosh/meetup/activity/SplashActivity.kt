package com.ashutosh.meetup.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.ashutosh.meetup.R
import com.ashutosh.meetup.onBoarding.OnboardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_splash)
        val not_first_time=sharedPreferences.getBoolean("not first time",false)

        Handler().postDelayed({
            if (not_first_time == false){
                startActivity(

                        Intent(
                                this@SplashActivity,
                                OnboardingActivity::class.java
                        )
                )
                finish()
            }else{

                  updateUI()

            }

        }, 500)
    }
    override fun onPause() {
        finish()
        super.onPause()
    }
  //checking already logged or not
    fun updateUI(){
        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){

            Toast.makeText(this,"Logged In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        else
        {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()

        }
    }
}