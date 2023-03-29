package com.luckypeng.mock.cli.print;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author coalchan
 */
public class JsonPrinter implements OutputPrinter {
    private final Writer writer;

    public JsonPrinter(List<String> fieldNames, Writer writer) {
        this.writer = requireNonNull(writer, "writer is null");
    }

    @Override
    public void printRow(JSONObject row) throws IOException {
        writer.write(row.toJSONString());
        writer.write("\n");
    }

    @Override
    public void finish() throws IOException {
        writer.flush();
    }
}
