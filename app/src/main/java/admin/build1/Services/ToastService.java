package admin.build1.Services;

import android.content.Context;
import android.widget.Toast;

public class ToastService {

    public static void showToast (Context context, String message){

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

}