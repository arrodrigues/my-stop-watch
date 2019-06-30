# MyStopWatch


Just another stopwatch, but with support to subtasks.
It is not intended to microbenchmarking, just to mesure tasks that take some time (more then 10 millis).

#### Usage Example:
```java
import msw.formatter.ResultsFormatter;
import msw.times.MillisCurrentTimeProvider;

public class Example {
    public static void main(String... args) throws InterruptedException {
        MillisCurrentTimeProvider millisProvider = new MillisCurrentTimeProvider();
        MyStopWatch myStopWatch = new MyStopWatch(millisProvider);

        myStopWatch.begin("Task1");
        Thread.sleep(100);
        myStopWatch.end("Task1");

        myStopWatch.begin("Task2");
        Thread.sleep(200);
        myStopWatch.begin("Task2.1");
        Thread.sleep(50);
        myStopWatch.begin("Task2.1.1");
        Thread.sleep(300);
        myStopWatch.end("Task2.1.1");
        myStopWatch.end("Task2.1");
        myStopWatch.end("Task2");

        myStopWatch.begin("Task3");
        Thread.sleep(130);
        myStopWatch.end("Task3");

        ResultsFormatter resultsFormatter = new ResultsFormatter();
        System.out.println(resultsFormatter.format(myStopWatch.getResults()));
    }
}

```


#### Expected Output:

```console
Task   Interval   Interval+Subnodes
-----------------------------------------
Task1   100   100
Task2   200   550
   Task2.1   50   350
      Task2.1.1   300   300
Task3   130   130
```