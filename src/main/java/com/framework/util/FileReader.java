package com.framework.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.framework.data.BaseEntity;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.framework.util.PathFinder.getFilePathForFile;


/**
 * Reusable File Reader utility capable of reading all types of files including, but not limited to text, csv, excel, json etc.
 * All methods accept a filename which would be automatically located by the PathFinder service.
 * All methods will either return a List of objects deserialized from given file or a List of String.
 */

public class FileReader {

    public File readFile(String filename) {
        return getFilePathForFile(filename).toFile();
    }


    public List<String> readTxtFile(String filename) throws IOException {
        return hasValidExtension(filename, "txt") ? Files.readAllLines(getFilePathForFile(filename)) : Arrays.asList("");
    }

    @SneakyThrows
    public BaseEntity readJsonFile(String filename) {
        return hasValidExtension(filename, "json") ? new ObjectMapper().readValue(getFilePathForFile(filename).toFile(), BaseEntity.class) : BaseEntity.builder().build();
    }


    public <T> List<T> readCsvFile(String filename, Class<T> type) throws IOException {

        CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
        ObjectReader reader = new CsvMapper().readerFor(type).with(csvSchema);

        List<T> genericList = new ArrayList<>();

        if (hasValidExtension(filename, "csv")) {
            try (FileInputStream inputStream = new FileInputStream(getFilePathForFile(filename).toAbsolutePath().toString())) {
                try (MappingIterator<T> iterator = reader.readValues(inputStream)) {
                    iterator.forEachRemaining(genericList::add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return genericList;
    }


    public String getFileExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public Boolean hasValidExtension(String filename, String fileType){
        return getFileExtension(filename).equalsIgnoreCase(fileType);
    }

}
