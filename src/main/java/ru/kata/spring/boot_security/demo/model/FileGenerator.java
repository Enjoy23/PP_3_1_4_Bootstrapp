package ru.kata.spring.boot_security.demo.model;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;

public class FileGenerator {

    //create file of given size with random content

    public static void main(String [] args) throws Exception {
        File file = new File("src/main/resources/random-file.txt");
        assert file.createNewFile();

        int megabytes = 71;
        int size = megabytes * 1024 * 1024;

        byte[] bytes = new byte[size];
        ThreadLocalRandom.current().nextBytes(bytes);

        Files.write(file.toPath(), bytes);
    }

}
