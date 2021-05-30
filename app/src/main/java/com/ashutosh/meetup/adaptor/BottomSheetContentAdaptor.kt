package com.ashutosh.meetup.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.activity.DetailsActivity
import com.ashutosh.meetup.model.BottomSheetContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BottomSheetContentAdaptor(private val context: Context,private val itemList:ArrayList<BottomSheetContent>)
    :RecyclerView.Adapter<BottomSheetContentAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_contents_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val buttomSheetContent=itemList[position]
        holder.content_name.text=buttomSheetContent.contentName
        holder.content_pic.setImageResource(buttomSheetContent.contentImage)
        holder.ll1.setOnClickListener {
          // buttomSheetContent.interestType
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("interest", buttomSheetContent.interestType)
            intent.putExtra("fav", buttomSheetContent.contentName)
            context.startActivity(intent)

        }

        }

    override fun getItemCount(): Int {
        return itemList.size
    }
  class ViewHolder(view: View):RecyclerView.ViewHolder(view){
      val content_pic:ImageView=view.findViewById(R.id.content_pic)
      val content_name:TextView=view.findViewById(R.id.content_name)
      val ll1:LinearLayout=view.findViewById(R.id.ll1)

  }
}

