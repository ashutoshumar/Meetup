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
import com.ashutosh.meetup.model.ChatList
import com.squareup.picasso.Picasso

class ChatListAdapter(val context: Context, val itemList:List<ChatList>, val isChatCheck:Boolean):
    RecyclerView.Adapter<ChatListAdapter.chatListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatListViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.friends_chat,parent,false)
        return chatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: chatListViewHolder, position: Int) {
        val users=itemList[position]
        holder.txtChatUserName.text=users.chatUserName
        Picasso.get().load(users.chatProfileImage).placeholder(R.drawable.white).into(holder.imgChatProfileImage)
      //  holder.chatLinearLayout.setOnClickListener{
        //    val intent= Intent(context, MessageActivity::class.java)
          //  intent.putExtra("visit_id",users.chatUserId)
            //intent.putExtra("sender_pic",mUsers.getProfile())
            //context.startActivity(intent)
        //}
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class chatListViewHolder(view: View): RecyclerView.ViewHolder(view){
        var txtChatUserName: TextView =view.findViewById(R.id.txtChatUserName)
        var imgChatProfileImage: ImageView =view.findViewById(R.id.imgChatProfileImage)
        var txtChatLastSeen: TextView =view.findViewById(R.id.txtChatLastSeen)
        var imgChatOnlineStatus: CardView =view.findViewById(R.id.imgChatOnlineStatus)
        var imgChatOfflineStatus: CardView =view.findViewById(R.id.imgChatOfflineStatus)
        var chatLinearLayout: LinearLayout =view.findViewById(R.id.chatLinearLayout)
    }
}