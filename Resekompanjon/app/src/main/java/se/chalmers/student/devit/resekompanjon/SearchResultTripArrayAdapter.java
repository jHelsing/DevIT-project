package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTripSummary;

/**
 * @author Jonathan
 * @version 1.0
 */
public class SearchResultTripArrayAdapter extends ArrayAdapter<SearchResultTripSummary> implements View.OnClickListener {

    private final Context context;

    private ArrayList<SearchResultTripSummary> values;
    private SelectionClickListener listener;
    private int valuePosition = 0;

    public SearchResultTripArrayAdapter(Context context, ArrayList<SearchResultTripSummary> values, ListView view) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.listener = new SelectionClickListener(context, view, null, values);
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        ItemViewHolder<SearchResultTripSummary> viewHolder = null;
        if (convertView == null || !(convertView.getTag() instanceof ItemViewHolder<?>)) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_search_result_box, null);

            viewHolder = new ItemViewHolder<SearchResultTripSummary>(convertView, values.get(valuePosition));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder<SearchResultTripSummary>) convertView.getTag();
        }
        SearchResultTripSummary entity = getItem(valuePosition);
        viewHolder.setViewFields(entity, convertView);
        Log.d("INDEX", valuePosition + "");
        valuePosition++;
        if (valuePosition >= values.size()) {
            valuePosition = 0;
        }
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