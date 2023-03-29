package com.luckypeng.mock.cli.print;

import au.com.bytecode.opencsv.CSVWriter;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * @author coalchan
 */
public class CsvPrinter implements OutputPrinter {
    private final List<String> fieldNames;
    private final CSVWriter writer;

    private boolean hasPrintHeader = false;

    public CsvPrinter(List<String> fieldNames, Writer writer) {
        requireNonNull(fieldNames, "fieldNames is null");
        requireNonNull(writer, "writer is null");
        this.fieldNames = fieldNames;
        this.writer = new CSVWriter(writer);
    }

    @Override
    public void printRow(JSONObject row) throws IOException {
        if (!hasPrintHeader) {
            hasPrintHeader = true;
            writer.writeNext(toStrings(fieldNames));
        }
        List<String> values = row.values().stream().map(Object::toString).collect(Collectors.toList());
        writer.writeNext(toStrings(values));
        checkError();
    }

    @Override
    public void finish() throws IOException {
        writer.flush();
        checkError();
    }

    private void checkError() throws IOException {
        if (writer.checkError()) {
            throw new IOException("error writing to output");
        }
    }

    private static String[] toStrings(List<?> values) {
        String[] array = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            array[i] = values.get(i).toString();
        }
        return array;
    }
}
