package com.ashutosh.meetup.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.adaptor.ChatAdaptor
import com.ashutosh.meetup.model.Chat
import com.ashutosh.meetup.model.HompageContent
import com.ashutosh.meetup.notification.*
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageActivity : AppCompatActivity() {
    //tried different method of initilazition
    private var userIdVisit:String?=""
    lateinit var imgMessageProfileImage:ImageView
    lateinit var txtMessageUserName:TextView
    lateinit var imgMessageSend:ImageView
    lateinit var imgMessageAttachImage:ImageView
    lateinit var etMessageText:EditText
    private var firebaseUser: FirebaseUser?=null
    private var chatsAdapter: ChatAdaptor?=null
    private var chatList= arrayListOf<Chat>()
    lateinit var messageRecyclerView: RecyclerView
    private var reference: DatabaseReference?=null
    private var notify=false
    private var apiService: ApiService?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val messageToolbar: Toolbar =findViewById(R.id.messageToolbar)
        setSupportActionBar(messageToolbar)
        supportActionBar!!.title=""
        //setting up back arrow () in toolbar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        messageToolbar.setNavigationOnClickListener {
            val intent= Intent(this@MessageActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        apiService= Clint.Client.getClint("https://fcm.googleapis.com/")!!.create(ApiService::class.java)
        intent=intent
       userIdVisit=intent.getStringExtra("visit_id")
        firebaseUser= FirebaseAuth.getInstance().currentUser
        txtMessageUserName=findViewById(R.id.txtMessageUserName)
        imgMessageProfileImage=findViewById(R.id.imgUser)
        etMessageText=findViewById(R.id.et_messageText)
        imgMessageAttachImage=findViewById(R.id.imgMessageAttachImage)
        messageRecyclerView=findViewById(R.id.messageRecyclerView)
        imgMessageSend=findViewById(R.id.imgMessageSend)
        messageRecyclerView.setHasFixedSize(true)
        var linearLayoutManager= LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd=true
        messageRecyclerView.layoutManager=linearLayoutManager
        //creating reference to database for displayin name ,pic and old messages
        reference= FirebaseDatabase.getInstance().reference.child("Users").child(userIdVisit!!)
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()) {
                    txtMessageUserName.text = p0.child("userName").value.toString()
                    Picasso.get().load(p0.child("picture").value.toString())
                        .into(imgMessageProfileImage)
                    //function to retrive old msg
                    retrieveMessage(
                        firebaseUser!!.uid,
                        userIdVisit!!,
                        p0.child("picture").value.toString()
                    )
                }
            }

        })
        imgMessageSend.setOnClickListener {
            notify=true
            val message=etMessageText.text.toString()
            if(message==""){
                Toast.makeText(this@MessageActivity,"Please Write A message....", Toast.LENGTH_LONG).show()

            }else{
                //function to send msg
               sendMessageToUser(firebaseUser!!.uid,userIdVisit,message)
            }
            etMessageText.setText("")
        }
        imgMessageAttachImage.setOnClickListener {
            notify=true
            val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"pick image"),438)
        }
        //function to see msg seen or not
        seenMessage(userIdVisit!!)
    }
    private fun sendMessageToUser(senderId: String, receiverId: String?, message: String) {
        val reference=FirebaseDatabase.getInstance().reference
        val messageKey=reference.push().key

        val messageHashMap=HashMap<String,Any?>()
        messageHashMap["sender"]=senderId
        messageHashMap["message"]=message
        messageHashMap["receiver"]=receiverId
        messageHashMap["isseen"]=false
        messageHashMap["url"]=""
        messageHashMap["messageId"]=messageKey
        reference.child("Chats").child(messageKey!!).setValue(messageHashMap).addOnCompleteListener {
                task ->
            if (task.isSuccessful){
                //creating reference to save receiver id for displaying all the people user hat chat with
                val chatListReference=FirebaseDatabase.getInstance().reference.child("newChatList").child(firebaseUser!!.uid).child(userIdVisit!!)

                chatListReference.addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (!p0.exists()){
                            chatListReference.child("id").setValue(userIdVisit)
                        }
                        val chatsListReceiverRef=FirebaseDatabase.getInstance().reference.child("newChatList").child(userIdVisit!!).child(firebaseUser!!.uid)
                        chatsListReceiverRef.child("id").setValue(firebaseUser!!.uid)
                    }

                })




            }

        }
        //implement the push notifications using fcm
               val userReference=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userReference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {


            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                val user = p0.child("userName").value.toString()
                var status = "offline"
                if (notify) {
                    //creating data base reference to check if receiver is online or not if offline send notification.
                    val userrReference =
                        FirebaseDatabase.getInstance().reference.child("Users").child(receiverId!!)
                    userrReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                status = snapshot.child("status").value.toString()
                                if (status == "offline") {
                                    //function to send notification
                                    sendNotification(receiverId, user, message)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })


                }
                    notify=false
            }

            }

        })



    }
    private fun sendNotification(receiverId: String?,userName:String?,message:String){
        val ref=FirebaseDatabase.getInstance().reference.child("Tokens")
        val query=ref.orderByChild("uid").startAt(receiverId).endAt(receiverId+"\ufaff")

        query.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (dataSnapshot in p0.children) {
                    val token = dataSnapshot.child("token").value.toString()
                    //creating data to be send
                    val data = Data(
                        firebaseUser!!.uid,
                        R.drawable.actress,
                        "$userName:$message",
                        "New Message",
                        userIdVisit!!
                    )
                    //setting data and token together
                    val sender = Sender(data!!, token)
                    //making api call for notification
                    apiService!!.sendNotification(sender)
                        .enqueue(object : Callback<MyResponse> {
                            override fun onFailure(call: Call<MyResponse>, t: Throwable) {

                            }

                            override fun onResponse(
                                call: Call<MyResponse>,
                                response: Response<MyResponse>
                            ) {
                                if (response.code() == 200) {
                                    if (response.body()!!.success != 1) {
                                        Toast.makeText(
                                            this@MessageActivity,
                                            "failed,nothing happened",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }

                        })

                }

            }

        })
    }
    //function to retrive old msg
   private fun retrieveMessage(senderId: String, receiverId: String?, receiverImageUrl: String?) {
        chatList=ArrayList()
        val reference=FirebaseDatabase.getInstance().reference.child("Chats")

        reference.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (chatList as ArrayList<Chat>).clear()
                for (snapshot in p0.children){
                   val chat=snapshot.getValue(Chat::class.java)
                   if (chat!!.receiver.equals(senderId)&& chat!!.sender.equals(receiverId)||chat!!.receiver.equals(receiverId)&&chat!!.sender.equals(senderId)){
                       (chatList as ArrayList<Chat>).add(chat)
                   }
                    chatsAdapter= ChatAdaptor(this@MessageActivity,chatList!!,receiverImageUrl!!)
                    messageRecyclerView.adapter=chatsAdapter
                }
            }

        })

    }
    //to handle result of image sending task
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==438 && resultCode== RESULT_OK && data!=null && data!!.data!=null){
            val progressBar= ProgressDialog(this)
            progressBar.setMessage("image is uploading,please wait.... ")
            progressBar.show()

            val fileUri=data.data
            val storageReference= FirebaseStorage.getInstance().reference.child("Chat Images")
            val ref =FirebaseDatabase.getInstance().reference
            val messageId=ref.push().key
            val filePath=storageReference.child("$messageId.jpg")

            var uploadTask: StorageTask<*>
            uploadTask=filePath.putFile(fileUri!!)

            uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{
                    task ->
                if (!task.isSuccessful){
                    task.exception?.let{
                        throw it
                    }
                }
                return@Continuation filePath.downloadUrl
            }).addOnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    val downloadUrl=task.result
                    val url=downloadUrl.toString()

                    val messageHashMap=HashMap<String,Any?>()
                    messageHashMap["sender"]=firebaseUser!!.uid
                    messageHashMap["message"]="sent you an image."
                    messageHashMap["receiver"]=userIdVisit
                    messageHashMap["isseen"]=false
                    messageHashMap["url"]=url
                    messageHashMap["messageId"]=messageId

                    ref.child("Chats").child(messageId!!).setValue(messageHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            progressBar.dismiss()
                            //for firebase fcm
                            val reference=FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
                            reference.addValueEventListener(object :ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {


                                }

                                override fun onDataChange(p0: DataSnapshot) {
                                   if (p0.exists()){
                                    val user = p0.child("userName").value.toString()
                                    var status = "offline"
                                    if (notify) {
                                        val userrReference =
                                            FirebaseDatabase.getInstance().reference.child("Users")
                                                .child(userIdVisit!!)
                                        userrReference.addValueEventListener(object :
                                            ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                status = snapshot.child("status").value.toString()
                                                if (status == "offline") {

                                                    sendNotification(
                                                        userIdVisit,
                                                        user,
                                                        "sent you an image."
                                                    )
                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                TODO("Not yet implemented")
                                            }

                                        })


                                    }
                                    notify = false
                                }
                                }

                            })

                        }
                    }

                }
            }
        }
    }
    //function to check msg seen or not
   var seenListner:ValueEventListener?=null
    private fun seenMessage(userId:String){
        val reference=FirebaseDatabase.getInstance().reference.child("Chats")
        seenListner=reference!!.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (dataSnapshot in p0.children){
                    val chat =dataSnapshot.getValue(Chat::class.java)
                    if (chat!!.receiver.equals(firebaseUser!!.uid)&& chat!!.sender.equals(userId)){
                        val hashMap=HashMap<String,Any>()
                        hashMap["isseen"]=true
                        dataSnapshot.ref.updateChildren(hashMap)
                    }
                }
            }

        })
    }
    //function to update status of user
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
        reference!!.removeEventListener(seenListner!!)
    }

}