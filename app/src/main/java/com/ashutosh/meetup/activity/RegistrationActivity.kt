package com.ashutosh.meetup.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.ashutosh.meetup.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class RegistrationActivity : AppCompatActivity() {
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var etUserName: TextInputLayout
    private lateinit var etUserDob:TextInputLayout
    private lateinit var imgBtnDob:ImageButton
    private lateinit var btnRegister:Button
    private lateinit var actxtGender: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        etUserName=findViewById(R.id.etUserName)
        imgBtnDob=findViewById(R.id.imgbtnDob)
        actxtGender=findViewById(R.id.actxtGender)
        btnRegister=findViewById(R.id.btnRegister)
        etUserDob=findViewById(R.id.etUserDob)
        imgBtnDob.setOnClickListener {
            val formate = SimpleDateFormat("dd,mm,yyyy", Locale.US)
            val now= Calendar.getInstance()
            val datePicker= DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selectedDate= Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date=formate.format(selectedDate.time)
                etUserDob.editText!!.setText(date)
            },
                now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        val genderItems = listOf("Male", "Female", "Neutral")
        val genderAdapter = ArrayAdapter(this, R.layout.list_item, genderItems)
        actxtGender.setAdapter(genderAdapter)
        btnRegister.setOnClickListener {
            val name= etUserName.editText!!.text.toString().trim()
            val dob=etUserDob.editText!!.text.toString().trim()

            mDatabase = FirebaseDatabase.getInstance()
            mDatabaseReference = mDatabase.reference.child("Users")
            mAuth= FirebaseAuth.getInstance()
            val userId= mAuth.currentUser!!.uid
            val userNumber=mAuth.currentUser!!.phoneNumber

            if (name.isNotEmpty()&& dob.isNotEmpty()
                && actxtGender.text.isNotEmpty()) {
                mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val anotherChild = mDatabaseReference.child(userId)
                        anotherChild.child("userId").setValue(userId)
                        anotherChild.child("mobileNumber").setValue(userNumber)
                        anotherChild.child("userName").setValue(name)
                        anotherChild.child("userDob").setValue(dob)
                        anotherChild.child("picture").setValue("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b")
                        anotherChild.child("studentGender").setValue(actxtGender.text.toString())
                        anotherChild.child("status").setValue("offline")

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
              /*  mDatabase = FirebaseDatabase.getInstance()
                val users:DatabaseReference = mDatabase.reference.child("USERS")
                users.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val anotherChild1=users.child(userId)
                        anotherChild1.child("userType").setValue("Student")
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })*/

                startActivity(Intent(this@RegistrationActivity, InterestActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"All fields are required",Toast.LENGTH_SHORT).show()
            }
        }

    }
}