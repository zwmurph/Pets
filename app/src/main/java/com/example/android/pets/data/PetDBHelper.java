package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * This is the schema (how the DB is created) for the database, where the SQLite commands are created
 * It extends the SQLiteOpenHelper which helps to manage the database CRUD commands
 */
public class PetDBHelper extends SQLiteOpenHelper {

    //Constants for the class, if the database schema is changed the version must be updated
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shelter.db";

    //Public constructor for the class
    public PetDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Override method that deals with the creation of the database, if it isn't already created
     * @param db - the database being dealt with and returned
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statement to CREATE the pets table, using the constants from the Contract class
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetsEntry.TABLE_NAME + "("
                + PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetsEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetsEntry.COLUMN_PET_BREED + " TEXT, "
                + PetsEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetsEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        //Execute the create statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    /**
     * Override method that deals with the upgrading (editing, dropping, updating etc.) of tables
     * @param sqLiteDatabase - the DB being dealt with
     * @param oldVersion - the previous version of the DB
     * @param newVersion - the new version of the DB if it's changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
