package com.itzroma.kpi.semester5;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class LabTask implements Callable<LabResult> {
    private final File file;
    private final ExecutorService pool;

    public LabTask(File file, ExecutorService pool) {
        this.file = file;
        this.pool = pool;
    }

    private int findAndDelete(File file) {
        int deletedWords = 0;
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))
        ) {
            StringBuilder result = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                deletedWords += Arrays.stream(line.split("\\W+")).filter(word -> word.length() >= 3 && word.length() <= 5).count();
                line = line.replaceAll("\\b[a-zа-яA-ZА-Я]{3,5}\\b", "");
                result.append(line);
            }

            writer.write(result.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return deletedWords;
    }

    @Override
    public LabResult call() {
        LabResult finalResult = new LabResult(file.getAbsolutePath());

        try {
            List<Future<LabResult>> results = new ArrayList<>();

            for (File file : file.listFiles()) {
                if (file.isDirectory()) {
                    LabTask task = new LabTask(file, pool);
                    Future<LabResult> result = pool.submit(task);
                    results.add(result);
                } else if (isTxtFile(file)) {
                    int deletedWords = findAndDelete(file);
                    finalResult.addDeletedWords(deletedWords);
                    System.out.println(file.getAbsolutePath() + " : deleted " + deletedWords + " words");
                }
            }
            for (Future<LabResult> result : results) {
                finalResult.addDeletedWords(result.get().getDeletedWords());
            }
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }

        return finalResult;
    }

    private boolean isTxtFile(File file) {
        String[] fileParts = file.toPath().toString().split("\\.");
        return fileParts[fileParts.length - 1].equals("txt");
    }
}
