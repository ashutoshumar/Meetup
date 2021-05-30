package com.ashutosh.meetup.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ashutosh.meetup.R
import com.ashutosh.meetup.databinding.ActivityHomeBinding
import com.ashutosh.meetup.databinding.ActivityInterestBinding
import com.ashutosh.meetup.fragment.ChatFragment
import com.ashutosh.meetup.fragment.HomeFragment
import com.ashutosh.meetup.fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var previousMenuItem: MenuItem?=null
    private lateinit var navigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
           navigationView=findViewById(R.id.navigationView)
        openHome()
       navigationView.setOnNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.menuHomepage->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrame,HomeFragment())
                        .commit()

                }
                R.id.menuChat->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrame, ChatFragment())
                        .commit()


                }
                R.id.menuSetting->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrame, SettingFragment())
                        .commit()

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
    fun openHome(){
        val fragment=HomeFragment()
        val transection=supportFragmentManager.beginTransaction()
        transection.replace(R.id.mainFrame,fragment)
        transection.commit()

    }
    override fun onBackPressed() {

        val frag=supportFragmentManager.findFragmentById(R.id.mainFrame)
        when(frag){
            !is HomeFragment ->{
                openHome()
                navigationView.setSelectedItemId(R.id.menuHomepage)
            }
            else-> super.onBackPressed()
        }
    }
}