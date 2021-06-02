package com.ashutosh.meetup.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.databinding.ActivityHomeBinding
import com.ashutosh.meetup.databinding.ActivityInterestBinding
import com.ashutosh.meetup.fragment.ChatFragment
import com.ashutosh.meetup.fragment.HomeFragment
import com.ashutosh.meetup.fragment.SettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {
    private var previousMenuItem: MenuItem?=null
    private lateinit var navigationView:BottomNavigationView
    private lateinit var imgProfileHome:ImageView
    private lateinit var txtMainUserName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigationView=findViewById(R.id.navigationView)
        imgProfileHome=findViewById(R.id.imgProfileHome)
        txtMainUserName=findViewById(R.id.txtMainUserName)

        //function call to open home fragment
        openHome()
        //below codes are for generating tokens
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { Task ->
                if (Task.isSuccessful) {
                    val token = Task.result.toString()
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    val ref = FirebaseDatabase.getInstance().reference.child("Tokens").child(userId)
                    val tokenHashMap = HashMap<String, Any>()
                    tokenHashMap["uid"] = userId
                    tokenHashMap["token"] = token
                    ref.setValue(tokenHashMap).addOnCompleteListener { Task ->
                        if (Task.isSuccessful) {
                            Log.d(ContentValues.TAG, "Refreshed token: $token ");
                        } else {
                            Log.d(ContentValues.TAG, "Refreshed token: ");
                        }

                    }
                } else {
                    val token = " "
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    val ref = FirebaseDatabase.getInstance().reference.child("Tokens")
                    val tokenHashMap = HashMap<String, Any>()
                    tokenHashMap["uid"] = userId
                    tokenHashMap["token"] = token
                    ref.setValue(tokenHashMap).addOnCompleteListener { Task ->
                        if (Task.isSuccessful) {
                            Log.d(ContentValues.TAG, "Refreshed token: $token ");
                        } else {
                            Log.d(ContentValues.TAG, "Refreshed token: ");
                        }

                    }
                }
            }
        }
        //for loading name and images
        val ref=FirebaseDatabase.getInstance().reference.child("Users").child(user!!.uid)
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    val username=snapshot.child("userName").value.toString()
                    val pic=snapshot.child("picture").value.toString()
                    txtMainUserName.text=username
                    Picasso.get().load(pic).placeholder(R.drawable.white).into(imgProfileHome)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //below codes are for buttom nav view
       navigationView.setOnNavigationItemSelectedListener {
           //to mark selected fragment
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
           //for traversing the fragments
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
    //handeling back presses
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
    //updating user online offline status

    private fun updateStatus(status:String){
        val firebaseUser=FirebaseAuth.getInstance().currentUser
        val ref= FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        val hashmap=HashMap<String,Any>()
        hashmap["status"]=status
        ref!!.updateChildren(hashmap)
    }
    override fun onStart() {
        super.onStart()
        updateStatus("online")
    }
    override fun onResume() {
        super.onResume()
        updateStatus("online")
    }

    override fun onPause() {
        super.onPause()
        updateStatus("offline")
    }

}