package learn.rs.bdsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="BD.db";
    public static final String TABLE_NAME="DONORS";
    public static final String COL_0="ID";
    public static final String COL_1 ="Name";
    public static final String COL_2="Age";
    public static final String COL_3="Phone";
    public static final String COL_4="Blood_Group";
    public static final String COL_5="Last_Donated";
    public static final String COL_6="Gender";
    public static final String COL_7="Sector";
    public static final String COL_8="Address";
    public static final String COL_9="Password";


    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + TABLE_NAME +
                " (ID INTEGER, Name TEXT, Age TEXT, Phone TEXT PRIMARY KEY, Blood_Group TEXT, Last_Donated TEXT, Gender TEXT, Sector TEXT, Address VARCHAR(50), Password TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String age, String phone, String blood, String last, String gender, String sector, String address, String password)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
//        contentValues.put(COL_0, ID);
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, age);
        contentValues.put(COL_3, phone);
        contentValues.put(COL_4, blood);
        contentValues.put(COL_5, last);
        contentValues.put(COL_6, gender);
        contentValues.put(COL_7, sector);
        contentValues.put(COL_8, address);
        contentValues.put(COL_9, password);

        long result= db.insert(TABLE_NAME,null,contentValues);

        if(result==-1)
            return false;
        else
            return true;

    }

    public Cursor loginRequest (String id, String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor res=db.rawQuery("select * from '" +TABLE_NAME + "' where Phone = '" + id + "' and Password = '" + password + "'"   ,null  );
        return res;
    }

    public Cursor getresults(String blood, String sec)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " +TABLE_NAME +" where Sector = '" + sec + "' and Blood_Group = '" + blood + "'" ,null );
        return res;
    }

    public boolean uprofile(String name, String age, String phone, String blood, String last, String gender, String sector, String address, String password, String original_ph)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, age);
        contentValues.put(COL_3, phone);
        contentValues.put(COL_4, blood);
        contentValues.put(COL_5, last);
        contentValues.put(COL_6, gender);
        contentValues.put(COL_7, sector);
        contentValues.put(COL_8, address);
        contentValues.put(COL_9, password);
        db.update(TABLE_NAME,contentValues, "Phone = ?",new String[] { original_ph });
        return true;

    }

}
