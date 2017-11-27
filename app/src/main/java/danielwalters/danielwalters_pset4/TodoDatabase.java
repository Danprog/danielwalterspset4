package danielwalters.danielwalters_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel Walters on 22/11/2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    private static TodoDatabase instance;
    private static String database = null;

    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE todos (title TEXT, completed INTEGER, _id INTEGER PRIMARY KEY AUTOINCREMENT);");
        sqLiteDatabase.execSQL("INSERT INTO todos (title, completed) VALUES ('test1', 1);");
        sqLiteDatabase.execSQL("INSERT INTO todos (title, completed) VALUES ('test2', 0);");
        sqLiteDatabase.execSQL("INSERT INTO todos (title, completed) VALUES ('test3', 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE sqLiteDatabase.todos");
        onCreate(sqLiteDatabase);
    }
    public static TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TodoDatabase(context.getApplicationContext(), database, null, 1);
        }
        return instance;
    }

    public Cursor selectAll() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM  todos", null);
        return cursor;
    }

    public void insert(String title, int completed) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("completed", completed);
        sqLiteDatabase.insert("todos", "null", contentValues);
    }

    public void update (long id, int check) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("completed", check);
        sqLiteDatabase.update("todos", contentValues, "_id=" + id, null);
    }

    public void delete (long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("todos", "_id=" + id, null);
    }
}