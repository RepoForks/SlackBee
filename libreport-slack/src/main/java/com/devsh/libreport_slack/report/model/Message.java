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

package com.devsh.libreport_slack.report.model;

public class Message {

    public String os_api_version;
    public String app_version_code;
    public String model;
    public String manufacture;
    public String stack_trace;
    public String os_version;
    public String app_version_name;
    public boolean rooted;

    public String toReport() {
        return "os_version: " + os_version + "\n" +
                "os_api_version: " + os_api_version + "\n"+
                "app_version_name: " + app_version_name + "\n" +
                "app_version_code: " + app_version_code + "\n" +
                "manufacture: " + manufacture + "\n" +
                "model: " + model + "\n" +
                "rooted: " + rooted + "\n" +
                "stack_trace: " + stack_trace;
    }
}
