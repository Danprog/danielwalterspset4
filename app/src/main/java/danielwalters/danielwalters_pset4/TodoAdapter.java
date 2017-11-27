package danielwalters.danielwalters_pset4;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;


/**
 * Created by Daniel Walters on 25/11/2017.
 */

public class TodoAdapter extends ResourceCursorAdapter {

    public TodoAdapter (Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.row_todo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        TextView todo = view.findViewById(R.id.textView);
        CheckBox checkbox = view.findViewById(R.id.checkBox);

        todo.setText(cursor.getString(cursor.getColumnIndex("title")));
        Integer bool = cursor.getInt(cursor.getColumnIndex("completed"));

        if (bool == 1){
            checkbox.setChecked(true);
        }
        else{
            checkbox.setChecked(false);
        }
    }
}