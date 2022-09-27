package com.itzroma.kpi.semester5;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileUtils.generateFiles();
        } catch (IOException ex) {
            System.out.printf("Cannot generate files: %s%n", ex.getMessage());
        }

        System.out.printf("%nLab1 started%n%n");

        new Runner().run();

        System.out.printf("%nLab1 finished%n");
    }
}
