package com.luckypeng.mock.cli.print;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * @author coalchan
 */
public class TsvPrinter implements OutputPrinter {
    private final List<String> fieldNames;
    private final Writer writer;
    private boolean hasPrintHeader = false;

    public TsvPrinter(List<String> fieldNames, Writer writer) {
        this.fieldNames = requireNonNull(fieldNames, "fieldNames is null");
        this.writer = requireNonNull(writer, "writer is null");
    }

    @Override
    public void printRow(JSONObject row) throws IOException {
        List<String> values = row.values().stream().map(Object::toString).collect(Collectors.toList());
        printRow(values);
    }

    public void printRow(List<?> row) throws IOException {
        if (!hasPrintHeader) {
            hasPrintHeader = true;
            printRow(fieldNames);
        }
        writer.write(formatRow(row));
    }

    @Override
    public void finish() throws IOException {
        writer.flush();
    }

    private static String formatRow(List<?> row) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> iter = row.iterator();
        while (iter.hasNext()) {
            String s = iter.next().toString();

            for (int i = 0; i < s.length(); i++) {
                escapeCharacter(sb, s.charAt(i));
            }

            if (iter.hasNext()) {
                sb.append('\t');
            }
        }
        sb.append('\n');
        return sb.toString();
    }

    private static void escapeCharacter(StringBuilder sb, char c) {
        switch (c) {
            case '\0':
                sb.append('\\').append('0');
                break;
            case '\b':
                sb.append('\\').append('b');
                break;
            case '\f':
                sb.append('\\').append('f');
                break;
            case '\n':
                sb.append('\\').append('n');
                break;
            case '\r':
                sb.append('\\').append('r');
                break;
            case '\t':
                sb.append('\\').append('t');
                break;
            case '\\':
                sb.append('\\').append('\\');
                break;
            default:
                sb.append(c);
        }
    }
}
