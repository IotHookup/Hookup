package com.example.roman.hookup;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText[] textViews;
    private Map<Integer, Boolean> textViewsClicked;
    private Uri imageUri;
    private ImageView cameraView;
    private int PICK_IMAGE = 123;
    private Bitmap camImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Lists for Edit Texts*/
        textViews = new EditText[6];
        textViewsClicked = new HashMap<>();

        /*Initialize the list*/
        textViews[0] = (EditText) findViewById(R.id.firstNameEdit);
        textViews[1] = (EditText) findViewById(R.id.lastNameEdit);
        textViews[2] = (EditText) findViewById(R.id.phoneNumberEdit);
        textViews[3] = (EditText) findViewById(R.id.facebookEdit);
        textViews[4] = (EditText) findViewById(R.id.instaEdit);
        textViews[5] = (EditText) findViewById(R.id.vkEdit);

        /*Three functional buttons.*/
        final Button saveButton = (Button) findViewById(R.id.saveEditDataButton);
        final Button shareButton = (Button) findViewById(R.id.shareToActivityButton);
        final Button receiveButton = (Button) findViewById(R.id.receiveButton);
        cameraView = (ImageView) findViewById(R.id.cameraView);

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        loadImageFromStorage();
        //saveData();

        for (int i = 0; i < textViews.length; i++) {

            String saved = sharedPref.getString(textViews[i].getId() + "", "");
            if ((textViews[i].getText().toString() != "")
                    && (textViews[i].getText().toString() != " ")
                    && (textViews[i].getText().toString() != "  ")) {
                textViews[i].setText(saved);
            }

            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(textViewsClicked.keySet().contains(v.getId())
                            && textViewsClicked.get(v.getId()))) {
                        ((EditText) v).setText("");
                        textViewsClicked.put(v.getId(), true);
                    }
                }
            });
        }

        /*Save the data.*/
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                showToast("Your data was saved");
            }
        });



        /*Share activity.*/
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShareActivity = new Intent(MainActivity.this, ShareActivity.class);
                startActivity(openShareActivity);
            }
        });

        /*Receive activity.*/
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openReceiveActivity = new Intent(MainActivity.this, ReceiveActivity.class);
                startActivity(openReceiveActivity);
            }
        });

        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public int saveData() {
        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i = 0; i < textViews.length; i++) {
            if ((textViews[i].getText().toString() != "")
                    && (textViews[i].getText().toString() != " ")
                    && (textViews[i].getText().toString() != "  ")) {
                editor.putString(textViews[i].getId() + "", textViews[i].getText().toString());
            }
        }
        editor.commit();
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK) && (requestCode == PICK_IMAGE)) {
            imageUri = data.getData();
            cameraView.setImageURI(imageUri);
            try {
                camImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                saveImageToInternalStorage(camImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String saveImageToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir

        File directory = cw.getExternalFilesDir("imageDir");
        // Create imageDir
        File myPath = new File(directory.toString(), "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage() {

        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getExternalFilesDir("imageDir");
            File f = new File(directory.toString(), "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            cameraView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
