package com.example.acerv17nitro.directoryapp3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class VeriTabani extends SQLiteOpenHelper {
    private static final String TABLO_KISILER = "kisiler";
    private static final String ROW_ID = "id";
    private static final String ROW_AD = "ad";
    private static final String ROW_SOYAD = "soyad";
    private static final String ROW_TEL = "tel";

    public VeriTabani(Context context) {
        super(context, "Rehber", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLO_KISILER + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_AD + " TEXT NOT NULL, "
                + ROW_SOYAD + " TEXT NOT NULL, "
                + ROW_TEL + " TEXT NOT NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_KISILER);
        onCreate(db);
    }

    public void VeriEkle(String ad, String soyad, String tel){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_AD, ad);
            cv.put(ROW_SOYAD, soyad);
            cv.put(ROW_TEL, tel);
            db.insert(TABLO_KISILER, null,cv);
        }catch (Exception e){
        }
        db.close();
    }
    public List<String> VeriListele(){
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_ID,ROW_AD,ROW_SOYAD,ROW_TEL};
            Cursor cursor = db.query(TABLO_KISILER, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                veriler.add(cursor.getInt(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2)
                        + " - "
                        + cursor.getString(3));
            }
        }catch (Exception e){
        }
        db.close();
        return veriler;
    }
    public void VeriSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = ROW_ID + " = " + id ;
            db.delete(TABLO_KISILER,where,null);
            db.execSQL("delete from sqlite_sequence where name='kisiler' ");
        }catch (Exception e){
        }
        db.close();
    }
    public void VeriDuzenle(int id, String ad, String soyad, String tel){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_AD, ad);
            cv.put(ROW_SOYAD, soyad);
            cv.put(ROW_TEL, tel);
            String where = ROW_ID +" = '"+ id + "'";
            db.update(TABLO_KISILER,cv,where,null);
        }catch (Exception e){
        }
        db.close();
    }
}
