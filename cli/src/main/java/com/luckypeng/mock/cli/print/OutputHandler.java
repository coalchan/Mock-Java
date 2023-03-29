package com.luckypeng.mock.cli.print;

import com.alibaba.fastjson.JSONObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.requireNonNull;

/**
 * @author coalchan
 */
public final class OutputHandler implements Closeable {
    private final AtomicBoolean closed = new AtomicBoolean();
    private final OutputPrinter printer;

    public OutputHandler(OutputPrinter printer) {
        this.printer = requireNonNull(printer, "printer is null");
    }

    @Override
    public void close() throws IOException {
        if (!closed.getAndSet(true)) {
            printer.finish();
        }
    }

    public void printRow(JSONObject row) throws IOException {
        printer.printRow(row);
    }
}
