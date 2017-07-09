package com.example.roman.hookup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Roman on 10-Jul-17.
 */

public class TemporaryInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temporary_info);
        TextView view = (TextView) findViewById(R.id.received_info);
        Bundle bundle = getIntent().getExtras();
        view.setText(bundle.getString("string"));
    }
}
