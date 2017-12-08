package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * This class is the Content Provider class for the app
 */
public class PetProvider extends ContentProvider {

    //Log tag
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    //Class-wide instance variable of the DB helper class
    private PetDBHelper mDbHelper;

    //Global variables for match case integers for Uri matcher
    private static final int PETS = 100;
    private static final int PET_ID = 101;

    //Global variable for the Uri matcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /**
     * The calls to addURI() go here, for all of the content URI patterns that the provider should recognize
     */
    static {
        //Adds the URI match for the whole pets table
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);

        //Adds the URI match for a single row of the pets table
        // # is used as a wildcard to support any integer for the specified row number
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);
    }

    /**
     * Initialise the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        //Create and initialise a PetDBHelper object to gain access to the pets database
        mDbHelper = new PetDBHelper(getContext());
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
