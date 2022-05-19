package service;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static config.Constants.MAX_COORDINATES_AMOUNT;

public class LocalCpuUsageServiceImpl implements CpuUsageService {

    private final LinkedList<Double> cpuData;
    private final OperatingSystemMXBean mxBean;

    public LocalCpuUsageServiceImpl() {
        this.mxBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

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
        cpuData.addLast(mxBean.getSystemCpuLoad() * 100);
        if (cpuData.size() > MAX_COORDINATES_AMOUNT) {
            cpuData.removeFirst();
        }
    }

}
