package com.itzroma.kpi.semester5;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        File file;

        do {
            System.out.print("Enter a directory path: ");
            file = new File(scanner.nextLine());
            System.out.println();
        } while (!correctInputDir(file));

        ExecutorService pool = Executors.newCachedThreadPool();

        System.out.printf("Starting thread for provided directory %s%n", file.getAbsolutePath());
        Task task = new Task(file, pool);

        Future<TaskResult> result = pool.submit(task);

        try {
            result.get();
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }

        pool.shutdown();
    }

    private boolean correctInputDir(File file) {
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("Provided directory does not exist. Try again");
            return false;
        }
        return true;
    }
}
