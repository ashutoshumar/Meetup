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
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso

class MessageActivity : AppCompatActivity() {
    var userIdVisit:String?=""
    lateinit var imgMessageProfileImage:ImageView
    lateinit var txtMessageUserName:TextView
    lateinit var imgMessageSend:ImageView
    lateinit var imgMessageAttachImage:ImageView
    lateinit var etMessageText:EditText
    var firebaseUser: FirebaseUser?=null
    var chatsAdapter: ChatAdaptor?=null
    var chatList= arrayListOf<Chat>()
    lateinit var messageRecyclerView: RecyclerView
    var reference: DatabaseReference?=null
    var notify=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val messageToolbar: Toolbar =findViewById(R.id.messageToolbar)
        setSupportActionBar(messageToolbar)
        supportActionBar!!.title=""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        messageToolbar.setNavigationOnClickListener {
            val intent= Intent(this@MessageActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
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
        reference= FirebaseDatabase.getInstance().reference.child("Users").child(userIdVisit!!)
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                txtMessageUserName.text=p0.child("userName").value.toString()
                Picasso.get().load(p0.child("picture").value.toString()).into(imgMessageProfileImage)

                retrieveMessage(firebaseUser!!.uid,userIdVisit!!,p0.child("picture").value.toString())
            }

        })
        imgMessageSend.setOnClickListener {
            notify=true
            val message=etMessageText.text.toString()
            if(message==""){
                Toast.makeText(this@MessageActivity,"Please Write A message....", Toast.LENGTH_LONG).show()

            }else{
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


    }

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


                        }
                    }

                }
            }
        }
    }
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

    override fun onPause() {
        super.onPause()

        reference!!.removeEventListener(seenListner!!)
    }
}