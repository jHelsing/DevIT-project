package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 * @author Jonathan
 * @version 1.0
 */
public class FavoriteArrayAdapter extends ArrayAdapter {

    private ArrayList<JsonObject> list;

    private Context context;

    public FavoriteArrayAdapter(Context context, ArrayList<JsonObject> values) {
        super(context, -1, values);
        this.context = context;
        this.list = values;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        JsonObject objToShow = list.get(index);
        View rowView = inflater.inflate(R.layout.fragment_favorite_trip, parent, false);
        TextView tv = (TextView) rowView.findViewById(R.id.textViewFromLocation);
        tv.setText(objToShow.get("originName").getAsString());
        tv = (TextView) rowView.findViewById(R.id.textViewToLocation);
        tv.setText(objToShow.get("endName").getAsString());
        return rowView;
    }


}
