//package com.example.tbank.presentation.notification
//
//
//import android.app.ActivityManager
//import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
//import android.app.PendingIntent
//import android.content.Intent
//import android.util.Log
//import com.example.tbank.domain.fcm.RegisterFcmTokenUseCase
//import com.example.tbank.presentation.MainActivity
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//
//class AppMessagingService : FirebaseMessagingService() {
//
////    @Inject
////    lateinit var notificationManager: NotificationManager
////
////    @Inject
////    lateinit var saveFcmTokenUseCase: RegisterFcmTokenUseCase
//
//    private val job = Job()
//    private val scope = CoroutineScope(Dispatchers.IO + job)
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//        val type = message.data[DATA_TYPE] ?: return
//        val intent = Intent(applicationContext, MainActivity::class.java)
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent.putExtra("tripId", message.data[DATA_TRIP_ID]?.toInt())
//        intent.putExtra("type", type)
//        intent.putExtra("message", message.data[DATA_MESSAGE])
//
//        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
//        notificationManager.showNotification(message.data[DATA_TITLE] ?: "", message.data[DATA_MESSAGE] ?: "", pendingIntent)
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        Log.d("DEBUG","TOKEN - $token")
//        scope.launch {
//            saveFcmTokenUseCase(token)
//        }
//    }
//
//    companion object{
//        const val DATA_TRIP_ID = "trip_id"
//        const val DATA_TITLE = "title"
//        const val DATA_MESSAGE = "message"
//        const val DATA_TYPE = "type"
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        job.cancel()
//    }
//}