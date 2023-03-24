package com.luckypeng.mock.spark;

import java.io.Serializable;

/**
 * @author coalchan
 */
public class MockParams implements Serializable {
    private String template;
    private int total;

    public MockParams(String template, int total) {
        this.template = template;
        this.total = total;
    }

    public String getTemplate() {
        return template;
    }

    public int getTotal() {
        return total;
    }
}
