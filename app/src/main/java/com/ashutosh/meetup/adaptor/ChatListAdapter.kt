package com.ashutosh.meetup.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.activity.MessageActivity
import com.ashutosh.meetup.model.Chat
import com.ashutosh.meetup.model.ChatList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ChatListAdapter(val context: Context, val itemList:List<ChatList>, val isChatCheck:Boolean):
    RecyclerView.Adapter<ChatListAdapter.chatListViewHolder>() {
    var lastMsg:String=""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.friends_chat, parent, false)
        return chatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: chatListViewHolder, position: Int) {
        val users = itemList[position]
        holder.txtChatUserName.text = users.chatUserName
        Picasso.get().load(users.chatProfileImage).placeholder(R.drawable.white)
            .into(holder.imgChatProfileImage)
        if (isChatCheck) {
            retriveLastMessage(users.chatUserId, holder.txtChatLastSeen)
        } else {
            holder.txtChatLastSeen.visibility = View.GONE
        }
        if (isChatCheck) {
            if (users.status == "online") {
                holder.imgChatOnlineStatus.visibility = View.VISIBLE
                holder.imgChatOfflineStatus.visibility = View.GONE
            } else {
                holder.imgChatOnlineStatus.visibility = View.GONE
                holder.imgChatOfflineStatus.visibility = View.VISIBLE
            }
        } else {
            holder.imgChatOnlineStatus.visibility = View.GONE
            holder.imgChatOfflineStatus.visibility = View.GONE
        }
          holder.chatLinearLayout.setOnClickListener{
        val intent= Intent(context, MessageActivity::class.java)
        intent.putExtra("visit_id",users.chatUserId)
        context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class chatListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtChatUserName: TextView = view.findViewById(R.id.txtChatUserName)
        var imgChatProfileImage: ImageView = view.findViewById(R.id.imgChatProfileImage)
        var txtChatLastSeen: TextView = view.findViewById(R.id.txtChatLastSeen)
        var imgChatOnlineStatus: ImageView = view.findViewById(R.id.imgChatOnlineStatus)
        var imgChatOfflineStatus: ImageView = view.findViewById(R.id.imgChatOfflineStatus)
        var chatLinearLayout: LinearLayout = view.findViewById(R.id.chatLinearLayout)
    }
    private fun retriveLastMessage(chatUserId: String?, txtSearchLastSeen: TextView) {
        lastMsg="defaultMsg"
        val firebaseUser= FirebaseAuth.getInstance().currentUser
        val refrence= FirebaseDatabase.getInstance().reference.child("Chats")
        refrence.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                for (dataSnapshot in p0.children){
                    val chat: Chat?=dataSnapshot.getValue(Chat::class.java)
                    if (firebaseUser!=null && chat !=null){
                        if (chat.receiver==firebaseUser!!.uid && chat.sender==chatUserId || chat.receiver==chatUserId&&chat.sender==firebaseUser!!.uid){
                            lastMsg=chat.message!!
                        }
                    }
                }
                when(lastMsg){
                    "defaultMsg"-> txtSearchLastSeen.text="no message"
                    "sent you an image"->txtSearchLastSeen.text="image sent."
                    else->txtSearchLastSeen.text=lastMsg
                }
                lastMsg="defaultMsg"
            }

        })
    }
}