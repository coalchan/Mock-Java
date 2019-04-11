package com.luckypeng.mock.flink;

import com.luckypeng.mock.core.util.ObjectUtils;
import lombok.Data;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

/**
 * @author coalchan
 * @since 1.0.1
 */
public class PageViewTest {
    @Test
    public void test() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String template = ObjectUtils.fromFile("template/page-view.json");
        FlinkMockSource<PageView> source = new FlinkMockSource(template, PageView.class)
                .setRate(100).setMaxDelayMsecs(10);
        DataStream<PageView> dataStream = env.addSource(source);
        dataStream.print();
        env.execute();
    }
}

@Data
class PageView {
    private long eventTime;
    private int userId;
    private String ip;
    private String ua;
    private String referer;
    private String page;
    private String sessionId;
    private int duration;
}
