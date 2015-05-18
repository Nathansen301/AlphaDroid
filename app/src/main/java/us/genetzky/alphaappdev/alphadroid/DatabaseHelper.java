package us.genetzky.alphaappdev.alphadroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nathansen on 5/16/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NGDatabase";
    public static final String DATABASE_TABLE1 = "PHONEMESSAGES";
    public static final String TABLE1_UID= "_id";
    public static final String TABLE1_NUMBER= "number";
    public static final String TABLE1_MESSAGE= "message";

    public static int DATABASE_VERSION = 1;

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PHONEMESSAGES (_id INTEGER PRIMARY KEY AUTOINCREMENT, number VARCHAR(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PHONEMESSAGES");
        onCreate(db);
    }
}
