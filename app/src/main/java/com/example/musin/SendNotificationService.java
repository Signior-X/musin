package com.example.musin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SendNotificationService extends Service {
    public SendNotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
