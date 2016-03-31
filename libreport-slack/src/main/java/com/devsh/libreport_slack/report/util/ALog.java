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

package com.devsh.libreport_slack.report.util;

import android.util.Log;

public class ALog {

    private static String TAG = "HoneyComb";
    private static int MAX_LENGTH = 2048;
    private static String PREFIX = "";
    private static boolean sDebug = true;

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    public static void setTAG(String tag) {
        TAG = tag;
    }

    private static String getPrefix(StackTraceElement callerElement) {
        String fileName = callerElement.getFileName();
        String methodName = callerElement.getMethodName();
        int lineNumber = callerElement.getLineNumber();

        if (fileName != null) {
            fileName = fileName.replace(".java", "");
        }

        return PREFIX + "[" +
                fileName + ":" +
                methodName + ":" +
                lineNumber + "]";
    }

    private static String replaceLineFeeds(String str) {
        if(str == null || str.length() == 0) {
            return null;
        }

        String clean = str.replace('\n', '_').replace('\r', '_');

        if(str.equals(clean) == false) {
            clean += " (Modified)";
        }
        return clean;
    }

    private static void print(LogFunction log, Object... obj) {
        try {
            // only debug mode.
            if (!isDebug()) {
                return;
            }

            Exception exception = new Exception();
            StackTraceElement callerElement = exception.getStackTrace()[1];
            String strString = obj.length == 0 ? "" : obj[0].toString();

            boolean isFirst = true;

            String clean = replaceLineFeeds(strString);

            while (clean != null && clean.length() > 0) {
                String strCurrent = clean;
                if (clean.length() > MAX_LENGTH) {
                    strCurrent = clean.substring(0, MAX_LENGTH);
                }

                if (isFirst) {
                    log.p(getPrefix(callerElement) + strCurrent);
                } else {
                    log.p(strCurrent);
                }

                if (clean.length() > MAX_LENGTH) {
                    clean = clean.substring(MAX_LENGTH, clean.length());
                } else {
                    clean = null;
                }
                isFirst = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(Object... obj) {
        print(new LogFunction() {
            @Override
            public void p(String str) {
                Log.i(TAG, str);
            }
        }, obj);
    }

    public static void w(Object... obj) {
        print(new LogFunction() {
            @Override
            public void p(String str) {
                Log.w(TAG, str);
            }
        }, obj);
    }

    public static void e(Object... obj) {
        print(new LogFunction() {
            @Override
            public void p(String str) {
                Log.e(TAG, str);
            }
        },  obj);
    }

    private interface LogFunction {
        void p(String str);
    }
}
