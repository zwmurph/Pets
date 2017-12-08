package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The contract class holds all of the constants that will be used for the SQLite DB throughout the other classes
 * These include table & column names, as well as other constants
 */
public final class PetContract {
    //Private constructor
    private PetContract() {
    }

    //The content authority that helps identify the content provider
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    //This is the base content Uri that will be shared with every Uri associated with PetContract
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //This is the table name that will be appended to the base content Uri
    public static final String PATH_PETS = "pets";

    /**
     * Inner class that defines the table contents
     */
    public static class PetsEntry implements BaseColumns {
        //Constants for table name, headings & pet genders
        public static final String TABLE_NAME = "pets";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        //The full Uri using the constants declared above
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);
    }
}
