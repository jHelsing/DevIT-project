package se.chalmers.student.devit.resekompanjon;

import android.view.View;

import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTripSummary;

/**
 * A Holder for ItemViews. Used to avoid using findViewById multiple times for the same view.
 *
 * @author Jonathan
 * @version 1.0
 */
public class ItemViewHolder<T> extends SearchResultViewHolder {

    private Object listener;
    private T item;

    public ItemViewHolder(View v) {
        super(v);
    }

    public ItemViewHolder(View v, T item) {
        super(v);
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public Object getListener() {
        return listener;
    }

    public void setItem(T item) {
        this.item = item;
    }

    protected void setListener(Object listener) {
        this.listener = listener;
    }

    public void setViewFields(SearchResultTripSummary entity, View convertView) {
        SearchResultTripSummary trip = (SearchResultTripSummary) item;
        super.setViewFields(trip);
    }
}
