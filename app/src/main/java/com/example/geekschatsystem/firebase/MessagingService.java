package com.example.geekschatsystem.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService
{
    @Override
    //This method is called when a new FCM(firebase cloud messaging) registration token is generated for the app
    public void onNewToken(@NonNull String token) {
        //The token parameter represents the new registration token assigned to the app instance on the device.
        super.onNewToken(token); //this call ensures that the default behavior of the parent class is executed.
        Log.d("FCM", "Token: " + token);//The token is then logged using
    }

    @Override
    //This method is called when a new FCM message is received by the device
    public void onMessageReceived(@NonNull RemoteMessage message) {
        //The message parameter contains the data and notification payload of the incoming message.
        super.onMessageReceived(message);
        Log.d("FCM", "Message: " + message.getNotification().getBody());
    }
}

/*
this class is responsible for handling the generation of new FCM registration tokens (onNewToken method)
and processing incoming FCM messages (onMessageReceived method).
The Log.d statements are useful for debugging and understanding the behavior of FCM within the application
 */
