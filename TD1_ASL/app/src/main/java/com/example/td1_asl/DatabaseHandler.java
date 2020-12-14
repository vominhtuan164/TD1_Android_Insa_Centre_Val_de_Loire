package com.example.td1_asl;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * DATABASE NAME est "achatManager.db". Il est sauvegardé dans le /data/data/com.example.td1_asl/databases/achatManager.db
     * Créer la table "contacts"
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "achatManager.db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ITEM = "item";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    public static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_ITEM + " TEXT" + ")";

    /**
     * Créer la table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_CONTACTS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mettre à jour la table
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    /**
     * Ajouter un nouveau élément dans la table
     * @param achat
     */
    void addAchat(Achat achat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, achat.getName()); // Achat Name
        values.put(KEY_ITEM, achat.getItem()); // Achat Phone
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    /**
     * Récupérer tous des éléments dans la table
     * @return
     */
    public List<Achat> getAllAchats() {
        List<Achat> achatList = new ArrayList<Achat>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Achat achat = new Achat();
                achat.setID(Integer.parseInt(cursor.getString(0)));
                achat.setName(cursor.getString(1));
                achat.setItem(cursor.getString(2));
                // Adding achat to list
                achatList.add(achat);
            } while (cursor.moveToNext());
        }
        return achatList;
    }


}