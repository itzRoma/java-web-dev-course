package com.itzroma.kpi.semester5;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LabRunner {
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a directory path: ");
        File file = new File(scanner.next());

        if (!file.exists() || !file.isDirectory())
            throw new IllegalStateException("Provided directory does not exist");

        ExecutorService pool = Executors.newCachedThreadPool();
        LabTask task = new LabTask(file, pool);
        Future<LabResult> result = pool.submit(task);

        try {
            System.out.println(result.get() + " in total");
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }

        pool.shutdown();
    }
}
