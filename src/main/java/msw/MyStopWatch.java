package msw;

import msw.results.ResultNode;
import msw.times.CurrentTimeProvider;

import java.util.*;

public class MyStopWatch {
    private final CurrentTimeProvider currentTimeProvider;
    private final List<ResultNode> results = new ArrayList<>();
    private LinkedList<ResultNode> nestedPath = new LinkedList<>();

    public MyStopWatch(CurrentTimeProvider currentTimeProvider) {
        this.currentTimeProvider = currentTimeProvider;
    }

    public void begin(String task) {
        ResultNode node = new ResultNode(task, currentTimeProvider.currentTime());
        List<ResultNode> resultNodes =
                nestedPath.isEmpty() ? results : nestedPath.getLast().getResultNodes();
        resultNodes.add(node);
        nestedPath.addLast(node);
    }

    public void end(String task) {
        ResultNode last = nestedPath.getLast();
        if (!Objects.equals(last.getTask(), task)) {
            throw new IllegalStateException("The task "+task+ " was not started yet, so it can not be ended.");
        }
        last.setEndTime(currentTimeProvider.currentTime());
        nestedPath.removeLast();
    }

    public List<ResultNode> getResults() {
        return Collections.unmodifiableList(results);
    }
}

