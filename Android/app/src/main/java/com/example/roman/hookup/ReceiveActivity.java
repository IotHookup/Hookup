package com.example.roman.hookup;

import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiveActivity extends AppCompatActivity {

    private Button closeButton;

    private TextView fullNameText;
    private TextView numberText;
    private TextView faceText;
    private TextView instaText;
    private TextView vkText;

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newInstagramProfileIntent(PackageManager pm, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                final String username = url.substring(url.lastIndexOf("/") + 1);
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                intent.setPackage("com.instagram.android");
                return intent;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        intent.setData(Uri.parse(url));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        setTitle(R.string.receive_label);


        fullNameText = (TextView) findViewById(R.id.fullNameEdit);
        numberText = (TextView) findViewById(R.id.phoneNumberEdit);
        faceText = (TextView) findViewById(R.id.facebookEdit);
        instaText = (TextView) findViewById(R.id.instaEdit);
        vkText = (TextView) findViewById(R.id.vkEdit);

        Bundle bundle = getIntent().getExtras();
        String info = bundle.getString("string");
        parse(info);


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
                String login = faceText.getText().toString();
                login = "https://www.facebook.com/" + login;
                PackageManager pm = getPackageManager();
                startActivity(newFacebookIntent(pm, login));
            }
        });

        instaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = instaText.getText().toString();
                login = "https://www.instagram.com/" + login;
                PackageManager pm = getPackageManager();
                startActivity(newInstagramProfileIntent(pm, login));
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
        fullNameText = (TextView) findViewById(R.id.fullNameEdit);
        numberText = (TextView) findViewById(R.id.phoneNumberEdit);
        faceText = (TextView) findViewById(R.id.facebookEdit);
        instaText = (TextView) findViewById(R.id.instaEdit);
        vkText = (TextView) findViewById(R.id.vkEdit);

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

        String firstName = obj.getString("full_name");
        fullNameText.setText(firstName);
        String number = obj.getString("number");
        numberText.setText(number);
        String faceLogin = obj.getString("facebook_login");
        faceText.setText(faceLogin);
        String instaLogin = obj.getString("insta_login");
        instaText.setText(instaLogin);
        String vkLogin = obj.getString("vk_login");
        vkText.setText(vkLogin);

        parsedData.put("full_name", firstName);
        parsedData.put("number", number);
        parsedData.put("facebook_login", faceLogin);
        parsedData.put("insta_login", instaLogin);
        parsedData.put("vk_login", vkLogin);

        return parsedData;
    }

    private void showToast(String message) {
        Toast.makeText(ReceiveActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void parse(String info) {
        String[] array = new String[5];
        int i = 0;
        Pattern p = Pattern.compile("\\:(.*?)\\;");
        Matcher m = p.matcher(info);
        while (m.find()) {
            array[i] = m.group(1);
            i++;
        }
        fullNameText.setText(array[0]);
        numberText.setText(array[1]);
        faceText.setText(array[2]);
        instaText.setText(array[3]);
        vkText.setText(array[4]);
    }
}

