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

import com.devsh.libreport_slack.report.storage.Bottle;
import com.devsh.libreport_slack.report.data.Device;
import com.devsh.libreport_slack.report.model.Message;

public class BeeUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler mDefaultHandler;

    private static volatile boolean mCrashing = false;

    public BeeUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        mDefaultHandler = handler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, final Throwable e) {

        // Don't re-enter -- avoid infinite loops if crash-reporting crashes.
        if (mCrashing) return;
        mCrashing = true;

        Message crashMessage = getCrashMessage(e);
        Bottle bottle = new Bottle(Bee.getContext());
        bottle.saveLog(crashMessage);

        try {
            mDefaultHandler.uncaughtException(t, e);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    private String getStackTrace(Throwable e) {
        String result ="";
        result += e.getMessage() + "\n";

        StackTraceElement[] lists = e.getStackTrace();
        for(StackTraceElement list: lists) {
            result += list.toString() + "\n";
        }

        return result;
    }

    private Message getCrashMessage(Throwable e) {
        Message message = new Message();
        message.app_version_name = Bee.getApp().getVersionName();
        message.app_version_code = Bee.getApp().getVersionCode();

        message.manufacture = Device.getManufacture();
        message.model = Device.getModel();

        message.os_api_version = Device.getOSAPiVersion() + "";
        message.os_version = Device.getOSVersion() + "";

        message.rooted = Device.isRooted();
        message.stack_trace = getStackTrace(e);

        return message;
    }
}
