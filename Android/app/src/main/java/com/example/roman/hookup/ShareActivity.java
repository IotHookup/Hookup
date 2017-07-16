package com.example.roman.hookup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ShareActivity extends AppCompatActivity {

    // Flag to indicate that Android Beam is available
    boolean mAndroidBeamAvailable = false;
    String QRContent = "";
    private Uri[] mFileUris = new Uri[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Bundle bundle = getIntent().getExtras();
        QRContent = " First name: " + bundle.getString("full name") + "\n" + " Last name: "
                + bundle.getString("last name")  + "\n" + " Phone number: "
                + bundle.getString("number") + "\n"
                + " Facebook login: " + bundle.getString("facebook") + "\n" + " Instagram login: "
                + bundle.getString("insta") + "\n" + " VK login: " + bundle.getString("vk");
        generateQR();
    }

    private void showToast(String message) {
        Toast.makeText(ShareActivity.this, message, Toast.LENGTH_SHORT).show();
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

    private void generateQR() {
        // this is a small sample use of the QRCodeEncoder class from zxing
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(QRContent, BarcodeFormat.QR_CODE, 812, 812);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.QRImageView)).setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
