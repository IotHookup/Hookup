package com.example.roman.hookup;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;

public class FriendInfoActivity extends AppCompatActivity {

    private Button closeButton;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView numberText;
    private TextView faceText;
    private TextView instaText;
    private TextView vkText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        setTitle("Received info");

        /*
        try {
            readJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        firstNameText = (TextView) findViewById(R.id.firstNameText);
        lastNameText = (TextView) findViewById(R.id.lastNameText);
        numberText = (TextView) findViewById(R.id.numberText);
        faceText = (TextView) findViewById(R.id.faceText);
        instaText = (TextView) findViewById(R.id.instaText);
        vkText = (TextView) findViewById(R.id.vkText);


        Bundle bundle = getIntent().getExtras();

        String info = "First name, Last name";
        Toast.makeText(getApplicationContext(), "Result is:" + regular(info), Toast.LENGTH_SHORT).show();


        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        numberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberText.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        faceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                String profile = faceText.getText().toString();
                showToast("Opening facebook app");
                try {
                    context.getPackageManager()
                            .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/" + profile))); //Trys to make intent with FB's URI
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/" + profile))); //catches and opens a url to the desired page
                }
            }
        });

        instaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Opening instagram app");
                String profile = instaText.getText().toString();
                Uri uri = Uri.parse("http://instagram.com/_u/" + profile);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/" + profile)));
                }
            }
        });

        vkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Opening vk app");
                String profile = vkText.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vkontakte://profile/" + profile));
                try {
                    int id = Integer.parseInt(profile);
                    try {
                        startActivity(intent);

                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://vk.com/" + id)));
                    }
                } catch (NumberFormatException nfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://vk.com/" + profile)));
                }
            }
        });

    }


    private HashMap<String, String> readJson() throws IOException, JSONException {
        firstNameText = (TextView) findViewById(R.id.firstNameText);
        lastNameText = (TextView) findViewById(R.id.lastNameText);
        numberText = (TextView) findViewById(R.id.numberText);
        faceText = (TextView) findViewById(R.id.faceText);
        instaText = (TextView) findViewById(R.id.instaText);
        vkText = (TextView) findViewById(R.id.vkText);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getExternalFilesDir("saveJsonFolder");
        File jsonFile = new File(directory.toString(), "save.json");
        FileInputStream stream = new FileInputStream(jsonFile);
        String jsonStr = null;
        FileChannel fc = stream.getChannel();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        jsonStr = Charset.defaultCharset().decode(bb).toString();
        JSONArray array = new JSONArray(jsonStr);

        HashMap<String, String> parsedData = new HashMap<>();

        JSONObject obj = array.getJSONObject(0);

        String firstName = obj.getString("first_name");
        firstNameText.setText(firstName);
        String lastName = obj.getString("second_name");
        lastNameText.setText(lastName);
        String number = obj.getString("number");
        numberText.setText(number);
        String faceLogin = obj.getString("facebook_login");
        faceText.setText(faceLogin);
        String instaLogin = obj.getString("insta_login");
        instaText.setText(instaLogin);
        String vkLogin = obj.getString("vk_login");
        vkText.setText(vkLogin);

        parsedData.put("first_name", firstName);
        parsedData.put("last_name", lastName);
        parsedData.put("number", number);
        parsedData.put("facebook_login", faceLogin);
        parsedData.put("insta_login", instaLogin);
        parsedData.put("vk_login", vkLogin);

        return parsedData;
    }


    private void showToast(String message) {
        Toast.makeText(FriendInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private String regular(String info) {
        boolean b = info.matches("First");
        String result = "";
        if (b) {
            result = "cool!";
        }
        return result;
    }
}

