/*
 * Copyright (C) 2015 Suhan Lee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devsh.libreport_slack.reporter;

import android.util.Log;

import com.devsh.libreport_slack.BuildConfig;
import com.devsh.libreport_slack.CrashMessage;
import com.devsh.libreport_slack.report.Bee;
import com.devsh.libreport_slack.report.util.ALog;
import com.devsh.libreport_slack.reporter.network.SlackReportService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlackReporter implements IReporter{

    private final static String TAG = "ReportManager";

    private static Retrofit sRetrofit;
    private static SlackReportService sService;
    private final String mApiKey;
    private final String mCrashName;
    private final String mIconUrl;

    public static SlackReporter create(String apiKey, String crashName, String iconUrl) {
        return new SlackReporter(apiKey, crashName, iconUrl);
    }

    private SlackReporter(String apiKey, String crashName, String iconUrl) {
        mIconUrl = iconUrl;
        mApiKey = apiKey;
        mCrashName = crashName;

        if (Bee.isDebug()) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            sRetrofit = new Retrofit.Builder()
                    .baseUrl("https://hooks.slack.com")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("https://hooks.slack.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        sService = sRetrofit.create(SlackReportService.class);
    }

    @Override
    public void report(String msg) {
        Call<String> report = sService.sendReport(mApiKey, new CrashMessage(mCrashName, mIconUrl, msg));
        report.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onSuccess");
                Bee.clearLog();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure");
                ALog.e(t);
            }
        });
    }
}
