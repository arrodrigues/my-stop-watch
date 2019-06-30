package msw.results;

import msw.formatter.ResultsFormatter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class ResultsFormatterTest {

    @Test
    public void format(){
        List<ResultNode> nodes = new ArrayList<>();

        ResultNode node = new ResultNode("Task1", 100, 200);
        nodes.add(node);

        node
                .getResultNodes()
                .add(new ResultNode("Task1.1", 110, 150));

        ResultsFormatter formatter = new ResultsFormatter();
        String formatted = formatter.format(nodes);
        String format = removeTheFirstTwoLines(formatted);
        assertThat(format).isEqualTo(
                 "Task1   60   100\n"+
                 "   Task1.1   40   40");

    }

    private String removeTheFirstTwoLines(String format) {
        return Arrays
                .stream(format.split(System.lineSeparator()))
                .skip(2)
                .collect(Collectors.joining(System.lineSeparator()));
    }

}