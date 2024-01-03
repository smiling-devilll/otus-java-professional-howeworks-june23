package ru.otus.dataprocessor;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String resourceFileName;
    private final ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        this.resourceFileName = fileName;
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        var file = this.getClass().getClassLoader().getResource(resourceFileName);
        if (file == null) throw new FileProcessException("File " + resourceFileName + " not found");
        var fullPath = new File(file.getPath());

        try {
            var stream = new BufferedReader(new FileReader(fullPath));
            return mapper.readValue(stream, mapper.getTypeFactory().constructCollectionType(List.class, Measurement.class));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        // читает файл, парсит и возвращает результат
    }
}
