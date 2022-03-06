package com.kamdan.dkexpress;
import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class FirebaseMessageReceiver extends FirebaseMessagingService  {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        shownotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    public void shownotification(String title , String Message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Mynotification").setContentText(Message)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.dk)
                .setContentTitle(title);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(99,builder.build());

    }
}
