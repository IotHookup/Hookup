package com.example.roman.hookup;

import android.content.Intent;
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

        final EditText lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        final Boolean[] lastNameEditClicked = {false};

        final EditText numberEdit = (EditText) findViewById(R.id.phoneNumberEdit);
        final Boolean[] numberEditClicked = {false};

        final EditText facebookEdit = (EditText) findViewById(R.id.facebookEdit);
        final Boolean[] facebookEditClicked = {false};

        final EditText instaEdit = (EditText) findViewById(R.id.instaEdit);
        final Boolean[] instaEditClicked = {false};

        final EditText vkEdit = (EditText) findViewById(R.id.vkEdit);
        final Boolean[] vkEditClicked = {false};

        final Button shareButton = (Button) findViewById(R.id.shareToActivityButton);
        final Button saveButton = (Button) findViewById(R.id.saveEditDataButton);

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

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShareActivity = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(openShareActivity);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Your data was saved successfully!");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
