package se.chalmers.student.devit.resekompanjon;

import android.app.Notification;
import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResaultTrips;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.SearchResultBoxFragment;

/**
 * @author Jonathan
 * @version 1.0
 */
public class SearchResultTripArrayAdapter extends ArrayAdapter<SearchResaultTrips> implements View.OnClickListener {

    private final Context context;

    private ArrayList<SearchResaultTrips> values;
    private SelectionClickListener listener;

    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

    public SearchResultTripArrayAdapter(Context context, ArrayList<SearchResaultTrips> values, ListView view) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.listener = new SelectionClickListener(context,view, null, values);
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        ItemViewHolder<SearchResaultTrips> viewHolder = null;
        if(convertView == null || !(convertView.getTag() instanceof ItemViewHolder<?>)) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_search_result_box, null);

            viewHolder = new ItemViewHolder<SearchResaultTrips>(convertView, values.get(index));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder<SearchResaultTrips>) convertView.getTag();
        }
        SearchResaultTrips entity = getItem(index);
        viewHolder.setViewFileds(entity, convertView);
        convertView.setOnClickListener(this);
        return convertView;

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

}