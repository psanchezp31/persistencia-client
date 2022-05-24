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
    private final JPanel middlePanel;
    private final JLabel ipLabel;
    private final JTextField ipTextField;
    private final JLabel portLabel;
    private final JTextField portTextField;
    private final JLabel nameLabel;
    private final JTextField nameTextField;
    private final JButton connectButton;
    private final JLabel imageLabel;
    private ImageIcon imageLiveChat;
//    private final UpdateChartActionListenerImpl listenerService;
    private XYLineAndShapeRenderer renderer;
    private XYPlot plot;

    public Frame(final String title) {
        super(title);
        setSystemLookAndFeel();

        ipLabel = new JLabel("IP Server: ");
        ipTextField = new JTextField(20);
        ipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

        portLabel = new JLabel("Port: ");
        portTextField = new JTextField(20);

        nameLabel = new JLabel("Your name: ");
        nameTextField = new JTextField(20);

        connectButton = new JButton("Connect");

        imageLiveChat = new ImageIcon(this.getClass().getResource("/gui/images/live_chat.png"));
        Image getImage = imageLiveChat.getImage();
        Image imgScale = getImage.getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon scaledImage = new ImageIcon(imgScale);

        imageLabel = new JLabel();
        imageLabel.setSize(600, 600);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setIcon(scaledImage);

        topPanel = new JPanel();
        topPanel.add(ipLabel);
        topPanel.add(ipTextField);
        topPanel.add(portLabel);
        topPanel.add(portTextField);
        topPanel.add(nameLabel);
        topPanel.add(nameTextField);
        topPanel.add(connectButton);

        middlePanel = new JPanel();
        middlePanel.add(imageLabel);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        this.setContentPane(mainPanel);
        //listenerService = new UpdateChartActionListenerImpl(ipTextField, connectButton);
        //connectButton.addActionListener(listenerService);
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

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}


