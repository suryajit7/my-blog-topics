package com.framework.util;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class PathFinder {

    @SneakyThrows
    public static Path getFilePathForFile(String filename) {
        try (Stream<Path> stream = Files.walk(Paths.get("src"))) {
            log.info("Looking for filepath for given filename: ".concat(filename));
            return stream
                    .filter(file -> !Files.isDirectory(file) && file.getFileName().startsWith(filename))
                    .findFirst().get();
        }
    }



}
