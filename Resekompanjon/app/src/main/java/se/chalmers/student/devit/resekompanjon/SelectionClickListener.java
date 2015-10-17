package se.chalmers.student.devit.resekompanjon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import se.chalmers.student.devit.resekompanjon.backend.utils.readers.PrenumHandler;
import se.chalmers.student.devit.resekompanjon.fragment.SearchResultBoxFragment;

/**
 * @author Jonathan
 * @version 0.5
 */
public class SelectionClickListener implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ActionMode actionMode;
    private PrenumHandler handler;
    private Drawable uncheckedDrawable;
    private Drawable checkedDrawable;
    private int selectedItems = 1;

    public SelectionClickListener(Context context, ListView listView, ActionMode actionMode) {
        this.listView = listView;
        this.actionMode = actionMode;
        uncheckedDrawable = context.getResources().getDrawable(R.drawable.checkbox_untoggled);
        checkedDrawable = context.getResources().getDrawable(R.drawable.checkbox_toggled);
        handler = new PrenumHandler(context);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchResultBoxFragment box = (SearchResultBoxFragment)listView.getItemAtPosition(position);

    }
}
