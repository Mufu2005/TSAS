
import javax.swing.JOptionPane;

import com.fazecast.jSerialComm.SerialPort;

public class Rfid {

    private String holderUid;
    static String portDescriptor = "Default"; // port name
    SerialPort comPort; // port

    // ---------------------getter setter--------------------------//
    public String getHolderUid() {
        return holderUid;
    }

    public void setHolderUid(String holderUid) {
        this.holderUid = holderUid;
    }

    // ---------------------connectToPort--------------------------//
    public String connectToPort(String portName) {
        // read uid from rfid
        // Close the port if it's already open
        if (comPort != null) {
            try {
                comPort.closePort();
                System.out.println("Port closed");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro closing port", "Message", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error closing port: " + e.getMessage());
            }
        }

        // Open the port
        portDescriptor = portName.toUpperCase();

        comPort = SerialPort.getCommPort(portDescriptor);
        comPort.setBaudRate(9600);

        System.out.println("Trying to open port " + portDescriptor);

        if (comPort.openPort()) {
            System.out.println("Port opened successfully");
            return "Connected to " + portDescriptor;
        } else {
            System.out.println("Failed to open port");
            JOptionPane.showMessageDialog(null, "Failed to open port", "Message", JOptionPane.ERROR_MESSAGE);
            return "Connection unsuccessful " + portDescriptor;
        }
    }

    // ---------------------ReadUid--------------------------//
    public String readUid() {
        if (comPort.bytesAvailable() > 0) {
            byte[] buffer = new byte[comPort.bytesAvailable()];
            comPort.readBytes(buffer, buffer.length);
            setHolderUid(new String(buffer).trim());
            return holderUid;
        } else {
            return "";
        }

    }

    // ---------------------closePort--------------------------//
    public void closePort() {
        comPort.closePort();
    }

    // -----------------------------------------//
}
