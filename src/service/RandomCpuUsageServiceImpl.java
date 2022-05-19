package service;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static config.Constants.MAX_COORDINATES_AMOUNT;

public class RandomCpuUsageServiceImpl implements CpuUsageService {

    private final LinkedList<Double> data;

    public RandomCpuUsageServiceImpl() {
        data = Stream.generate(() -> 0.0)
                .limit(MAX_COORDINATES_AMOUNT)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public LinkedList<Double> loadAndGetCpuData() {
        data.addLast(Math.floor(Math.random() * 100));
        if (data.size() > MAX_COORDINATES_AMOUNT) {
            data.removeFirst();
        }
        return data;
    }

}
