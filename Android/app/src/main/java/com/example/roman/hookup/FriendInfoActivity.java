package com.example.roman.hookup;

import android.content.ContextWrapper;
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
        try {
            readJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}

