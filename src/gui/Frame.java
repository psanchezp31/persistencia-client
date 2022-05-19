package gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import service.UpdateChartActionListenerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Frame extends JFrame {

    private static JPanel mainPanel;
    private final JPanel topPanel;
    private final JLabel ipLabel;
    private final JTextField ipTextField;
    private final JButton connectButton;
    private final ChartPanel chartPanel;
    private final JFreeChart chart;
    private final UpdateChartActionListenerImpl listenerService;
    private XYLineAndShapeRenderer renderer;
    private XYPlot plot;

    public Frame(final String title) {
        super(title);
        setSystemLookAndFeel();
        ipLabel = new JLabel("IP: ");
        ipTextField = new JTextField(20);
        connectButton = new JButton("Connect");
        topPanel = new JPanel();
        topPanel.add(ipLabel);
        topPanel.add(ipTextField);
        topPanel.add(connectButton);
        chart = createChart(new XYSeriesCollection());
        chartPanel = new ChartPanel(chart);
        chartPanel.setSize(560, 370);
        chartPanel.setMouseZoomable(true, false);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(chartPanel, BorderLayout.CENTER);
        this.setContentPane(mainPanel);
        listenerService = new UpdateChartActionListenerImpl(ipTextField, connectButton, chart);
        connectButton.addActionListener(listenerService);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Frame window = new Frame("Remote CPU Usage");
            window.setSize(800, 600);
            window.setResizable(false);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Remote CPU Usage",
                "Time (seconds)",
                "% Utilization",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.green);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot = chart.getXYPlot();
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.BLACK);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle("Remote CPU Usage", new Font("Sans-Serif", java.awt.Font.BOLD, 18)));

        return chart;
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}


