package com.example.roman.hookup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        Button receiveButton = (Button) findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Taking data from another phone...");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(ReceiveActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
