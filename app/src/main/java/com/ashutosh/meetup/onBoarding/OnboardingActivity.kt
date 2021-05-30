package com.ashutosh.meetup.onBoarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ashutosh.meetup.activity.LoginActivity
import com.ashutosh.meetup.R
import com.ashutosh.meetup.activity.InterestActivity

class OnboardingActivity : AppCompatActivity() {
    lateinit var viewPager:ViewPager2
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var btnStart: Button
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences=getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_onboarding)
        viewPager=findViewById(R.id.viewPager)
        img1=findViewById(R.id.indicator1)
        img2=findViewById(R.id.indicator2)
        img3=findViewById(R.id.indicator3)
        btnStart=findViewById(R.id.btnStart)
        val fragmentList= arrayListOf<Fragment>(OnBoardingFragment1(),
        OnBoardingFragment2(),OnBoardingFragment3())
        val adapter=OnBoardingAdapter(fragmentList,
            supportFragmentManager,
            lifecycle
        )
        viewPager.adapter=adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (position==2){
                    btnStart.visibility= View.VISIBLE
                    btnStart.setOnClickListener {
                        sharedPreferences.edit().putBoolean("not first time",true).apply()
                        startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
                        finish()
                    }
                }else{
                    btnStart.visibility= View.GONE
                }
                when(viewPager.currentItem){
                    0->{
                        img1.setImageResource(R.drawable.indicator_circle)
                        img2.setImageResource(R.drawable.indicator_circle_blank)
                        img3.setImageResource(R.drawable.indicator_circle_blank)
                    }
                    1->{
                        img2.setImageResource(R.drawable.indicator_circle)
                        img1.setImageResource(R.drawable.indicator_circle_blank)
                        img3.setImageResource(R.drawable.indicator_circle_blank)
                    }
                    2->{
                        img3.setImageResource(R.drawable.indicator_circle)
                        img1.setImageResource(R.drawable.indicator_circle_blank)
                        img2.setImageResource(R.drawable.indicator_circle_blank)
                    }
                }
            }
        })
    }

}



