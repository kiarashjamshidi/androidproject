package android.sxample.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG ="DatabaseHelper";
    public static final String Table_Name ="TrashDB";
    public static final String COL1 ="id";
    public static final String COL2 ="geo_latitude";
    public static final String COL3 ="geo_longitude";
    public static final String COL4 ="picture";
    public static final String COL5 ="type";




    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable ="CREATE TABLE " +Table_Name+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL2+ " float, "+COL3+" float, "+COL4 +" Text, "+COL5+" Text)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP  TABLE  "+ Table_Name);
        onCreate(db);

    }
    public boolean addData(String[] item){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2 ,item[0]);
        contentValues.put(COL3 ,item[1]);
        contentValues.put(COL4 ,item[2]);
        contentValues.put(COL5 ,item[3]);
        Log.d(TAG,"add Data : Adding "+item+ " to " +Table_Name);
        long result = db.insert(Table_Name,null,contentValues);
        if (result == -1 ){
            return false;

        }else {
            return true;

        }

    }
    public Cursor getData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor data=db.rawQuery("Select * from "+Table_Name,null);
        return data;
    }
}
