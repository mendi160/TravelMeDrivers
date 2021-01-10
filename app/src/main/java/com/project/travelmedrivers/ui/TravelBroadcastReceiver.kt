package com.project.travelmedrivers.ui

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
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
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val mBuilder: Notification.Builder = Notification.Builder(context, id)
        mBuilder.setSmallIcon(R.mipmap.sym_def_app_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setContentIntent(pendingIntent)
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .build()

        // Gets an instance of the NotificationManager service
        val mNotificationManager =
            context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        /*
        What Are Notification Channels?
        Notification channels enable us app developers to group our notifications into groups—channels—with
        the user having the ability to modify notification settings for the entire channel at once.
        For example, for each channel, users can completely block all notifications,
        override the importance level, or allow a notification badge to be shown.
        This new feature helps in greatly improving the user experience of an app.
        */
        mNotificationManager!!.createNotificationChannel(channel)
        mBuilder.setChannelId(id)

        /*
        When you issue multiple notifications about the same type of event,
        it’s best practice for your app to try to update an existing notification
        with this new information, rather than immediately creating a new notification.
        If you want to update this notification at a later date, you need to assign it an ID.
        You can then use this ID whenever you issue a subsequent notification.
        If the previous notification is still visible, the system will update this existing notification,
        rather than create a new one. In this example, the notification’s ID is 001
        */mNotificationManager.notify(1, mBuilder.build())
}

fun getPackageName(context: Context): String {
    return context.packageName
}