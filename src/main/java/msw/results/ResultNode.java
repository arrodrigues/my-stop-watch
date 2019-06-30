package msw.results;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultNode {
    private final List<ResultNode> resultNodes = new ArrayList<>();
    private final String task;
    private final long beginTime;
    private long endTime = -1;

    public ResultNode(String task, long beginTime) {
        this.task = task;
        this.beginTime = beginTime;
    }

    public ResultNode(String task, long beginTime, long endTime) {
        this(task, beginTime);
        this.endTime = endTime;
    }

    public String getTask() {
        return task;
    }

    public List<ResultNode> getResultNodes() {
        return resultNodes;
    }

    public long getIntervalTotal(){
        return endTime - beginTime;
    }

    public long getIntervalExcludingSubnodes(){
        return endTime - beginTime - subNodesIntervalSum();
    }

    private Long subNodesIntervalSum() {
        return resultNodes
                .stream()
                .collect(Collectors.summingLong(ResultNode::getIntervalTotal));
    }

    @Override
    public String toString() {
        return "ResultNode{" +
                "resultNodes=" + resultNodes +
                ", task='" + task + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
