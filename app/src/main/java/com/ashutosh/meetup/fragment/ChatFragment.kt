package com.ashutosh.meetup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.adaptor.ChatListAdapter
import com.ashutosh.meetup.model.ChatList
import com.ashutosh.meetup.model.ChatUserId
import com.ashutosh.meetup.model.HompageContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatFragment : Fragment() {
    lateinit var chatListAdapter:ChatListAdapter
    private var mUsers= arrayListOf<ChatList>()
    private var usersChatList= arrayListOf<ChatUserId>()
    lateinit var chatRecyclerView: RecyclerView
    private var firebaseUser: FirebaseUser?=null
    //lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_chat, container, false)
        chatRecyclerView=view.findViewById(R.id.chatFragmentRecyclerView)
        chatRecyclerView.setHasFixedSize(true)
       chatRecyclerView!!.layoutManager= LinearLayoutManager(context)

        firebaseUser= FirebaseAuth.getInstance().currentUser
        val ref= FirebaseDatabase.getInstance().reference.child("newChatList").child(firebaseUser!!.uid)
        ref!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (usersChatList as ArrayList).clear()
                if (p0.exists()){
                    for (dataSnapshot in p0.children){
                        //val chatList=dataSnapshot.getValue(ChatUserId::class.java)
                        val chatList=dataSnapshot.child("id").value.toString()
                        if(!(chatList).equals(firebaseUser!!.uid)){
                            (usersChatList as ArrayList).add(ChatUserId(chatList))}
                        retrieveChatList()
                    }
                }

            }

        })
       /* val user1 = ChatList("userid.toString()", "user_name","https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b","  ","offline")
        val user3 = ChatList("userid.toString()", "user_name","https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b","  ","offline")
        val user2 = ChatList("userid.toString()", "user_name","https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b","  ","offline")
        mUsers.add(user1)
        mUsers.add(user2)
        mUsers.add(user3)
        chatListAdapter= ChatListAdapter(context!!,(mUsers as ArrayList<ChatList>),true)
        chatRecyclerView!!.adapter=chatListAdapter*/
        return view
    }
    private fun retrieveChatList(){
        mUsers=ArrayList()
        val ref=FirebaseDatabase.getInstance().reference.child("Users")
        ref!!.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (mUsers as ArrayList).clear()
                for (snapshot in p0.children) {
                    val userid = snapshot.child("userId").value
                    val user_name = snapshot.child("userName").getValue().toString()
                    val user_pic = snapshot.child("picture").value.toString()
                    val user = ChatList(userid.toString(), user_name, user_pic, "  ", "offline")

                    for (eachChatList in usersChatList!!) {
                        if ((user.chatUserId)!!.equals(eachChatList.id) && !(user!!.chatUserId).equals(
                                firebaseUser!!.uid
                            )
                        ) {
                            (mUsers as ArrayList).add(user!!)
                        }
                    }
                }
                if (context != null) {
                    chatListAdapter =
                        ChatListAdapter(context!!, (mUsers as ArrayList<ChatList>), true)
                    chatRecyclerView!!.adapter = chatListAdapter
                    //chatRecyclerView!!.layoutManager=layoutManager}
                }
            }

        })
    }

}