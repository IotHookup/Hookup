package com.example.roman.hookup;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class ShareActivity extends AppCompatActivity {

    private Uri[] mFileUris = new Uri[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        checkIfEnabledNFC();
        Button shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Your data is sharing...");
                sendNFC();
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(ShareActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void sendNFC() {
         /*
         * Create a list of URIs, get a File,
         * and set its permissions
         */

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getExternalFilesDir("saveJsonFolder");
        File requestFile = new File(directory, "save.json");
        requestFile.setReadable(true, false);

        // Get a URI for the File and add it to the list of URIs
        Uri fileUri = Uri.fromFile(requestFile);
        if (fileUri != null) {
            mFileUris[0] = fileUri;
            showToast("Data was transferred!");
        } else {
            Log.e("My Activity", "No File URI available for file.");
            showToast("Something went wrong.");
        }

    }

    private void checkIfEnabledNFC() {
        Context context = getApplicationContext();
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter != null && !adapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return "
                    + "to the application!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
    }

}
