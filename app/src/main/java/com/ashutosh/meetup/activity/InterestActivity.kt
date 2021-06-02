package com.ashutosh.meetup.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.adaptor.BottomSheetContentAdaptor
import com.ashutosh.meetup.databinding.ActivityInterestBinding
import com.ashutosh.meetup.model.BottomSheetContent
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

class InterestActivity : AppCompatActivity() {

    private lateinit var buttonSheetContentAdapter: BottomSheetContentAdaptor
    private val bottom_sheet_list= arrayListOf<BottomSheetContent>()
    private lateinit var txtName:TextView
    private lateinit var userId: String
    private lateinit var mAuth:FirebaseAuth
   private lateinit var mDatabaseReference: DatabaseReference
   private lateinit var mstorageReference: StorageReference
   private lateinit var binding:ActivityInterestBinding
   private val requestCODE=111
    private var imageUrl: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //trying view binding
        binding= ActivityInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        txtName=findViewById(R.id.txtName)
        mAuth= FirebaseAuth.getInstance()
        userId=mAuth.currentUser!!.uid
        //creating database reference for displaying name and profile pic
        mDatabaseReference=FirebaseDatabase.getInstance().reference.child("Users").child(userId)
        mstorageReference=FirebaseStorage.getInstance().reference.child("userImage")
        mDatabaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    val username=snapshot.child("userName").value.toString()
                    val pic=snapshot.child("picture").value.toString()
                    txtName.text=username
                    Picasso.get().load(pic).placeholder(R.drawable.white).into(binding.imgProfile)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding.imgProfile.setOnClickListener {
            //funftion to pic image from gallery
            pickImage()
        }

        binding.cardView2.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
            findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.football,"FOOTBALL","Sports")
            val sheet_list2=BottomSheetContent(R.drawable.cricket,"CRICKET","Sports")
            val sheet_list3=BottomSheetContent(R.drawable.polo,"POLO","Sports")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        }
        binding.cardView3.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                    this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                    findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.stevejobs,"Steve Jobs","Entrepreneur")
            val sheet_list2=BottomSheetContent(R.drawable.elonmusk,"Elon Musk","Entrepreneur")
            val sheet_list3=BottomSheetContent(R.drawable.azimpremzi,"Azim Premji","Entrepreneur")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView4.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                    this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                    findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.inc,"Congress","Political Party")
            val sheet_list2=BottomSheetContent(R.drawable.bjp,"Bjp","Political Party")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView5.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                    this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                    findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.obama,"Barak Obama","Politician")
            val sheet_list2=BottomSheetContent(R.drawable.modiji,"Narendra Modi","Politician")
            val sheet_list3=BottomSheetContent(R.drawable.rahulgandhi,"Rahul Gandhi","Politician")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView6.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.harrypotter,"Harry Potter","Movies")
            val sheet_list2=BottomSheetContent(R.drawable.inception,"Inception","Movies")
            val sheet_list3=BottomSheetContent(R.drawable.endgame,"Avenger Endgame","Movies")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView7.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.blackpink,"Blackpink","Singer")
            val sheet_list2=BottomSheetContent(R.drawable.maluma,"Maluma","Singer")
            val sheet_list3=BottomSheetContent(R.drawable.taylorswift,"Taylor Swift","Singer")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView8.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.srk,"Srk","Actor")
            val sheet_list2=BottomSheetContent(R.drawable.danialcraig,"Danial Craig","Actor")
            val sheet_list3=BottomSheetContent(R.drawable.jamiedornan,"Jamie Dornan","Actor")
            val sheet_list4=BottomSheetContent(R.drawable.bradpitt,"Brad Pitt","Actor")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            bottom_sheet_list.add(sheet_list4)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView9.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.dakotajohanson,"Dakota Johanson","Actress")
            val sheet_list2=BottomSheetContent(R.drawable.priyankachopra,"Priyanka Chopra","Actress")
            val sheet_list3=BottomSheetContent(R.drawable.galgadot,"Gal Gadot","Actress")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView10.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.travelling,"Travelling","Hobbies")
            val sheet_list2=BottomSheetContent(R.drawable.watchingtv,"Watching Tv","Hobbies")
            val sheet_list3=BottomSheetContent(R.drawable.reading,"Reading","Hobbies")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView11.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.switzerland,"Switzerland","Destination")
            val sheet_list2=BottomSheetContent(R.drawable.norway,"Norway","Destination")
            val sheet_list3=BottomSheetContent(R.drawable.itly,"Itly","Destination")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView12.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.police,"Police","Career")
            val sheet_list2=BottomSheetContent(R.drawable.engineering,"Engineering","Career")
            val sheet_list3=BottomSheetContent(R.drawable.fashiondesigner,"Fashion Designer","Career")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
        binding.cardView13.setOnClickListener {
            val bottomSheetDialog=BottomSheetDialog(
                this,R.style.BottomSheetDialogTheme
            )
            val bottomSheetView=LayoutInflater.from(applicationContext).inflate(R.layout.buttomsheet,
                findViewById(R.id.buttomsheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            bottom_sheet_list.clear()
            val recyclerView=bottomSheetView.findViewById<RecyclerView>(R.id.bottom_sheet_recyclerView)
            val sheet_list1=BottomSheetContent(R.drawable.ai,"Artificial Intelligence","Technology")
            val sheet_list2=BottomSheetContent(R.drawable.blockchain,"Blockchain","Technology")
            val sheet_list3=BottomSheetContent(R.drawable.cryptography,"Cryptography","Technology")
            bottom_sheet_list.add(sheet_list1)
            bottom_sheet_list.add(sheet_list2)
            bottom_sheet_list.add(sheet_list3)
            buttonSheetContentAdapter= BottomSheetContentAdaptor(this,bottom_sheet_list)
            recyclerView.adapter=buttonSheetContentAdapter
            recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }
       binding.btnNext.setOnClickListener {
           startActivity(Intent(this,HomeActivity::class.java))
           finish()
       }
    }

//fun to pick image
    private fun pickImage() {
        val intent= Intent()
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,requestCODE)
    }
    //fun to return result of aboue intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==requestCODE&&resultCode== Activity.RESULT_OK&&data!!.data!=null){
            imageUrl=data.data
            Toast.makeText(this,"uploading,,,,,", Toast.LENGTH_LONG).show()
            uploadImageToDatabase()
        }
    }
    //fun to upload image in database
    private fun uploadImageToDatabase() {
        val progressBar= ProgressDialog(this)
        progressBar.setMessage("image is uploading,please wait.....")
        progressBar.show()
        if(imageUrl!=null){
            val fileRef=mstorageReference!!.child(System.currentTimeMillis().toString()+".jpg")

            var uploadTask: StorageTask<*>
            uploadTask=fileRef.putFile(imageUrl!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                    task ->
                if (!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }

                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val downloadUrl=task.result
                    val url=downloadUrl.toString()


                    val mapProfileImage=HashMap<String,Any>()
                    mapProfileImage["picture"]=url
                    mDatabaseReference!!.updateChildren(mapProfileImage)

                }
                progressBar.dismiss()
            }
        }
    }
}