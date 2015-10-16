package se.chalmers.student.devit.resekompanjon;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Calendar;

/**
 * @author Linnea
 */
public class PlannedTripAlarm extends BroadcastReceiver {

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.  During this time you can use the other methods on
     * BroadcastReceiver to view/modify the current result values.  This method
     * is always called within the main thread of its process, unless you
     * explicitly asked for it to be scheduled on a different thread using
     * {@link Context#registerReceiver(BroadcastReceiver,
     * IntentFilter, String, Handler)}. When it runs on the main
     * thread you should
     * never perform long-running operations in it (there is a timeout of
     * 10 seconds that the system allows before considering the receiver to
     * be blocked and a candidate to be killed). You cannot launch a popup dialog
     * in your implementation of onReceive().
     * <p/>
     * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
     * then the object is no longer alive after returning from this
     * function.</b>  This means you should not perform any operations that
     * return a result to you asynchronously -- in particular, for interacting
     * with services, you should use
     * {@link Context#startService(Intent)} instead of
     * {@link Context#bindService(Intent, ServiceConnection, int)}.  If you wish
     * to interact with a service that is already running, you can use
     * {@link #peekService}.
     * <p/>
     * <p>The Intent filters used in {@link Context#registerReceiver}
     * and in application manifests are <em>not</em> guaranteed to be exclusive. They
     * are hints to the operating system about how to find suitable recipients. It is
     * possible for senders to force delivery to specific recipients, bypassing filter
     * resolution.  For this reason, {@link #onReceive(Context, Intent) onReceive()}
     * implementations should respond only to known actions, ignoring any unexpected
     * Intents that they may receive.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        JsonObject jsonObject = new JsonParser().parse(intent.getStringExtra("JSON")).getAsJsonObject();
        Calendar cal = Calendar.getInstance();
        String dateAsString = jsonObject.get("originDate").getAsString();
        int year = Integer.parseInt(dateAsString.substring(0, 4));
        int month = Integer.parseInt(dateAsString.substring(5, 7));
        int day = Integer.parseInt(dateAsString.substring(8));
        String timeAsString = jsonObject.get("originTime").getAsString();
        int hour = Integer.parseInt(timeAsString.substring(0,2));
        int min = Integer.parseInt(timeAsString.substring(3));
        cal.set(year, month, day, hour, min);
        Notification notify = new Notification(R.drawable.stop_toggled, "Dags att gå till bussen",
                cal.getTimeInMillis());
        notifyMgr.notify(R.drawable.favourite_toggled, notify);
    }
}