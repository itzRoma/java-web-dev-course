package com.itzroma.kpi.semester5;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Task implements Callable<TaskResult> {
    private final File file;
    private final ExecutorService pool;

    public Task(File file, ExecutorService pool) {
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
            throw new RuntimeException("Error while deleting words from %s : %s".formatted(file.getAbsolutePath(), ex.getMessage()));
        }
        return deletedWords;
    }

    @Override
    public TaskResult call() {

        // If provided file is a valid txt file
        if (!file.isDirectory()) {
            int deletedWordsCount = findAndDelete(file);
            System.out.printf("%s : deleted %d words%n", file.getAbsolutePath(), deletedWordsCount);
            return new TaskResult(deletedWordsCount);
        }

        // If provided file is a directory
        List<Future<TaskResult>> dirResults = new ArrayList<>();

        for (File file : file.listFiles()) {
            if (file.isDirectory()) {
                System.out.printf("Starting new thread for %s directory%n", file.getAbsolutePath());
                Future<TaskResult> result = pool.submit(new Task(file, pool));
                dirResults.add(result);
            } else if (!file.isDirectory() && isTxtFile(file)) {
                System.out.printf("Starting new thread for %s file%n", file.getAbsolutePath());
                Future<TaskResult> result = pool.submit(new Task(file, pool));
                dirResults.add(result);
            }
        }

        TaskResult result = collectResults(dirResults);
        System.out.printf("Total result for %s : deleted %d words%n", file.getAbsolutePath(), result.getDeletedWords());
        return result;
    }

    private boolean isTxtFile(File file) {
        String[] fileParts = file.toPath().toString().split("\\.");
        return fileParts[fileParts.length - 1].equals("txt");
    }

    private TaskResult collectResults(List<Future<TaskResult>> dirResults) {
        TaskResult collected = new TaskResult();
        dirResults.forEach(labResultFuture -> {
            try {
                collected.addDeletedWords(labResultFuture.get().getDeletedWords());
            } catch (InterruptedException | ExecutionException ex) {
                System.out.printf("Error while collecting results: %s%n", ex.getMessage());
                throw new RuntimeException(ex);
            }
        });
        return collected;
    }
}
