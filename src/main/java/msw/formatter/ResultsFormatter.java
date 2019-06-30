package msw.formatter;


import msw.results.ResultNode;

import java.util.List;
import java.util.stream.IntStream;

public class ResultsFormatter {
    public static final String TAB = "   ";
    public static final String SEPARATOR = "   ";
    public static final int HEADER_LINE_SEPARATOR_SIZE = 40;
    public static final String HEADER_LINE_SEPARATOR_CHAR = "-";

    public ResultsFormatter() {
    }

    public String format(List<ResultNode> nodes) {
        StringBuilder sb = new StringBuilder();
        appendHeader(sb);
        format(sb, nodes, 0);
        return sb.toString();
    }

    private void format(StringBuilder sb, List<ResultNode> nodes, int level) {
        for (ResultNode resultNode : nodes) {
            IntStream.range(0, level).forEach(l -> sb.append(TAB));
            sb.append(resultNode.getTask())
                    .append(SEPARATOR).append(resultNode.getIntervalExcludingSubnodes())
                    .append(SEPARATOR).append(resultNode.getIntervalTotal());
            sb.append(System.lineSeparator());
            format(sb, resultNode.getResultNodes(), level + 1);
        }
    }

    private void appendHeader(StringBuilder sb) {
        sb.append("Task")
                .append(SEPARATOR).append("Interval")
                .append(SEPARATOR).append("Interval+Subnodes")
                .append(System.lineSeparator());
        IntStream.rangeClosed(0, HEADER_LINE_SEPARATOR_SIZE).forEach(n -> sb.append(HEADER_LINE_SEPARATOR_CHAR));
        sb.append(System.lineSeparator());
    }
}
