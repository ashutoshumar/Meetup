package com.ashutosh.meetup.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashutosh.meetup.R
import com.ashutosh.meetup.adaptor.HomepagrAdaptor
import com.ashutosh.meetup.model.HompageContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class HomeFragment : Fragment() {
    private lateinit var homepagrAdaptor: HomepagrAdaptor
    private lateinit var homepageRecyclerview:RecyclerView
    private  var user_list= arrayListOf<HompageContent>()
    lateinit var homeProgressLayout: RelativeLayout
    lateinit var homeProgressBar: ProgressBar
    private lateinit var actxttFilter: AutoCompleteTextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        homepageRecyclerview=view.findViewById(R.id.recyclerViewHome)
        homeProgressBar=view.findViewById(R.id.homeProgressBar)
        homeProgressLayout=view.findViewById(R.id.homeProgressLayout)
        actxttFilter=view.findViewById(R.id.actxtFilter)
        homeProgressLayout.visibility=View.VISIBLE
        homeProgressBar.visibility=View.VISIBLE
        homepageRecyclerview!!.setHasFixedSize(true)
        homepageRecyclerview!!.layoutManager= LinearLayoutManager(context)
        retrieveAllUsers("Users","none")

        val filterItems = listOf("None", "Sports", "Entrepreneur","Political Party","Politician","Movies","Singer","Actor","Actress","Hobbies","Destination","Career","Technology")
        val filterAdapter = ArrayAdapter(activity as Context,R.layout.list_item,filterItems)
        actxttFilter.setAdapter(filterAdapter)
        actxttFilter.setOnItemClickListener { parent, view, position, id ->
            val actxtFilter=parent.getItemAtPosition(position).toString()

            if (actxtFilter.toString() == "None")
            {
                retrieveAllUsers("Users","none")
            }
            else if(actxtFilter.toString() == "Sports")
            {

                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Sports").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Sports").value.toString()
                            retrieveUsers("Sports",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Entrepreneur")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Entrepreneur").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Entrepreneur").value.toString()
                            retrieveUsers("Entrepreneur",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }
            else if(actxtFilter.toString() == "Political Party")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Political Party").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Political Party").value.toString()
                            retrieveUsers("Political Party",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }
            else if(actxtFilter.toString() == "Politician")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Politician").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Politician").value.toString()
                            retrieveUsers("Politician",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }
            else if(actxtFilter.toString() == "Movies")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Movies").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Movies").value.toString()
                            retrieveUsers("Movies",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Singer")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Singer").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Singer").value.toString()
                            retrieveUsers("Singer",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Actor")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Actor").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Actor").value.toString()
                            retrieveUsers("Actor",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Actress")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Actress").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Actress").value.toString()
                            retrieveUsers("Actress",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Hobbies")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Hobbies").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Hobbies").value.toString()
                            retrieveUsers("Hobbies",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Destination")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Destination").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Destination").value.toString()
                            retrieveUsers("Destination",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else if(actxtFilter.toString() == "Career")
            {
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Career").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Career").value.toString()
                            retrieveUsers("Career",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else{
                var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
                var refUsers = FirebaseDatabase.getInstance().reference.child("Technology").child(firebaseUserId)
                refUsers.addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists())
                        {
                            val interest=snapshot.child("Technology").value.toString()
                            retrieveUsers("Technology",interest)
                        }else
                        {
                            (user_list as ArrayList<HompageContent>).clear()
                            Toast.makeText(context,"You Have Not Chosen Your Interest",Toast.LENGTH_SHORT).show()
                            val homepage1 = HompageContent("https://firebasestorage.googleapis.com/v0/b/meet-up-a1d6f.appspot.com/o/white.jpg?alt=media&token=3c7d74b5-9404-49d8-a8dd-803da696318b", " "," ")
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                            homepagrAdaptor =
                                HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                            homepageRecyclerview!!.adapter = homepagrAdaptor
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }


        return view

    }
    fun retrieveUsers(interestType:String,interest:String)
    {
        var firebaseUserId=FirebaseAuth.getInstance().currentUser!!.uid
                    var querryUsers=FirebaseDatabase.getInstance().reference.child(interestType).orderByChild(interestType).startAt(interest).endAt(interest+"\ufaff")
                    querryUsers!!.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            (user_list as ArrayList<HompageContent>).clear()
                            for (snapshot in p0.children) {
                                val userid = snapshot.child("userId").value
                                val user_name = snapshot.child("userName").getValue().toString()
                                val user_pic = snapshot.child("picture").value.toString()
                                val homepage1 = HompageContent(user_pic, user_name,userid.toString())
                                if (!(userid)!!.equals(firebaseUserId)) {
                                    homeProgressLayout.visibility = View.GONE
                                    (user_list as ArrayList<HompageContent>).add(homepage1)
                                }
                            }
                            if (context != null) {
                                homepagrAdaptor =
                                    HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                                homepageRecyclerview!!.adapter = homepagrAdaptor
                            }

                        }

                    })








    }



    fun retrieveAllUsers(interestType:String,interest:String) {
        if (interest == "none") {
            var firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
            var refUsers = FirebaseDatabase.getInstance().reference.child("Users")
            refUsers!!.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    (user_list as ArrayList<HompageContent>).clear()
                    for (snapshot in p0.children) {
                        val userid = snapshot.child("userId").value
                        val user_name = snapshot.child("userName").getValue().toString()
                        val user_pic = snapshot.child("picture").value.toString()
                        val homepage1 = HompageContent(user_pic, user_name,userid.toString())
                        if (!(userid)!!.equals(firebaseUserId)) {
                            homeProgressLayout.visibility = View.GONE
                            (user_list as ArrayList<HompageContent>).add(homepage1)
                        }
                    }
                    if (context != null) {
                        homepagrAdaptor =
                            HomepagrAdaptor(context!!, (user_list as ArrayList<HompageContent>))
                        homepageRecyclerview!!.adapter = homepagrAdaptor
                    }

                }

            })
        }

    }
}