package com.ashutosh.meetup.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.ashutosh.meetup.activity.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessaging:FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val sented=p0!!.data["sented"]
        val user=p0!!.data["user"]
        val firebaseUser= FirebaseAuth.getInstance().currentUser
        if (firebaseUser!=null && sented==firebaseUser.uid) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(p0)
                } else {
                    sendNotification(p0)
                }

        }
    }
    private fun sendNotification(mRemoteMessage: RemoteMessage) {
        val user=mRemoteMessage.data["user"]
        val icon=mRemoteMessage.data["icon"]
        val title=mRemoteMessage.data["title"]
        val body=mRemoteMessage.data["body"]
        val notification=mRemoteMessage.notification
        val j=user!!.replace("[\\D]".toRegex(),"").toInt()
        val intent= Intent(this, MessageActivity::class.java)

        val bundle= Bundle()
        bundle.putString("visit_id",user)
        intent.putExtras(bundle)
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent= PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder: NotificationCompat.Builder= NotificationCompat.Builder(this)
            .setSmallIcon(icon!!.toInt())
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)

        val noti=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var i=0
        if (j>0){
            i=j
        }
        //  oreoNotification.getManager!!.notify(i,builder.build())
    }

    private fun sendOreoNotification(mRemoteMessage: RemoteMessage) {
        val user=mRemoteMessage.data["user"]
        val icon=mRemoteMessage.data["icon"]
        val title=mRemoteMessage.data["title"]
        val body=mRemoteMessage.data["body"]
        val sented=mRemoteMessage!!.data["sented"]
        val notification=mRemoteMessage.notification
        val j=user!!.replace("[\\D]".toRegex(),"").toInt()
        val intent= Intent(this,MessageActivity::class.java)

        val bundle=Bundle()
        bundle.putString("visit_id",user)
        intent.putExtras(bundle)
      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val oreoNotification=OreoNotification(this)
        val builder: Notification.Builder=oreoNotification.getOreoNotofication(title,body,pendingIntent,defaultSound,icon)

        var i=0
        if (j>0){
            i=j
        }

            oreoNotification.getManager!!.notify(i, builder.build())

    }
}