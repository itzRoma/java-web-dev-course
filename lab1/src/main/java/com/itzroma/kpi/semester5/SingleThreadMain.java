package com.itzroma.kpi.semester5;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SingleThreadMain {
    public static void main(String[] args) {
        try {
            FileUtils.generateFiles();
        } catch (IOException e) {
            System.out.println("Cannot regenerate files");
        }

        Scanner scanner = new Scanner(System.in);
        File file;

        do {
            System.out.print("Enter a directory path: ");
            file = new File(scanner.nextLine());
            System.out.println();
        } while (!correctInputDir(file));

        File finalFile = file;
        FileVisitor<Path> fv = new FileVisitor<>() {
            private final Map<String, Integer> dirResults = new HashMap<>();

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.printf("%s - Entering directory %s%n", Thread.currentThread().getName(), dir);
                dirResults.put(dir.toString(), 0);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                int deletedWords = FileUtils.findAndDelete(file.toFile(), "\\b[a-zа-яA-ZА-Я]{3,5}\\b");
                System.out.printf("%s - Inside file %s deleted %d words%n", Thread.currentThread().getName(), file, deletedWords);

                int oldValue = dirResults.get(file.getParent().toString());
                dirResults.replace(file.getParent().toString(), oldValue + deletedWords);

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.printf("%s - Error while visiting file %s: %s%n", Thread.currentThread().getName(), file, exc.getMessage());
                throw exc;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.printf(
                        "%s - Exiting directory %s%n with total of %d deletion%n",
                        Thread.currentThread().getName(), dir, dirResults.get(dir.toString())
                );

                if (dir.equals(finalFile.toPath())) {
                    System.out.printf("Total deletion for %s are %d words%n", dir, dirResults.values().stream().reduce(Integer::sum).get());
                }

                if (exc != null) throw exc;
                return FileVisitResult.CONTINUE;
            }
        };

        long start = System.currentTimeMillis();

        try {
            Files.walkFileTree(file.toPath(), fv);
        } catch (IOException e) {
            System.out.println("Error while file walking");
        }

        System.out.printf("Total time: %d ms%n", System.currentTimeMillis() - start);
    }

    private static boolean correctInputDir(File file) {
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("Provided directory does not exist. Try again");
            return false;
        }
        return true;
    }
}
