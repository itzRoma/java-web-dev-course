package com.itzroma.kpi.semester5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class LabUtils {
    public static void generateFiles() throws IOException {
        File file1 = new File("lab1/src/main/resources/file1.txt");
        forceCreate(file1);
        writeText(file1, """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut euismod neque id sem consequat, a sodales ante tempus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Aenean nec erat tempor neque venenatis tincidunt. Nam in diam nec nisl facilisis efficitur ac eu purus. Phasellus egestas est id pulvinar fringilla. Aenean justo massa, ultrices quis tempor in, auctor a arcu. Pellentesque imperdiet tellus malesuada neque suscipit, vel laoreet ex tincidunt. Cras ullamcorper non leo id interdum. Pellentesque velit purus, lacinia ac enim at, consequat lobortis sem. Nunc pharetra, diam at ultricies rhoncus, arcu odio convallis lectus, a aliquet sem felis et orci. Pellentesque commodo tortor vitae purus porttitor bibendum. Maecenas vitae lectus dapibus, gravida dui nec, gravida nulla. Etiam eget lectus quis neque rhoncus lobortis. Curabitur ut accumsan augue, in tempor tortor. Morbi vitae tincidunt nunc. Nunc cursus finibus libero ac tristique.
                                
                Donec blandit turpis purus, id maximus est convallis ac. Morbi in interdum nibh. Vestibulum imperdiet in magna id convallis. Curabitur egestas nibh sed nisi interdum, at ultricies erat suscipit. Cras commodo id leo at tempus. Cras tempus tempus tortor et euismod. Interdum et malesuada fames ac ante ipsum primis in faucibus.
                """
        );

        File file2 = new File("lab1/src/main/resources/file2.txt");
        forceCreate(file2);
        writeText(file2, """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fermentum, ante ut egestas bibendum, lorem tortor faucibus eros, eget viverra lacus sem eget tellus. Curabitur vehicula et orci eu pellentesque. Sed bibendum, dui in maximus porta, metus tortor lobortis risus, ut dictum arcu ante luctus nisi. Nunc eget dui sit amet odio fringilla commodo eget a mauris. Suspendisse ut nunc et sem aliquet sodales at nec nisi. Sed vel urna convallis, efficitur lacus sit amet, tempus nibh. Donec malesuada elementum pretium. Integer et lorem aliquam, ullamcorper odio id, luctus metus. Sed commodo leo non neque dictum, vitae elementum velit commodo.
                                
                Maecenas condimentum lorem nec imperdiet auctor. Sed vel justo convallis arcu pulvinar maximus sit amet eget justo. Suspendisse potenti. Praesent fermentum lorem quis pulvinar feugiat. Nulla in egestas quam. Nulla quis placerat arcu. Nullam porttitor, mi ut pellentesque ultricies, dolor ex blandit ex, id rutrum neque magna a neque. Quisque vestibulum velit non imperdiet malesuada. Praesent vulputate urna sed justo sagittis auctor. Nunc ligula ex, rutrum ac porta eget, dignissim mollis dui. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Integer scelerisque eget ligula eu laoreet. Nunc vel mi neque. Morbi consectetur ultricies felis a mattis. Pellentesque ut urna sit amet ante tincidunt mollis. Nulla ornare justo urna, sit amet aliquet tellus maximus sed.
                """);

        File subdirFile1 = new File("lab1/src/main/resources/subdir/file1.txt");
        forceCreate(subdirFile1);
        writeText(subdirFile1, """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam nec dui consectetur, suscipit ex id, pharetra ex. Suspendisse felis nunc, viverra et purus nec, semper convallis tortor. Phasellus laoreet maximus ligula non feugiat. Vivamus congue nunc in fringilla hendrerit. Fusce tellus augue, tempor vitae pellentesque ut, elementum in lorem. Morbi sed magna mauris. In rhoncus volutpat pellentesque. In hendrerit tellus tempus risus volutpat, eget mollis augue lobortis.
                                
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu consectetur tortor. Duis et nulla quis diam condimentum semper. Pellentesque nunc ipsum, tempus vel diam nec, gravida scelerisque tortor. Sed ullamcorper rutrum tortor sed maximus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi ipsum sapien, fermentum sed nulla et, fringilla consectetur metus. Donec convallis mattis tincidunt. Sed hendrerit magna eget euismod viverra. Aenean pretium leo lorem, sed faucibus ipsum sodales et. Integer id dignissim dolor, eget tincidunt lacus. Aliquam laoreet lobortis dignissim. Curabitur purus diam, feugiat vitae gravida quis, facilisis ut risus. Curabitur luctus imperdiet sem, eget molestie purus tincidunt ut.
                """);

        System.out.println("All files were rewritten: P:\\kpi\\semester-5\\jwdc\\java-web-dev-course\\lab1\\src\\main\\resources");
    }

    private static void forceCreate(File file) throws IOException {
        if (file.exists()) {
            Files.delete(file.toPath());
        }
        Files.createFile(file.toPath());
    }

    private static void writeText(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
