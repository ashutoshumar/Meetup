package com.ashutosh.meetup.adaptor

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.activity.ViewFullImageActivity
import com.ashutosh.meetup.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ChatAdaptor(val context: Context, val chatList: List<Chat>, val imageUrl:String):
    RecyclerView.Adapter<ChatAdaptor.chatViewHolder>() {

     var firebaseUser: FirebaseUser?= FirebaseAuth.getInstance().currentUser
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {
         if (viewType==1){
             val view= LayoutInflater.from(context).inflate(R.layout.right_chat,parent,false)
             return chatViewHolder(view)
         }else{
             val view= LayoutInflater.from(context).inflate(R.layout.left_chat,parent,false)
             return chatViewHolder(view)
         }
     }

     override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
         val chat: Chat = chatList[position]

         Picasso.get().load(imageUrl).into(holder.imgMessageItemLeftProfile)
         //image message
         if (chat.message.equals("sent you an image.") && !chat.url.equals("")) {
             //image message -right side
             if (chat.sender.equals(firebaseUser!!.uid)) {
                 holder.txtShowTextMessage!!.visibility = View.GONE
                 holder.rightImageView!!.visibility = View.VISIBLE
                 Picasso.get().load(chat.url).into(holder.rightImageView)

                 holder.rightImageView!!.setOnClickListener {
                     val options= arrayOf<CharSequence>(
                         "view full image",
                         "delete image",
                         " cancel"
                     )
                     var builder: AlertDialog.Builder= AlertDialog.Builder(holder.itemView.context)
                     builder.setTitle("What do you want?")
                     builder.setItems(options, DialogInterface.OnClickListener({
                             dialog, which ->
                         if (which==0){
                             val intent= Intent(context,
                                 ViewFullImageActivity::class.java)
                             intent.putExtra("url",chat.url)
                             context.startActivity(intent)
                         }
                         else if(which==1)
                         {
                             deleteSentMessage(position,holder)
                         }
                     }))
                     builder.show()
                 }
             }
             //image message -left side
             else if (!chat.sender.equals(firebaseUser!!.uid)) {
                 holder.txtShowTextMessage!!.visibility = View.GONE
                 holder.leftImageView!!.visibility = View.VISIBLE
                 Picasso.get().load(chat.url).into(holder.leftImageView)
                 holder.leftImageView!!.setOnClickListener {
                     val options= arrayOf<CharSequence>(
                         "view full image",
                         "delete image",
                         " cancel"
                     )
                     var builder: AlertDialog.Builder= AlertDialog.Builder(holder.itemView.context)
                     builder.setTitle("What do you want?")
                     builder.setItems(options, DialogInterface.OnClickListener({
                             dialog, which ->
                         if (which==0){
                             val intent= Intent(context,
                                 ViewFullImageActivity::class.java)
                             intent.putExtra("url",chat.url)
                             context.startActivity(intent)
                         }
                         else if(which==1)
                         {
                             deleteSentMessage(position,holder)
                         }
                     }))
                     builder.show()
                 }
             }
         }
         //text message
         else {
             holder.txtShowTextMessage!!.text = chat.message
             holder.txtShowTextMessage!!.setOnClickListener {
                 val options= arrayOf<CharSequence>(

                     "delete message",
                     " cancel"
                 )
                 var builder: AlertDialog.Builder= AlertDialog.Builder(holder.itemView.context)
                 builder.setTitle("What do you want?")
                 builder.setItems(options, DialogInterface.OnClickListener({
                         dialog, which ->
                     if(which==0)
                     {
                         deleteSentMessage(position,holder)
                     }
                 }))
                 builder.show()
             }
         }
         //sent and seen msg
         if (position == chatList.size - 1) {
             if (chat.isseen) {
                 holder.textSeen!!.text = "seen"
                 if (chat.message.equals("sent you an image.") && !chat.url.equals("")) {
                     val lp: RelativeLayout.LayoutParams? =
                         holder.textSeen!!.layoutParams as RelativeLayout.LayoutParams
                     lp!!.setMargins(0, 245, 10, 0)
                     holder.textSeen!!.layoutParams = lp
                 }
             } else {
                 holder.textSeen!!.text = "sent"
                 if (chat.message.equals("sent you an image.") && !chat.url.equals("")) {
                     val lp: RelativeLayout.LayoutParams? =
                         holder.textSeen!!.layoutParams as RelativeLayout.LayoutParams
                     lp!!.setMargins(0, 245, 10, 0)
                     holder.textSeen!!.layoutParams = lp
                 }
             }
         } else {
             holder.textSeen!!.visibility = View.GONE
         }
     }

     override fun getItemCount(): Int {
         return chatList.size
     }
     class chatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         var imgMessageItemLeftProfile: ImageView? =
             view.findViewById(R.id.msg_left_profile)
         var txtShowTextMessage: TextView? = view.findViewById(R.id.txtShowTextMessage)
         var leftImageView: ImageView? = view.findViewById(R.id.leftImageView)
         var textSeen: TextView? = view.findViewById(R.id.textSeen)
         var rightImageView: ImageView? = view.findViewById(R.id.rightImageView)
     }
    override fun getItemViewType(position: Int): Int {


        return if (chatList[position].sender.equals(firebaseUser!!.uid)) {
            1
        } else {
            0
        }
    }
    private fun deleteSentMessage(position: Int,holder:ChatAdaptor.chatViewHolder){
        val ref= FirebaseDatabase.getInstance().reference.child("Chats")
            .child(chatList.get(position).messageId!!)
            .removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(holder.itemView.context,"deleted", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(holder.itemView.context,"failed,to delete", Toast.LENGTH_SHORT).show()
                }
            }
    }

 }