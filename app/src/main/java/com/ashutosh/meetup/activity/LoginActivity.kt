package com.ashutosh.meetup.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import com.ashutosh.meetup.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var etMobileNumber: TextInputLayout
    private lateinit var btnNext: Button
    private lateinit var loginProgressBar:ProgressBar
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth= FirebaseAuth.getInstance()
        etMobileNumber=findViewById(R.id.etMobileNumber)
        btnNext=findViewById(R.id.btn_get_otp)
        loginProgressBar=findViewById(R.id.loginProgressBar)
        btnNext.setOnClickListener {
            val number= etMobileNumber.editText!!.text.toString().trim()
            if(number.isEmpty()||number.length!=10) {
                Toast.makeText(this,"Enter Valid Number", Toast.LENGTH_SHORT).show()
            }
            else
            {
                btnNext.visibility = View.GONE
                loginProgressBar.visibility = View.VISIBLE
                //sending for otp verification
                val intent= Intent(this, OtpActivity::class.java)
                intent.putExtra("mobileNumber", number)
                startActivity(intent)
                loginProgressBar.visibility = View.GONE
                btnNext.visibility= View.VISIBLE
            }}
    }
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }


}