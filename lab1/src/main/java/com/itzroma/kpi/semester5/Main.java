package com.itzroma.kpi.semester5;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            LabUtils.generateFiles();
        } catch (IOException ex) {
            System.out.println("Cannot generate files: " + ex.getMessage());
        }

        System.out.printf("%nLab1 started%n%n");

        new LabRunner().run();

        System.out.printf("%nLab1 finished%n");
    }
}
