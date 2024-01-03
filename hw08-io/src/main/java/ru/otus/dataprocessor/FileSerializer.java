package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileSerializer implements Serializer {

    private final String fileName;
    private final ObjectMapper om;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        this.om = new ObjectMapper();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        var sorted =
                data.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.
                                toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        try {
            om.writeValue(new File(fileName), sorted);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        // формирует результирующий json и сохраняет его в файл
    }
}
