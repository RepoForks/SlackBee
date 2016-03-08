package com.devsh.crashreportslackandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.devsh.libreport_slack.reporter.IReporter;
import com.devsh.libreport_slack.reporter.SlackReporter;
import com.devsh.libreport_slack.report.Bee;

public class MainActivity extends AppCompatActivity {

    private String apiKey = "T0DJ86CLB/B0Q8V7ET0/74M6oWZIbxPcyAwK3fhxjfLh";
    private String crashName = "SlackBee";
    private String iconUrl = "https://avatars0.githubusercontent.com/u/2666166?v=3&s=460";

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
