package service;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static config.Constants.MAX_COORDINATES_AMOUNT;

public class RemoteCpuUsageServiceImpl implements CpuUsageService {

    private final LinkedList<Double> cpuData;
    private final DataInputStream inputStream;

    public RemoteCpuUsageServiceImpl(DataInputStream inputStream) {
        this.inputStream = inputStream;
        this.cpuData = Stream.generate(() -> 0.0)
                .limit(MAX_COORDINATES_AMOUNT)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public LinkedList<Double> loadAndGetCpuData() {
        this.loadCoordinate();
        return this.getCpuData();
    }

    private LinkedList<Double> getCpuData() {
        return cpuData;
    }

    private void loadCoordinate() {
        try {
            double datas = inputStream.readDouble();
            cpuData.addLast(datas);
            System.out.println(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cpuData.size() > MAX_COORDINATES_AMOUNT) {
            cpuData.removeFirst();
        }
    }
}
