package com.andersenlab.file_rewriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileRewriter {
    private static final Logger logger = LogManager.getLogger(FileRewriter.class);
    private static final String INIT_REWRITER_EXCEPTION_TEXT = "Can't init file-rewriter";
    private static List<BufferedReader> readers = new ArrayList<>();
    private static BufferedWriter outputStream;
    private static List<BufferedReader> emptyReaders = new ArrayList<>();

    public void run(String[] sourceFileNames, String resultFileName) throws Exception {
        initReadersAndWriter(sourceFileNames, resultFileName);
        try {
            String currentString;
            while (readers.size() != 0) {
                for (BufferedReader reader: readers) {
                    currentString = reader.readLine();
                    if (currentString != null) {
                        writeLine(currentString, readers.size() == 1);
                    } else {
                        reader.close();
                        emptyReaders.add(reader);
                    }
                }
                clearEmptyReaders();
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        } finally {
            for (BufferedReader reader: readers) {
                reader.close();
            }
            outputStream.close();
        }
    }

    private static void initReadersAndWriter(String[] sourceFileNames, String resultFileName) {
        try {
            for (String fileName: sourceFileNames) {
                readers.add(new BufferedReader(new FileReader(fileName)));
            }
            outputStream = new BufferedWriter(new FileWriter(resultFileName));
        } catch (Exception e) {
            logger.error(INIT_REWRITER_EXCEPTION_TEXT + ": " + e.getMessage());
        }
    }

    private static void writeLine(String line, boolean isLastLine) throws Exception {
        outputStream.write(line);
        if (!isLastLine) {
            outputStream.newLine();
        }
    }

    private static void clearEmptyReaders() {
        for (BufferedReader emptyReader: emptyReaders) {
            readers.remove(emptyReader);
        }
        emptyReaders.clear();
    }
}
