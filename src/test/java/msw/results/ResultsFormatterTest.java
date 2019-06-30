package msw.results;

import msw.formatter.ResultsFormatter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ResultsFormatterTest {

    @Test
    public void formt1(){
        List<ResultNode> nodes = new ArrayList<>();

        ResultNode node = new ResultNode("Task1", 100, 200);
        nodes.add(node);

        node
                .getResultNodes()
                .add(new ResultNode("Task1.1", 110, 150));

        ResultsFormatter formatter = new ResultsFormatter(nodes);

        assertThat(formatter.format()).isEqualTo(
                 "Task1  100   60\n"+
                 "   Task1.1  40   40\n");

    }

}