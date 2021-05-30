package com.ashutosh.meetup.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ashutosh.meetup.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private lateinit var btnVerify: Button
    private lateinit var txtNumber: TextView
    private lateinit var txtResend: TextView
    private lateinit var etOtp: EditText
    private var mVerificationId: String? = null
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        val teacherNumber=intent.getStringExtra("mobileNumber")
        btnVerify=findViewById(R.id.btnVerify)
        txtNumber=findViewById(R.id.txtNumber)
        txtResend=findViewById(R.id.txtResend)
        etOtp=findViewById(R.id.etOtp)
        txtNumber.text = "+91 $teacherNumber"
        txtNumber.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        mAuth = FirebaseAuth.getInstance()
        sendVerificationCode(teacherNumber!!)

        btnVerify.setOnClickListener {
            val code: String = etOtp.text.toString().trim()

            verifyVerificationCode(code)

        }
    }
    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    val code = phoneAuthCredential.smsCode
                    if (code != null) {
                        etOtp.setText(code)
                        verifyVerificationCode(code)

                    }
                }
                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@OtpActivity, e.message, Toast.LENGTH_LONG).show()
                }
                override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(s, forceResendingToken)
                    mVerificationId = s
                }
            }

    private fun sendVerificationCode(mobile: String) {
        @Suppress("DEPRECATION")
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91$mobile",
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks
        )
    }
    private fun verifyVerificationCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("MEET UP")
        progressDialog.setMessage("We are processing, please wait")
        progressDialog.show()
        progressDialog.setCancelable(false)
        val number = intent.getStringExtra("mobileNumber")!!.toString()
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this@OtpActivity) { task ->
                    if (task.isSuccessful) {
                        val isNewUser = task.result?.additionalUserInfo!!.isNewUser
                        if (!isNewUser){
                            status(progressDialog)
                        }
                        else{
                            progressDialog.hide()
                            val intent=Intent(this,RegistrationActivity::class.java)
                            intent.putExtra("mobileNumber",number)
                            startActivity(intent)
                            finish()
                        }

                    }
                    else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            progressDialog.hide()
                            val message = "Invalid code entered..."
                            Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
    }

    private fun status(progress: ProgressDialog) {
        val number = intent.getStringExtra("mobileNumber")!!.toString()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("Users").child(uid)
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val intent = Intent(this@OtpActivity, InterestActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    progress.hide()
                    val intent =
                            Intent(this@OtpActivity, RegistrationActivity::class.java)
                    intent.putExtra("mobileNumber",number)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        uidRef.addListenerForSingleValueEvent(eventListener)
    }

}