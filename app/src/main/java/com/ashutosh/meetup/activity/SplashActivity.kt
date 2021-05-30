package com.ashutosh.meetup.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.ashutosh.meetup.R
import com.ashutosh.meetup.onBoarding.OnboardingActivity

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
            }else{
                startActivity(

                        Intent(
                                this@SplashActivity,
                                LoginActivity::class.java
                        )
                )

            }

        }, 500)
    }
    override fun onPause() {
        finish()
        super.onPause()
    }
}