package se.chalmers.student.devit.resekompanjon;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Linnea
 */
public class PlannedTripExpandableListAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {

    private final Context context;
    /**
     * The list of objects to display and have reminder for
     */
    private ArrayList<JsonObject> arrayListData;

    public PlannedTripExpandableListAdapter(Context context, ArrayList<JsonObject> values) {
        super();
        this.context = context;
        this.arrayListData = values;
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return arrayListData.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public Object getGroup(int groupPosition) {
        return arrayListData.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayListData.get(groupPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_planned_trip, null);
        }
        // Initiates all default values
        JsonObject objectToShow = arrayListData.get(groupPosition).getAsJsonObject();
        TextView tvLine = (TextView) convertView.findViewById(R.id.plannedTripsTVLineNumber);
        TextView tvStartPos = (TextView) convertView.findViewById(R.id.plannedTripsTVFrom);
        TextView tvEndPos = (TextView) convertView.findViewById(R.id.plannedTripsTVTo);
        TextView tvDate = (TextView) convertView.findViewById(R.id.plannedTripsTVDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.plannedTripsTVTime);
        tvLine.setTypeface(null, Typeface.BOLD);
        tvLine.setText("XX");
        tvStartPos.setText(objectToShow.get("originName").getAsString());
        tvEndPos.setText(objectToShow.get("endName").getAsString());
        tvDate.setText(objectToShow.get("date").getAsString());
        tvTime.setText(objectToShow.get("time").getAsString());
        return convertView;
    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_planned_trip_extended, null);
        }

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.plannedTripExtendedFrameLayout);
        linearLayout.setTag(groupPosition);
        Switch reminderSwitch = (Switch) convertView.findViewById(R.id.plannedTripExtendedReminderSwitch);
        Switch stopSwitch = (Switch) convertView.findViewById(R.id.plannedTripExtendedStopSwitch);
        reminderSwitch.setOnCheckedChangeListener(this);
        stopSwitch.setOnCheckedChangeListener(this);
        if (!reminderSwitch.isChecked()) {
            // If the user don't want a reminder, make sure to hide the rest of the information
            LinearLayout linearLayoutOne = (LinearLayout) convertView.findViewById(R.id.plannedTripExtendedLLOne);
            LinearLayout linearLayoutTwo = (LinearLayout) convertView.findViewById(R.id.plannedTripExtendedLLTwo);
            linearLayoutOne.setVisibility(View.INVISIBLE);
            linearLayoutTwo.setVisibility(View.INVISIBLE);
        }
        startAlarm(groupPosition);
        return convertView;
    }

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()) {
            case R.id.plannedTripExtendedReminderSwitch:
                LinearLayout parent = (LinearLayout) buttonView.getParent();
                int position = (Integer) parent.getTag();
                if(isChecked) {
                    // show the extra items on screen.
                    LinearLayout linearLayout = (LinearLayout) parent.findViewById(R.id.plannedTripExtendedLLOne);
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout = (LinearLayout) parent.findViewById(R.id.plannedTripExtendedLLTwo);
                    linearLayout.setVisibility(View.VISIBLE);

                    // Start the alarm
                    startAlarm(position);

                } else {
                    // Turn off the alarm
                    stopAlarm(position);
                }
                break;
            case R.id.plannedTripExtendedStopSwitch:
                ViewParent v = buttonView.getParent();
                break;
        }
    }

    private void startAlarm(int position) {
        JsonObject jsonObject = arrayListData.get(position);
        Calendar cal = Calendar.getInstance();
        String dateAsString = jsonObject.get("date").toString();
        String timeAsString = jsonObject.get("time").getAsString();
        int year = Integer.parseInt(dateAsString.substring(1, 5));
        int month = Integer.parseInt(dateAsString.substring(6, 8));
        int day = Integer.parseInt(dateAsString.substring(9, 11));
        int hour = Integer.parseInt(timeAsString.substring(0,2));
        int min = Integer.parseInt(timeAsString.substring(3));
        cal.set(year, month, day, hour, min);
        AlarmManager alarmMng = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context.getApplicationContext(), PlannedTripAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMng.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1, pendingIntent);
        Log.d("TAGG", "Turned On");
    }

    private void stopAlarm(int position) {
        AlarmManager alarmMng = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        JsonObject jsonObject = arrayListData.get(position);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClassName("se.chalmers.student.devit.resekompanjon", "PlannedTripAlarm");
        i.putExtra("JSON", jsonObject.toString());
        PendingIntent intent = PendingIntent.getActivity(context, position,i, PendingIntent.FLAG_UPDATE_CURRENT );
        alarmMng.cancel(intent);
        Log.d("TAGG", "TURNED OFF");
    }
}