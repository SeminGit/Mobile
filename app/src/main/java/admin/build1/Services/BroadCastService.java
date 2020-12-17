package admin.build1.Services;

import android.content.Context;
import android.content.Intent;

public class BroadCastService {

    public static void fireShowToastBroadCast(Context context, String message) {
        Intent intentToBroadcast = new Intent("this.broadcast.action.taxi");

        intentToBroadcast.putExtra("message", message);
        context.sendBroadcast(intentToBroadcast);
    }

}