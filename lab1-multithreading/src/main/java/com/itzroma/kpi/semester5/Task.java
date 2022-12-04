package com.itzroma.kpi.semester5;

import java.io.File;
import java.util.ArrayList;
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

    @Override
    public TaskResult call() {

        // If provided file is a valid txt file
        if (!file.isDirectory()) {
            int deletedWordsCount = FileUtils.findAndDelete(file, "\\b[a-zа-яA-ZА-Я]{3,5}\\b");
            System.out.printf("File %s : deleted %d words%n", file.getAbsolutePath(), deletedWordsCount);
            return new TaskResult(deletedWordsCount);
        }

        // If provided file is a directory
        List<Future<TaskResult>> dirResults = new ArrayList<>();

        for (File file : file.listFiles()) {
            if (file.isDirectory() || (!file.isDirectory() && isTxtFile(file))) {
                Future<TaskResult> result = pool.submit(new Task(file, pool));
                dirResults.add(result);
            }
        }

        TaskResult result = collectResults(dirResults);
        System.out.printf("Directory %s : deleted %d words in total%n", file.getAbsolutePath(), result.getDeletedWords());
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
