package nfu.csie.newdrflower.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by barry on 2017/6/13.
 */

public class CoordinateDataBases extends SQLiteOpenHelper {
    private final static int _DBVersion = 1;
    private final static String _DBName = "coordinate.sqlite";
    private final static String _TableName1 = "FlowerCoordinate"; //<-- table name
    private SQLiteDatabase db;
    Context context;

    public CoordinateDataBases(Context context) {
        super(context, _DBName, null, _DBVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName1 + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_Picture TEXT," +
                "_Latitude DOUBLE," +
                "_Longitude DOUBLE" +
                ");";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE " + _TableName1;
        db.execSQL(SQL);
    }
}
