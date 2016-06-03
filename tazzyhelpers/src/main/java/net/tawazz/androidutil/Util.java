package net.tawazz.androidutil;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.view.View;

import java.io.File;

/**
 * Created by tawanda on 14/03/2016.
 */
public class Util {

    /**
     * @param context application context
     * @param title   title of the alert
     * @param message message of the alert
     */
    public static void Alert(Context context, String title, String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static ProgressDialog loadingDialog(Context context, String message) {

        return ProgressDialog.show(context, "", message, true);
    }
    /**
     * @param dp dp to be conveted to px
     * @param context activity context
     * @return pixels of given dp
     */
    public static int dpTopx(int dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    public static int generateViewId() {

        int id = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            id = View.generateViewId();
        }

        return id;
    }

    /*
     * convertByteArrayImageToBase64: convert an image stored as a byte array to a base64 string
     */
    public static String convertByteArrayImageToBase64(byte[] byteArrayImage) {
        return Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);
    }

    /**
     * Deletes a file at the specified URI
     *
     * @param activity
     *            the activity context that this is being called from
     * @param uri
     *            the URI to delete
     */
    public static boolean deleteFileAtUri(Activity activity, Uri uri)
    {
        boolean deleted = false;
        if (uri != null)
        {
            try
            {
                activity.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        MediaStore.MediaColumns.DATA + "='" + uri.getPath() + "'", null);
            }
            catch (Throwable th)
            {
                // Do nothing file could be from gallery
            }

            File file = new File(uri.getPath());
            deleted = file.delete();
        }
        return deleted;
    }

    /**
     *
     * @param context launching context of the notification
     * @param openingClass Activity to open when notification clicked
     * @param icon notification icon
     * @param title Title of the notification
     * @param message notification content
     */

    public static void displayNotification(Context context,Class openingClass, int icon ,String title, String message )
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(icon);
        mBuilder.setContentTitle(title).setContentText(message).setAutoCancel(true);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, openingClass);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // The stack builder object will contain an artificial back stack for
        // the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(openingClass);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
