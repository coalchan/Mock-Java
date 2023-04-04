package com.luckypeng.mock.cli;

import com.alibaba.fastjson.JSONObject;
import com.luckypeng.mock.cli.print.*;
import com.luckypeng.mock.core.Mock;
import com.luckypeng.mock.core.template.Rule;
import com.luckypeng.mock.core.util.JsonUtils;
import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author coalchan
 */
public class MockCli {
    public static final String CMD_LINE_SYNTAX = "mock-cli";

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("h", "help", false, "显示帮助信息");
        options.addRequiredOption("t", "template", true, "mock数据的JSON模板");
        options.addRequiredOption("c", "count", true, "生成数据条数");
        options.addOption("f", "format", true, "输出格式，支持 JSON、TSV、CSV");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(CMD_LINE_SYNTAX, options);
            } else {
                String template = cmd.getOptionValue("template");
                String format = cmd.getOptionValue("format", "TSV");
                int count = Integer.parseInt(cmd.getOptionValue("count"));
                mockData(template, format, count);
            }
        } catch (ParseException e1) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(CMD_LINE_SYNTAX, options);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public static void mockData(String template, String format, int count) throws IOException {
        JSONObject jsonTemplate = JsonUtils.toJson(template);
        Writer writer = new BufferedWriter(new OutputStreamWriter(System.out, UTF_8), 16384);
        List<String> fieldNames = jsonTemplate.keySet().stream()
                .map(key -> Rule.fromKey(key).getKey()).collect(Collectors.toList());
        try(OutputHandler outputHandler = new OutputHandler(createOutputPrinter(format, writer, fieldNames))) {
            for (int i = 0; i < count; i++) {
                outputHandler.printRow(Mock.mock(jsonTemplate));
            }
        }
    }

    private static OutputPrinter createOutputPrinter(String format, Writer writer, List<String> fieldNames) {
        switch (format) {
            case "TSV":
                return new TsvPrinter(fieldNames, writer);
            case "CSV":
                return new CsvPrinter(fieldNames, writer);
            case "JSON":
                return new JsonPrinter(fieldNames, writer);
            default:
                throw new RuntimeException(format + " not supported");
        }
    }
}
