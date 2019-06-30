package msw;

import msw.results.ResultNode;
import msw.times.CurrentTimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

public class MyStopWatchTest {
    private CurrentTimeProvider currentTimeProviderMock = Mockito.mock(CurrentTimeProvider.class);
    private MyStopWatch stopWatch;

    @Before
    public void setUp() {
        stopWatch = new MyStopWatch(currentTimeProviderMock);
    }

    @Test
    public void plainTasks() {
        when(currentTimeProviderMock.currentTime()).thenReturn(100L, 500L, 700L, 900L);

        stopWatch.begin("Task1");
        stopWatch.end("Task1");
        stopWatch.begin("Task2");
        stopWatch.end("Task2");

        List<ResultNode> resultNodes = stopWatch.getResults();
        assertThat(resultNodes).hasSize(2);
        assertThat(resultNodes)
                .extracting("task", "intervalTotal", "intervalExcludingSubnodes")
                .containsExactly(
                        tuple("Task1", 400L, 400L),
                        tuple("Task2", 200L, 200L));
    }

    @Test
    public void nestedTasks() {
        when(currentTimeProviderMock.currentTime())
                .thenReturn(100L, 500L, 700L, 900L, 1300L, 1350L, 1400L, 1500L);

        stopWatch.begin("Task1");     // 100L
        stopWatch.end("Task1");       // 500L
        stopWatch.begin("Task2");     // 700L
        stopWatch.begin("Task2.1");   // 900L
        stopWatch.begin("Task2.1.1"); //1300L
        stopWatch.end("Task2.1.1");   //1350L
        stopWatch.end("Task2.1");     //1400L
        stopWatch.end("Task2");       //1500L

        List<ResultNode> nodes = stopWatch.getResults();
        assertThat(nodes)
                .extracting("task", "intervalTotal", "intervalExcludingSubnodes")
                .containsExactly(
                        tuple("Task1", 400L, 400L),
                        tuple("Task2", 800L, 300L));

        ResultNode task2 = findTask(nodes, "Task2");
        List<ResultNode> task2Nodes = task2.getResultNodes();
        assertThat(task2Nodes).extracting("task", "intervalTotal", "intervalExcludingSubnodes")
                .containsExactly(
                        tuple("Task2.1", 500L, 450L));

        ResultNode task2_1 = findTask(task2Nodes, "Task2.1");
        assertThat(task2_1.getResultNodes()).extracting("task", "intervalTotal", "intervalExcludingSubnodes")
                .containsExactly(
                        tuple("Task2.1.1", 50L, 50L));
    }

    @Test
    public void invalidEnd() {
        when(currentTimeProviderMock.currentTime())
                .thenReturn(100L, 500L);

        stopWatch.begin("Task1");
        assertThatThrownBy(() -> stopWatch.end("Task2"))
                .hasMessageContaining("Task2")
                .isInstanceOf(IllegalStateException.class);

    }

    private ResultNode findTask(List<ResultNode> resultNodes, String task) {
        return resultNodes
                .stream()
                .filter(resultNode -> resultNode.getTask().equals(task))
                .findAny()
                .get();
    }

}
