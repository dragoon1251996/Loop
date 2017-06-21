package com.t3h.whiyew.loophabit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Whiyew on 27/04/2017.
 */

public class Data extends SQLiteOpenHelper implements Serializable {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "japan.db";
    public static final String TABLE_JAPAN = "japan";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUESITION = "suggest";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_REPEAR = "repeat";
    public static final String COLUMN_GIO = "gio";
    public static final String COLUMN_PHUT = "phut";
    public static final String COLUMN_NGAY = "ngay";
    public static final String KEY_ID = "id";
    //    public static final String COLUMN_TIME = "time";
//    public static final String COLUMN_IMAGE = "image";

    private SQLiteDatabase database;
    private Context context;
    public static final String PATH= Environment.getDataDirectory()+"/data/com.t3h.whiyew.loophabit/databases/"+DATABASE_NAME;

    public Data(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        copyDataBase(context);
    }
    private void openDataBase(){
        database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
    }
    private void copyDataBase(Context context){
        try{

            File file= new File(PATH);
            if(file.exists()){
                return;

            }
            File parent=file.getParentFile();
            parent.mkdirs();
            FileOutputStream outputStream= new FileOutputStream(file);
            InputStream inputStream= context.getAssets().open(DATABASE_NAME);
            byte[] b=new byte[1024];
            int count=inputStream.read(b);
            while (count!=-1){
                outputStream.write(b,0,count);
                count=inputStream.read(b);
            }
            inputStream.close();
            outputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public ArrayList<NameHabit> getAllContacts() {
        ArrayList<NameHabit> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_JAPAN;

         database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NameHabit contact = new NameHabit(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
            contactList.add(contact);
            cursor.moveToNext();
        }
        cursor.close();

        // return contact list
        return contactList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creat_table = "CREATE TABLE " + TABLE_JAPAN + "(\""
                +  KEY_ID + "\" INTEGER PRIMARY KEY   AUTOINCREMENT,\""+COLUMN_NAME + "\" TEXT,\"" + COLUMN_QUESITION + "\" TEXT,\"" + COLUMN_NGAY + "\" TEXT,\"" + COLUMN_COLOR + "\" INTEGER,\"" + COLUMN_REPEAR + "\" INTEGER,\"" + COLUMN_GIO + "\" INTEGER,\"" + COLUMN_PHUT + "\" INTEGER" + ")";
        db.execSQL(creat_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JAPAN);
        onCreate(db);
    }

    public Cursor getDataAll() {
        ArrayList<NameHabit> japen = new ArrayList<>();
         database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_JAPAN, new String[]{COLUMN_NAME, COLUMN_QUESITION, COLUMN_COLOR, COLUMN_REPEAR, COLUMN_GIO, COLUMN_PHUT, COLUMN_NGAY
        }, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor;
        } else {
            return null;
        }
    }

    public void addJapan(NameHabit japan) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, japan.getTenThuocTinh());
        contentValues.put(COLUMN_QUESITION, japan.getQuestision());
        contentValues.put(COLUMN_COLOR, japan.getColor());
        contentValues.put(COLUMN_REPEAR, japan.getRepeat());
        contentValues.put(COLUMN_GIO, japan.getGio());
        contentValues.put(COLUMN_PHUT, japan.getPhut());
        contentValues.put(COLUMN_NGAY, japan.getNgay());
        database = this.getReadableDatabase();
        database.insert(TABLE_JAPAN, null, contentValues);
        database.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_JAPAN;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        return cursor.getCount();
    }
    public int  update(NameHabit habit){
        ContentValues  contentValues= new ContentValues();
        contentValues.put(KEY_ID, habit.getId());
        contentValues.put(COLUMN_NAME,habit.getTenThuocTinh());
        contentValues.put(COLUMN_QUESITION,habit.getQuestision());
        contentValues.put(COLUMN_COLOR,habit.getColor());
        contentValues.put(COLUMN_REPEAR,habit.getRepeat());
        contentValues.put(COLUMN_GIO,habit.getGio());
        contentValues.put(COLUMN_PHUT,habit.getPhut());
        contentValues.put(COLUMN_NGAY,habit.getNgay());
//        openDataBase();
//        database=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        database=this.getWritableDatabase();
        String[] whereAgr={habit.getId()+""};
        int rows= database.update(TABLE_JAPAN,contentValues,KEY_ID+ " = ?",whereAgr);
        database.close();
        return rows;

    }
    public int delete(NameHabit habit){
        database=this.getWritableDatabase();
        String[] whereAgr={habit.getId()+""};
        int rows= database.delete(TABLE_JAPAN,KEY_ID+ " = ?",whereAgr);
        database.close();
        return rows;
    }
}
