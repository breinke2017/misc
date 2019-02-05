package com.androidexample.notifications2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // We create a custom notificationID and channelID.
    private val NOTIFICATION_ID: Int = 123
    private val CHANNEL_ID = "my_channel_123"

    var notification_default = NotificationCompat.Builder(this, CHANNEL_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notification_default.setAutoCancel(true)

        // Must call as soon as app starts--only for Oreo and above!
        createNotificationChannel()


        setupNotification()
        displayNotification()

        button.setOnClickListener {
            // Displays the notification_default
            displayNotification()
        }
    }

    private fun setupNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // pendingIntent runs even if the app is closed!
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notification_default.apply {
            setContentTitle("New blog got updated!")
            setContentText("Here is the title of the new blog that was posted!")
            setPriority(NotificationCompat.PRIORITY_LOW)

            // Note: On versions less than Oreo, this *must* be a mipmap!  Otherwise the app will crash!
            // The image has to be transparent, otherwise it will just show as a white box.
            setSmallIcon(R.mipmap.baseline_alarm_black_18dp)

            // Set the intent that will fire when the user taps the notification_default
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
    }


    private fun displayNotification() {
        // Displays the notification_default.

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification_default that you must define
            notify(NOTIFICATION_ID, notification_default.build())
        }
    }


    private fun createNotificationChannel() {
        // Only for Oreo and above!

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = "some notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}


