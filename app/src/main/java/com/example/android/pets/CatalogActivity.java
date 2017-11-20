package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * This is the MainActivity, it displays a list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    //Class-wide instance variable of the DB helper class
    private PetDBHelper mDbHelper;

    /**
     * Override method that deals with the creation of this activity
     * @param savedInstanceState - if any non-persistent data is stored, this brings it back in if
     * the activity needs to be recreated (e.g. an orientation change)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Add a listener to the FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the EditorActivity
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        //Create an instance of the PetDBHelper class
        mDbHelper = new PetDBHelper(this);
        //Refresh the page with the latest DB results
        displayDatabaseInfo();
    }

    /**
     * Override method for when the activity starts, but has already been created
     * e.g. returning from another activity
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Show the latest DB results
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of the DB
     */
    private void displayDatabaseInfo() {
        //To access the database, the subclass of SQLiteOpenHelper is instantiated and is passed the context: the current activity
        PetDBHelper mDbHelper = new PetDBHelper(this);

        //Create and/or open a database object to read from it
        //If the database already exists a connection to it will be established, as opposed to overwriting it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //The projection (the columns to return)
        String[] projection = {
                PetsEntry._ID,
                PetsEntry.COLUMN_PET_NAME,
                PetsEntry.COLUMN_PET_BREED,
                PetsEntry.COLUMN_PET_GENDER,
                PetsEntry.COLUMN_PET_WEIGHT
        };
        
        //An exselection (the WHERE part of the SQL query)
        //String selection = PetsEntry._ID + "=?";
        //The selection arguments, seperated to prevent an SQL injection
        //String[] selectionArgs = {"1"};

        //Get a Cursor that returns all columns and rows from the pets table
        Cursor cursor = db.query(
                PetsEntry.TABLE_NAME, //The table to query
                projection, //The columns to return
                null, //The columns for the WHERE clause
                null, //The values for the WHERE clause
                null, //Group by
                null, //Filter by
                null //Sort/order by
        );

        //Get the TextView as an object
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        
        //Use a try:finally block so that the cursor can be closed
        try {
            //Create a header in the Text View that looks like this:
            //
            //The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            //In the while loop below, iterate through the rows of the cursor and display
            //the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            //Use append so that more text can be added without the need for extra variables
            displayView.append(
                    PetsEntry._ID + " - " +
                            PetsEntry.COLUMN_PET_NAME + " - " +
                            PetsEntry.COLUMN_PET_BREED + " - " +
                            PetsEntry.COLUMN_PET_GENDER + " - " +
                            PetsEntry.COLUMN_PET_WEIGHT + "\n"
            );

            //Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PetsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetsEntry.COLUMN_PET_WEIGHT);

            //Iterate through each returned row in the cursor, one at a time
            while (cursor.moveToNext()) {
                // Use the indexes above to extract the String or Int value of the word at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);

                //Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" +
                        currentID + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        currentGender + " - " +
                        currentWeight
                ));
            }
        } finally {
            //Close the cursor now that's it finished with. This releases all its resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Method that inserts a new pet into the database
     */
    private void insertPet() {
        //Create an instance of the writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create the key:value pairs
        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetsEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetsEntry.COLUMN_PET_GENDER, PetsEntry.GENDER_MALE);
        values.put(PetsEntry.COLUMN_PET_WEIGHT, 7);

        //Insert the data into a new row, with the row ID being returned
        long newRowId = db.insert(PetsEntry.TABLE_NAME, null, values);

        Log.i("Catalog activity", "Row ID: " + newRowId);
    }

    /**
     * Override method that creates the menu options
     *
     * @param menu - the menu that should be passed in
     * @return - the inflated menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    /**
     * Override method that handles the selection of the menu items
     *
     * @param item - the particular menu item that the user has selected
     * @return - the actions that clicking on the menu item triggers
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Switch statement for when a user clicks on an overflow menu item
        switch (item.getItemId()) {
            //Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            //Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                //Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
