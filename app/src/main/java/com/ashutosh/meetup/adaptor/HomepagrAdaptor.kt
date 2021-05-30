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
import com.ashutosh.meetup.model.BottomSheetContent
import com.ashutosh.meetup.model.HompageContent
import com.squareup.picasso.Picasso

class HomepagrAdaptor(private val context: Context, private val itemList:ArrayList<HompageContent>)
    : RecyclerView.Adapter<HomepagrAdaptor.HomepageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomepagrAdaptor.HomepageViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.homepagerecyclerview,parent,false)
        return HomepagrAdaptor.HomepageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomepagrAdaptor.HomepageViewHolder, position: Int) {
        val homepageContent=itemList[position]
        holder.userName.text = homepageContent.name
        Picasso.get().load(homepageContent.pic).into(holder.homeUserProfilePic)
        if(homepageContent.userId==" " && homepageContent.name==" ")
        {
            holder.cardView.isClickable=false
        }
        else{
            holder.cardView.setOnClickListener {
                val intent= Intent(context, MessageActivity::class.java)
                intent.putExtra("visit_id",homepageContent.userId)
                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class HomepageViewHolder(view: View):RecyclerView.ViewHolder(view){
        val homeUserProfilePic: ImageView =view.findViewById(R.id.homeUserProfilePic)
        val userName: TextView =view.findViewById(R.id.userName)
        val cardView:CardView =view.findViewById(R.id.homepagecardview)

    }
}