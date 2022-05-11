# Bug: Tailing commons-io

## Error Description

When using `Tailer` on big log files, the implemented listener gets in his `handle(String line)`
method broken lines.

## Verification

To verify the error, I have created a [generated.log](src/main/resources/generated.log) which should
be parsed by the Tailer. The log looks like this:

```log
127.0.0.1 - - [1223] "POST /f861fe23-5c56-4bb1-9438-480a60619d96 HTTP/1.1"
127.0.0.1 - - [1223] "POST /54ffd37c-fab3-4c50-9021-35decab96e22 HTTP/1.1"
127.0.0.1 - - [1223] "POST /0cd02a3a-5aea-4dca-9720-5dd7fa746fd1 HTTP/1.1"
127.0.0.1 - - [1223] "POST /f8a0a767-224a-4415-bb04-1f244a082bde HTTP/1.1"
```

To validate the line a pattern was created:

```java
String PATTERN="(?<HostIp>[0-9.-]+) - - \\[(?<Timestamp>.+)] \"(?<Method>[A-Z]+) (?<Path>.+) HTTP/1.1\"";
```

When Tailer will parse a line, the line will be validated against the pattern, and if it doesn't
matches the line is logged and kept in the list:

```java
public class CommonsListener extends TailerListenerAdapter {

  private final List<String> brokenLines = new ArrayList<>();
  private final Pattern pattern;

  @Override
  public void handle(String line) {
    var matcher = pattern.matcher(line);

    if (!matcher.matches()) {
      log.error("Broken line: '{}'", line);
      brokenLines.add(line);
    }
  }
} 
```

Now when
executing [CommonsTailerTest.java](src/test/java/com/vscoding/tutorial/bug/tailor/control/CommonsTailerTest.java)
I get

```log
11:55:32.962 [main] ERROR com.vscoding.tutorial.bug.tailor.control.CommonsListener - Broken line: '1bd HTTP/1.1"'
11:55:32.962 [main] ERROR com.vscoding.tutorial.bug.tailor.control.CommonsListener - Broken line: '127.0.0.1 - - [1223] "POST /933edf66-1156-4a15-9b4a-TTP/1.1"'
11:55:32.962 [main] ERROR com.vscoding.tutorial.bug.tailor.control.CommonsListener - Broken line: '1fe HTTP/1.1"'
[...]
[ERROR] Failures: 
[ERROR]   CommonsTailerTest.testCommonsTailor:20 expected: <0> but was: <245>
```

## Solution

Found out, that the `Tailer.create(new File(path), listener);` already starts a trailer in a
different thread. So instead of

```java
  public void run(){
        TailerListener listener=new MyTailerListener();
        Tailer tailer=new Tailer(file,listener,delay);
        Thread thread=new Thread(tailer);
        thread.setDaemon(true); // optional
        thread.start();
        }
```

use

```java
  public void run(){
        Tailer.create(new File(path),listener);
        }
```

also the `Tailer` would tail the whole file, this means, he will iterate over each line over and
over again, instead of just parsing the new line. So if you have a file and just want to trail
additions use:

```java
  public void run(){
        Tailer.create(new File(config.getPath()),listener,tailingDelay,true);
        }
```

And if you want to process whole file and after that tail for new lines appended, you will have to
read the file first and then append `Tailer`. 
