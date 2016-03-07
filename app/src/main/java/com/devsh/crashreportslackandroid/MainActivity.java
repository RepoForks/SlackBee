package com.devsh.crashreportslackandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devsh.libreport_slack.reporter.IReporter;
import com.devsh.libreport_slack.reporter.SlackReporter;
import com.devsh.libreport_slack.report.Bee;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private String apiKey = "SLACK-API-KEY";
    private String crashName = "CRASH-BOT-NAME";
    private String iconUrl = "http://www.example.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Slack Reporter
        IReporter reporter = SlackReporter.create(apiKey, crashName, iconUrl);
        Bee.init(getApplication(), reporter);

        // Crash Test
        Button btnCrash = (Button) findViewById(R.id.btnCrash);
        btnCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             testCrash();
            }
        });
    }

    private int testCrash() {
        return 2/ 0;
    }
}
