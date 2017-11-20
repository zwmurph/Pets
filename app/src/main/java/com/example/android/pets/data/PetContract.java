package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * The contract class holds all of the constants that will be used for the SQLite DB throughout the other classes
 * These include table & column names, as well as other constants
 */
public final class PetContract {
    //Private constructor
    private PetContract() {
    }

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
    }
}
