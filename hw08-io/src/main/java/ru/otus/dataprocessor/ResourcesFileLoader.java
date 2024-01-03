package ru.otus.dataprocessor;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final File fullPath;
    private final ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        var file = this.getClass().getClassLoader().getResource(fileName);
        if (file == null) throw new FileProcessException("File " + fileName + " not found");

        this.fullPath = new File(file.getPath());
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        try {
            var stream = new BufferedReader(new FileReader(fullPath));
            return mapper.readValue(stream, mapper.getTypeFactory().constructCollectionType(List.class, Measurement.class));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        // читает файл, парсит и возвращает результат
    }
}
