package ru.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.otus.model.Measurement;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream()
                .collect(Collectors.toMap(Measurement::name, Measurement::value, Double::sum));
        // группирует выходящий список по name, при этом суммирует поля value
    }
}
