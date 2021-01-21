package com.project.travelmedrivers.ui

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.project.travelmedrivers.MainActivity


class TravelBroadcastReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("MyBroadCast", "brdcst rcvr")
        addNotification(context)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun addNotification(context: Context?) {
    val id = "channel_1" //id of channel
    val description = "New Travel" //Description information of channel
    val importance = NotificationManager.IMPORTANCE_HIGH //The Importance of channel
    val channel = NotificationChannel(id, description, importance) //Generating channel
    // prepare intent which is triggered if the
    // notification is selected
    val intent = Intent(context, MainActivity::class.java)
    //what will be open when clicking on notification
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
    val mBuilder: Notification.Builder = Notification.Builder(context, id)
    mBuilder.setSmallIcon(R.drawable.ic_input_add)
        .setContentTitle("Travel Added")
        .setContentText("A new Travel has been added, hurry up to catch it first.")
        .setContentIntent(pendingIntent)
    // Gets an instance of the NotificationManager service
    val mNotificationManager =
        context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    mNotificationManager!!.createNotificationChannel(channel)
    mBuilder.setChannelId(id)
    mNotificationManager.notify(1, mBuilder.build())
}