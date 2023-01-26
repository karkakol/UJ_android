package com.example.a8zad.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.a8zad.R
import com.example.a8zad.data.model.product.Product

class AppNotification {
    companion object{
       private const val importantChannelName = "ImportantDefault"
        private const val importantChannelDescription = "ImportantDefaultChannelDesc"

        private const val channelName = "Default"
        private const val channelDescription = "DefaultChannelDesc"

        private const val KEY_TEXT_REPLY = "key_text_reply"
        private const val replayLabel = "Odpowiedz"
        private const val label = "Odpowiedz"
        private var id =1
        private var id2 =2

        fun createImportantNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(importantChannelName, importantChannelName, importance).apply {
                    description = importantChannelDescription
                }
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelName, channelName, importance).apply {
                    description = channelDescription
                }
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun createSimpleNotification(context: Context){

            val builder = NotificationCompat.Builder(context, channelName)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Nowe produkty!!")
                .setContentText("Szybko obczajaj nowe produkty zanim znikną")
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setNumber(1)
                .setPriority(Notification.BADGE_ICON_LARGE)

            with(NotificationManagerCompat.from(context)) {
                notify(id2, builder.build())
            }
        }

        fun createImportantNotification(context: Context, product: Product){
            var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replayLabel)
                build()
            }

            val intent = Intent(context, AppBroadcastReceiver::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            var replyPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(context,
                    1,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            var action: NotificationCompat.Action =
                NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_foreground,
                    label, replyPendingIntent)
                    .addRemoteInput(remoteInput)
                    .build()

            val builder = NotificationCompat.Builder(context, importantChannelName)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Zniżka")
                .setContentText("Czy chcesz kupić: ${product.name}")
                .addAction(action)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setNumber(1)
                .setPriority(Notification.PRIORITY_HIGH);

            with(NotificationManagerCompat.from(context)) {
                notify(id, builder.build())
            }
        }

        fun replyUser(context: Context, message: String) {
            val builder = NotificationCompat.Builder(context, importantChannelName)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reply from server")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setNumber(1)
            with(NotificationManagerCompat.from(context)) {
                notify(id, builder.build())
            }
        }

        fun getMessage(intent: Intent): CharSequence? {
            return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_REPLY)
        }
    }
}