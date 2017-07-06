package com.example.roman.hookup;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class ShareActivity extends AppCompatActivity {

    private Uri[] mFileUris = new Uri[10];
    // Flag to indicate that Android Beam is available
    boolean mAndroidBeamAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        nfcCheck();
        checkIfEnabledNFC();
        sendNFC();
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

    private void nfcCheck() {
        // NFC isn't available on the device
        PackageManager pm = this.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            /*
             * Disable NFC features here.
             * For example, disable menu items or buttons that activate
             * NFC-related features
             */

            // Android Beam file transfer isn't supported
        } else if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // If Android Beam isn't available, don't continue.
            mAndroidBeamAvailable = false;
            /*
             * Disable Android Beam file transfer features here.
             */

            // Android Beam file transfer is available, continue
        } else {
            NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            showToast("Your device has NFC feature.");
        }
    }

}
