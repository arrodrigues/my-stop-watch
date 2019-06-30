package msw.formatter;


import msw.results.ResultNode;

import java.util.List;
import java.util.stream.IntStream;

public class ResultsFormatter {
    public static final String TAB = "   ";
    public static final String SEPARATOR = "   ";
    private final List<ResultNode> nodes;

    public ResultsFormatter(List<ResultNode> nodes) {
        this.nodes = nodes;
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        format(sb, nodes, 0);
        return sb.toString();
    }

    private void format(StringBuilder sb, List<ResultNode> nodes, int level) {
        for (ResultNode resultNode : nodes) {
            IntStream.range(0, level).forEach(l -> sb.append(TAB));
            sb.append(resultNode.getTask())
                    .append(SEPARATOR).append(resultNode.getIntervalTotal())
                    .append(SEPARATOR).append(resultNode.getIntervalExcludingSubnodes());
            sb.append(System.lineSeparator());
            format(sb, resultNode.getResultNodes(), level+1);
        }
    }
}
