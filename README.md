# SlackBee
Android Crash Report to Slack Channel

# How to Use
```java

private String apiKey = "SLACK-API-KEY";
private String crashName = "CRASH-BOT-NAME";
private String iconUrl = "http://www.example.jpg";
...

  // Slack Reporter
  IReporter reporter = SlackReporter.create(apiKey, crashName, iconUrl);
  Bee.init(getApplication(), reporter);
```
