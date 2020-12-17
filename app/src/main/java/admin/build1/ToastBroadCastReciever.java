package admin.build1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import admin.build1.Services.ToastService;

public class ToastBroadCastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");

        ToastService.showToast(context, message);
    }
}
