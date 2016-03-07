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

package com.devsh.libreport_slack.report.storage;

import android.content.Context;

import com.devsh.libreport_slack.report.model.Message;
import com.devsh.libreport_slack.report.util.ALog;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Bottle {
    private final String SEPARATE = "/";

    private final File mFile;
    private final String mFilePath;

    public Bottle(Context context) {
        mFile = context.getFilesDir();
//        mFile = Environment.getExternalStorageDirectory();
        mFilePath = mFile.getAbsolutePath() + SEPARATE;
    }

    public List<Message> loadPreviousLog() {
        List<Message> listMsg = new ArrayList<>();

        String[] list = getList(mFile);
        for (String elem : list) {
            String filePath = mFilePath + elem;
            Message msg = null;
            try {
                msg = readLog(filePath);
            } catch (IOException e) {
                deleteLog(filePath);
            }
            listMsg.add(msg);
        }
        return listMsg;
    }

    public void clearAll() {
        String[] list = getList(mFile);
        String dirPath = mFile.getAbsolutePath() + SEPARATE;
        for (String elem : list) {
            String filePath = dirPath + elem;
            deleteLog(filePath);
        }
    }

    private String[] getList(File dir) {
        if(dir != null && dir.exists()) {
            return dir.list();
        }
        return null;
    }

    private String getFileName() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss", Locale.getDefault() );
        Date currentTime = new Date ();
        return "Log_" + mSimpleDateFormat.format ( currentTime );
    }

    public void saveLog(Message msg) {
        try {
            PrintWriter printWriter = new PrintWriter(mFilePath + getFileName());
            Gson gson = new Gson();
            String json = gson.toJson(msg);
            printWriter.write(json);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Message readLog(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        Gson gson = new Gson();
        Message msg = null;
        try {
            msg = gson.fromJson(fileData.toString(), Message.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    private void deleteLog(String filename) {
        File f = new File(filename);

        if (f.delete()) {
            ALog.i("delete file : success");
        } else {
            ALog.i("delete file : fail");
        }
    }
}
