package com.example.android.pets;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetContract.PetsEntry;

/**
 * This is the EditorActivity class, it allows the user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    //Class-wide instance variables
    private EditText mNameEditText; //EditText field to enter the pet's name
    private EditText mBreedEditText; //EditText field to enter the pet's breed
    private EditText mWeightEditText; //EditText field to enter the pet's weight
    private Spinner mGenderSpinner; //EditText field to enter the pet's gender
    private PetDBHelper mDbHelper; //The DB helper class
    private int mGender = 0; //Gender of the pet. The possible values are: 0-unknown, 1-male, 2-female

    /**
     * Override method for onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        
        //Set-up the spinner with its relevant options
        setupSpinner();

        //Create an instance of the PetDBHelper class
        mDbHelper = new PetDBHelper(this);
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        //Create an adapter for the spinner. The list options it will use are from the String array, it will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);

        //Specify the dropdown layout style - a simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        //Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetsEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetsEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetsEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            //Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    /**
     * Gets user input from the editor interface and save into the database
     */
    private void insertNewPet() {
        //Create an instance of the writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //Create the key:value pairs
        ContentValues values = new ContentValues();
        values.put(PetsEntry.COLUMN_PET_NAME, mNameEditText.getText().toString().trim());
        values.put(PetsEntry.COLUMN_PET_BREED, mBreedEditText.getText().toString().trim());
        values.put(PetsEntry.COLUMN_PET_GENDER, mGender);
        values.put(PetsEntry.COLUMN_PET_WEIGHT, Integer.parseInt(mWeightEditText.getText().toString().trim()));

        //Insert the data into a new row, with the row ID being returned
        long newRowId = db.insert(PetsEntry.TABLE_NAME, null, values);

        //Notify the user of the success/or not of the query
        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "Pet saved with ID: " + newRowId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error with saving pet", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Override method that creates the menu options
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu options from the res/menu/menu_editor.xml file.
        //This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * Override method that handles the selection of the menu items
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Switch statement for when a user clicks on an overflow menu item in the app bar
        switch (item.getItemId()) {
            //Respond to a click on the "Save" menu option
            case R.id.action_save:
                //Insert a new pet into the database
                insertNewPet();
                //Return to the previous page
                finish();
                return true;
            //Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                //Do nothing for now
                return true;
            //Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                //Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
