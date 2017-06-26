package com.example.roman.hookup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        final Boolean[] firstNameEditClicked = {false};
        final String FIRST_NAME_EDIT = "first name";

        final EditText lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        final Boolean[] lastNameEditClicked = {false};
        final String LAST_NAME_EDIT = "second name";

        final EditText numberEdit = (EditText) findViewById(R.id.phoneNumberEdit);
        final Boolean[] numberEditClicked = {false};
        final String NUMBER_EDIT = "number";

        final EditText facebookEdit = (EditText) findViewById(R.id.facebookEdit);
        final Boolean[] facebookEditClicked = {false};
        final String FACEBOOK_EDIT = "facebook";

        final EditText instaEdit = (EditText) findViewById(R.id.instaEdit);
        final Boolean[] instaEditClicked = {false};
        final String INSTA_EDIT = "insta";

        final EditText vkEdit = (EditText) findViewById(R.id.vkEdit);
        final Boolean[] vkEditClicked = {false};
        final String VK_EDIT = "vk";

        final Button shareButton = (Button) findViewById(R.id.shareToActivityButton);
        final Button saveButton = (Button) findViewById(R.id.saveEditDataButton);

        final Button receiveButton = (Button) findViewById(R.id.receiveButton);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String firstNameSaved = sharedPref.getString(FIRST_NAME_EDIT, "First name");
        firstNameEdit.setText(firstNameSaved);

        lastNameEdit.setText(sharedPref.getString(LAST_NAME_EDIT, "Second name"));

        numberEdit.setText(sharedPref.getString(NUMBER_EDIT, "Number"));
        facebookEdit.setText(sharedPref.getString(FACEBOOK_EDIT, "Facebook login"));
        instaEdit.setText(sharedPref.getString(INSTA_EDIT, "Instagram login"));
        vkEdit.setText(sharedPref.getString(VK_EDIT, "VK login"));

        firstNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameEditClicked[0] == false) {
                    firstNameEdit.setText("");
                    firstNameEditClicked[0] = true;
                }
            }
        });
        lastNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNameEditClicked[0] == false) {
                    lastNameEdit.setText("");
                    lastNameEditClicked[0] = true;
                }
            }
        });
        numberEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEditClicked[0] == false) {
                    numberEdit.setText("");
                    numberEditClicked[0] = true;
                }
            }
        });

        facebookEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facebookEditClicked[0] == false) {
                    facebookEdit.setText("");
                    facebookEditClicked[0] = true;
                }
            }
        });
        instaEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instaEditClicked[0] == false) {
                    instaEdit.setText("");
                    instaEditClicked[0] = true;
                }
            }
        });

        vkEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vkEditClicked[0] == false) {
                    vkEdit.setText("");
                    vkEditClicked[0] = true;
                }
            }
        });


        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openReceiveActivity = new Intent(MainActivity.this, ReceiveActivity.class);
                startActivity(openReceiveActivity);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = MainActivity.this;
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if (firstNameEdit.getText().toString() != "First Name") {
                    editor.putString(FIRST_NAME_EDIT, firstNameEdit.getText().toString());
                }
                if (lastNameEdit.getText().toString() != "Last Name") {
                    editor.putString(LAST_NAME_EDIT, lastNameEdit.getText().toString());
                }
                if (numberEdit.getText().toString() != "Number") {
                    editor.putString(NUMBER_EDIT, numberEdit.getText().toString());
                }
                if (facebookEdit.getText().toString() != "Facebook login") {
                    editor.putString(FACEBOOK_EDIT, facebookEdit.getText().toString());
                }
                if (instaEdit.getText().toString() != "Instagram login") {
                    editor.putString(INSTA_EDIT, instaEdit.getText().toString());
                }
                if (vkEdit.getText().toString() != "Vk login") {
                    editor.putString(VK_EDIT, vkEdit.getText().toString());
                }
                editor.commit();

                showToast("Your data was saved");
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShareActivity = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(openShareActivity);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}
