import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Scan {

    // ---------------------Objects----------------------//
    JFrame scanFrame;
    FileHandling file = new FileHandling();
    Rfid id = new Rfid();

    public Scan() {

        // ---------------------Instance--------------------------//
        scanFrame = new JFrame();
        ImageIcon icon = null;
        JLabel label = new JLabel();

        // ---------------------Label--------------------------//
        label.setText("scanning....");
        label.setForeground(Color.white);
        System.out.println(id.connectToPort(Rfid.portDescriptor));
        readUid(id, label);

        // ---------------------Frame--------------------------//
        icon = new ImageIcon("src\\Icon.png");
        scanFrame.setTitle("SCAN");
        scanFrame.setSize(500, 200);
        scanFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        scanFrame.setIconImage(icon.getImage());
        scanFrame.getContentPane().setBackground(new Color(50, 50, 50));
        scanFrame.setLocationRelativeTo(null);
        scanFrame.setVisible(true);
        scanFrame.setLayout(new GridBagLayout());
        scanFrame.setResizable(false);
        scanFrame.add(label);

    }

    // ---------------------readUid--------------------------//
    public void readUid(Rfid id, JLabel uid) {
        new Thread(() -> {
            if (id.comPort.isOpen()) {
                boolean condition = true;
                while (condition) {
                    try {
                        if (id.getHolderUid() != null) {
                            id.closePort();
                            SwingUtilities.invokeLater(() -> uid.setText(id.getHolderUid()));
                            Thread.sleep(1000);
                            new HomeWindow();
                            scanFrame.dispose();
                            condition = false;
                        }
                        id.readUid();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (id.getHolderUid() == null) {
                    id.closePort();
                }

                file.readUidHolderFromFile(id.getHolderUid());

                try {
                    file.writeToExcel(file.name, file.uid);
                    System.out.println(file.name + file.uid);
                    file.name = null;
                    file.uid = null;
                    id.setHolderUid(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Comport not found","Warning",JOptionPane.WARNING_MESSAGE);
                scanFrame.dispose();
                new HomeWindow();
            }

        }).start();
    }
}