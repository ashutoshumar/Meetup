package com.ashutosh.meetup.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ashutosh.meetup.R
import com.ashutosh.meetup.activity.InterestActivity
import com.ashutosh.meetup.activity.LoginActivity
import com.ashutosh.meetup.activity.MessageActivity
import com.ashutosh.meetup.activity.SplashActivity
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*


class SettingFragment : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var etUserName_set: TextInputLayout
    private lateinit var btnmoresetting: Button
    private lateinit var logout: Button
    private lateinit var update1: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        etUserName_set = view.findViewById(R.id.etUserName_set)
        btnmoresetting = view.findViewById(R.id.btnmoresetting)
        logout = view.findViewById(R.id.btnlogout)
        update1 = view.findViewById(R.id.btnUpdate1)



        val mUser = FirebaseAuth.getInstance().currentUser
        val mDatabase = FirebaseDatabase.getInstance().reference.child("Users").child(mUser!!.uid)
        update1.setOnClickListener {
            val name_set = etUserName_set.editText!!.text.toString().trim()
            if (name_set.isEmpty()) {
                Toast.makeText(
                    activity as Context,
                    "Empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                mDatabase.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val user = HashMap<String, Any?>()
                            user["userName"] = name_set
                            mDatabase.updateChildren(user).addOnCompleteListener { Task ->
                                if (Task.isSuccessful) {
                                    etUserName_set.editText!!.setText("")
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }
        }

        btnmoresetting.setOnClickListener {
            startActivity(Intent(activity as Context,InterestActivity::class.java))
            activity!!.finish()

        }
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

            return view
        }

    }
