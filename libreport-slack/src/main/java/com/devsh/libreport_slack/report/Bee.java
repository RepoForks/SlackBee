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

package com.devsh.libreport_slack.report;

import android.app.Application;
import android.content.Context;

import com.devsh.libreport_slack.report.model.Message;
import com.devsh.libreport_slack.report.storage.Bottle;
import com.devsh.libreport_slack.report.util.ALog;
import com.devsh.libreport_slack.reporter.IReporter;
import com.devsh.libreport_slack.report.data.App;

import java.util.Iterator;
import java.util.List;

public class Bee {
    private static String TAG = "Bee";

    private static Thread.UncaughtExceptionHandler mDefaultHandler;

    private static Context sContext;
    private static App sApp;
    private static BeeUncaughtExceptionHandler mUncaughtExceptionHandler;
    private static IReporter sReporter;
    private static Bottle sBottle;
    private static boolean sDebug;

    public static void init(Application application, IReporter reporter) {
        sReporter = reporter;
        sContext = application.getApplicationContext();
        sApp = new App(sContext);

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        mUncaughtExceptionHandler = new BeeUncaughtExceptionHandler(mDefaultHandler);

        ALog.setTAG(TAG);
        sendAllLog();
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
        ALog.setDebug(debug);
    }

    public static Context  getContext() {
        return sContext;
    }

    public static App getApp() {
        return sApp;
    }

    public static void sendAllLog() {
        sBottle = new Bottle(sContext);
        List<Message> listMsg = sBottle.loadPreviousLog();

        Iterator<Message> itr = listMsg.iterator();

        while (itr.hasNext()) {
            final Message msg = itr.next();
            if (msg != null) {
                ALog.i("message:" + msg);
                sReporter.report(msg.toReport());
            }
        }

    }

    public static void clearLog() {
        if (sBottle != null) {
            sBottle.clearAll();
        }
    }
}
