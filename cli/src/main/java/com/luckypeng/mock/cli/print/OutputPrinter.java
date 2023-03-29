package com.luckypeng.mock.cli.print;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @author coalchan
 */
public interface OutputPrinter {
    void printRow(JSONObject row) throws IOException;

    void finish() throws IOException;
}
