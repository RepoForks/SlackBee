# SlackBee
Android Crash Report to Slack Channel

# How to Use
```java
private String apiKey = "T0DJ86CLB/B0Q8V7ET0/74M6oWZIbxPcyAwK3fhxjfLh"; // web-hook-api key
private String crashName = "SlackBee";  // bot name
private String iconUrl = "https://avatars0.githubusercontent.com/u/2666166?v=3&s=460"; // image url
...

  // Slack Reporter
  IReporter reporter = SlackReporter.create(apiKey, crashName, iconUrl);
  Bee.init(getApplication(), reporter);
```
# Example Log

![](https://github.com/suhanlee/SlackBee/blob/master/demo_screenshot.png)

# License
```
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
 ```
