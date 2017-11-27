package danielwalters.danielwalters_pset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TodoDatabase database;
    private TodoAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = TodoDatabase.getInstance(getApplicationContext());
        Cursor cursor = database.selectAll();

        listView = findViewById(R.id.listView);
        adapter = new TodoAdapter(this, cursor);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new shortClick());
        listView.setOnItemLongClickListener(new longClick());


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                addItem();
            }
        });

    }

    private void addItem(){
        EditText editText = findViewById(R.id.editText);

        database.insert(editText.getText().toString(), 0);
        editText.setText("");
        updateData();

        String added = "Item added";
        Toast.makeText(MainActivity.this, (String) added,
                Toast.LENGTH_LONG).show();
    }

    private void updateData() {

        Cursor newCursor = database.selectAll();

        adapter.swapCursor(newCursor);
        listView.setAdapter(adapter);
    }

    private class shortClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CheckBox cb_completed = view.findViewById(R.id.checkBox);
            Cursor cursor = database.selectAll();
            cursor.move(position + 1);

            int long_id = cursor.getInt(cursor.getColumnIndex("_id"));
            if (cb_completed.isChecked()) {
                database.update(long_id, 0);
            }
            else {
                database.update(long_id , 1);
            }

            updateData();
        }
    }

    private class longClick implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = database.selectAll();
            cursor.move(position + 1);

            int long_id = cursor.getInt(cursor.getColumnIndex("_id"));
            database.delete(long_id);
            updateData();
            String deleted = "Item deleted";
            Toast.makeText(MainActivity.this, (String) deleted,
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }


}