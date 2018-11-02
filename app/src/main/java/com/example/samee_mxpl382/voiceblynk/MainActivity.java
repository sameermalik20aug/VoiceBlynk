package com.example.samee_mxpl382.voiceblynk;

import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tView1);
        imageButton = findViewById(R.id.imgButton1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(getBaseContext(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String q=result.get(0);
                    textView.setText(q);
                    try {
                        String search=URLEncoder.encode(q,"UTF-8");
                        String url="https://www.google.co.in/search?q="+search+"&rlz=1C1CHBF_enIN821IN821&oq="+search+"&aqs=chrome..69i57j0l5.1618j0j7&sourceid=chrome&ie=UTF-8";
                        CustomTabsIntent.Builder builder= new CustomTabsIntent.Builder();
                        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorAccent));
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(this, Uri.parse(url));


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}