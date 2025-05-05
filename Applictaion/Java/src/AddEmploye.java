import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddEmploye implements ActionListener {
    JFrame addEmployeFrame;
    JTextField field;
    JButton submit;
    FileHandling file = new FileHandling();
    Rfid id = new Rfid();

    public AddEmploye() {

        // ---------------------Instance--------------------------//
        addEmployeFrame = new JFrame();
        ImageIcon icon = null;
        JLabel label = new JLabel();
        field = new JTextField();
        GridBagConstraints textGrid = new GridBagConstraints();
        GridBagConstraints labelGrid = new GridBagConstraints();
        GridBagConstraints buttonGrid = new GridBagConstraints();

        submit = new JButton("Submit");

        // ---------------------Label--------------------------//
        labelGrid.gridx = 0;
        labelGrid.gridy = 0;
        labelGrid.anchor = GridBagConstraints.CENTER;
        labelGrid.insets = new Insets(10, 0, 10, 0);

        label.setText("Add Employe");
        label.setForeground(Color.white);

        // ---------------------textField--------------------------//
        textGrid.gridx = 0;
        textGrid.gridy = 1;
        textGrid.anchor = GridBagConstraints.CENTER;
        textGrid.insets = new Insets(10, 0, 10, 0);
        field.setPreferredSize(new Dimension(200, 25));
        field.setBackground(new Color(100, 100, 100));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(50, 50, 50));
        field.setText("Holder Name");

        // ---------------------SubmitButton--------------------------//
        buttonGrid.gridx = 0;
        buttonGrid.gridy = 2;
        buttonGrid.anchor = GridBagConstraints.CENTER;
        buttonGrid.insets = new Insets(10, 0, 10, 0);
        submit.setSize(new Dimension(100, 50));
        submit.setForeground(Color.white);
        submit.setBorderPainted(false);
        submit.setBackground(new Color(100, 100, 100));
        submit.setFocusable(false);

        // ---------------------Frame--------------------------//
        icon = new ImageIcon("src\\Icon.png");
        addEmployeFrame.setTitle("Add Employe");
        addEmployeFrame.setSize(500, 400);
        addEmployeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addEmployeFrame.setIconImage(icon.getImage());
        addEmployeFrame.getContentPane().setBackground(new Color(50, 50, 50));
        addEmployeFrame.setLocationRelativeTo(null);
        addEmployeFrame.setVisible(true);
        addEmployeFrame.setLayout(new GridBagLayout());
        addEmployeFrame.setResizable(false);
        addEmployeFrame.add(label, labelGrid);
        addEmployeFrame.add(field, textGrid);
        addEmployeFrame.add(submit, buttonGrid);

        System.out.println(id.connectToPort(Rfid.portDescriptor));
        if (id.comPort.isOpen()) {
            submit.addActionListener(this);
        }
        else{
            SwingUtilities.invokeLater(() -> submit.setText("Close"));
            submit.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            new Thread(() -> {
                if (id.comPort.isOpen()) {
                    System.out.println("thread running");
                    field.setEditable(false);
                    submit.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Place your card on the scanner", "Message",JOptionPane.INFORMATION_MESSAGE);
                    boolean condition = true;
                    while (condition) {
                        try {
                            if (id.getHolderUid() != null) {
                                id.closePort();
                                Thread.sleep(3000);
                                addEmployeFrame.dispose();
                                new HomeWindow();
                                condition = false;
                            }
                            id.readUid();
                        } catch (Exception f) {
                            f.printStackTrace();
                        }
                    }
                    if (id.getHolderUid() == null) {
                        id.closePort();
                    }
                    String name = field.getText();
                    String uid = id.getHolderUid();
                    System.out.println(name + uid);
                    file.writeUidHoldersToFile(name, uid);
                    id.setHolderUid(null);
                } else {
                    
                    addEmployeFrame.dispose();
                    new HomeWindow();
                }

            }).start();
        }
    }
}
