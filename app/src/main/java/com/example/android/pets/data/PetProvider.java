package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.pets.PetDBHelper;

/**
 * This class is the Content Provider class for the app
 */
public class PetProvider extends ContentProvider {

    //Log tag
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    //Class-wide instance variable of the DB helper class
    public PetDBHelper DbHelper;

    /**
     * Initialise the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        //Create and initialise a PetDBHelper object to gain access to the pets database
        DbHelper = new PetDBHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    /**
     * Insert new data into the provider with the given ContentValues
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Delete the data at the given selection and selection arguments
     */
    @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Returns the MIME type of data for the content URI
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
