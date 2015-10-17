package se.chalmers.student.devit.resekompanjon;

import android.view.View;

/**
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

}