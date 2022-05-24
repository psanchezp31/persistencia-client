package service;

import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static config.Constants.MAX_COORDINATES_AMOUNT;
import static config.Constants.PORT;

public class UpdateChartActionListenerImpl implements ActionListener {

    private final JTextField ipTextField;
    private final JButton connectButton;
    private final JFreeChart chart;
    private ExecutorService executorService;
    private boolean isRunning = false;
    private CpuUsageService cpuUsageService;

    public UpdateChartActionListenerImpl(JTextField ipTextField, JButton connectButton, JFreeChart chart) {
        this.ipTextField = ipTextField;
        this.connectButton = connectButton;
        this.chart = chart;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isRunning) {
            executorService.shutdownNow();
            ipTextField.setEnabled(true);
            connectButton.setText("Connect");
            isRunning = false;
        } else {
            isRunning = true;
            ipTextField.setEnabled(false);
            connectButton.setText("Disconnect");
            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(this::ListenSocketAndDrawChart);
        }

    }

    private void ListenSocketAndDrawChart() {

        String ip = ipTextField.getText();
        try (Socket socket = new Socket(ip, PORT);
             DataInputStream inputCpuData = new DataInputStream(socket.getInputStream())) {
            cpuUsageService = new RemoteCpuUsageServiceImpl(inputCpuData);
            while (true) {
                Thread.sleep(1000);
                XYDataset dataSetFromCpuData = createDataSetFromCpuData(cpuUsageService.loadAndGetCpuData());
                chart.getXYPlot().setDataset(dataSetFromCpuData);
            }
        } catch (Exception x) {
            System.out.println("Interupted connection");
        }

    }

    private XYDataset createDataSetFromCpuData(LinkedList<Double> cpuData) {
        XYSeries series = new XYSeries("CPU Usage");
        for (int i = 0; i < MAX_COORDINATES_AMOUNT; i++) {
            series.add(i, cpuData.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

}
