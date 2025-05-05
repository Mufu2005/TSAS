import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.nio.file.Path;

public class HomeWindow implements ActionListener {

    // ---------------------Objects--------------------------//
    JFrame homeFrame;
    JButton scanButton;
    JButton addButton;
    FileHandling file = new FileHandling();
    Rfid id = new Rfid();
    JMenuItem editEmloye;
    JMenuItem deleteEmploye;
    JMenuItem openExel;
    JMenuItem comport;

    public HomeWindow() {
        // ---------------------Instances--------------------------//
        homeFrame = new JFrame();
        ImageIcon icon = null;
        scanButton = new JButton("Scan");
        addButton = new JButton("Add new employe");
        GridBagConstraints addGrid = new GridBagConstraints();
        GridBagConstraints scanGrid = new GridBagConstraints();
        JMenuBar bar = new JMenuBar();
        JMenu settings = new JMenu("Settings");
        editEmloye = new JMenuItem("Edit employe");
        deleteEmploye = new JMenuItem("Delete employe");
        openExel = new JMenuItem("Excel file");
        comport = new JMenuItem("Set com port");

        // ---------------------Frame--------------------------//
        try {
            icon = new ImageIcon("src\\Icon.png");
        } catch (Exception e) {
            System.err.println("Icon image not found.");
        }
        homeFrame.setTitle("TSAS");
        homeFrame.setSize(500, 500);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (icon != null) {
            homeFrame.setIconImage(icon.getImage());
        }
        homeFrame.getContentPane().setBackground(new Color(50, 50, 50));
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setVisible(true);
        homeFrame.setLayout(new GridBagLayout());
        homeFrame.setResizable(false);

        // ---------------------Menu bar--------------------------//
        settings.add(editEmloye);
        settings.add(deleteEmploye);
        settings.add(openExel);
        settings.add(comport);

        openExel.addActionListener(this);
        comport.addActionListener(this);
        editEmloye.addActionListener(this);
        deleteEmploye.addActionListener(this);

        bar.add(settings);
        bar.setBackground(new Color(100, 100, 100));
        bar.setForeground(Color.WHITE);
        homeFrame.setJMenuBar(bar);

        // ---------------------Add Button--------------------------//
        addGrid.gridx = 0;
        addGrid.gridy = 0;
        addGrid.anchor = GridBagConstraints.CENTER;
        addGrid.insets = new Insets(20, 0, 10, 0); // Top, Left, Bottom, Right

        addButton.setSize(new Dimension(100, 50));
        addButton.setForeground(Color.white);
        addButton.setBorderPainted(false);
        addButton.setBackground(new Color(100, 100, 100));
        addButton.setFocusable(false);
        homeFrame.add(addButton, addGrid);
        addButton.addActionListener(this);

        // ---------------------Scan Button--------------------------//
        scanGrid.gridx = 0;
        scanGrid.gridy = 1;
        scanGrid.anchor = GridBagConstraints.CENTER;
        scanGrid.insets = new Insets(10, 10, 10, 0);

        scanButton.setSize(new Dimension(100, 50));
        scanButton.setForeground(Color.white);
        scanButton.setBorderPainted(false);
        scanButton.setBackground(new Color(100, 100, 100));
        scanButton.setFocusable(false);
        homeFrame.add(scanButton, scanGrid);
        scanButton.addActionListener(this);

    }

    // ---------------------Action--------------------------//
    @Override
    public void actionPerformed(ActionEvent a) {

        // ---------------------Scanbutton--------------------------//
        if (a.getSource() == scanButton) {
            homeFrame.dispose();
            new Scan();
        }

        // ---------------------addButton--------------------------//
        else if (a.getSource() == addButton) {
            homeFrame.dispose();
            new AddEmploye();
        }

        // ------------------------openExcel-----------------------//
        else if (a.getSource() == openExel) {

            if (Desktop.isDesktopSupported()) {
                Path path = Paths.get("Excel files//Attendence.xlsx");

                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(path.toFile());
                    JOptionPane.showMessageDialog(null, "File opend succesfully", "Message",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to open fiel", "Message", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "System does not support the following option", "Message",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

        // ---------------------comport--------------------------//
        else if (a.getSource() == comport) {
            Rfid.portDescriptor = JOptionPane.showInputDialog("Enter the comport");
            JOptionPane.showConfirmDialog(comport, Rfid.portDescriptor);
        }

        // ---------------------editEmoloye--------------------------//
        else if (a.getSource() == editEmloye) {
            ArrayList<String> list = new ArrayList<>();
            File temp = new File("Txt files\\Workers.txt");
            String name = JOptionPane.showInputDialog("Enter the name");
            try (Scanner read = new Scanner(temp)) {
                int match = 0;
                while (read.hasNext()) {

                    String line = read.nextLine();
                    String[] parts = line.split("\\|");
                    if (parts[0].equals(name)) {
                        name = JOptionPane.showInputDialog("Enter the new name");
                        list.add(name);
                        list.add("|");
                        list.add(parts[1]);
                        match = 1;
                    } else {
                        list.add(parts[0]);
                        list.add("|");
                        list.add(parts[1]);
                    }

                }

                if (match == 0) {
                    JOptionPane.showMessageDialog(null, "No match found", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    FileWriter write = new FileWriter("Txt files\\Workers.txt");
                    for (int i = 0; i < list.size(); i++) {
                        write.write(list.get(i));
                        write.write(list.get(i += 1));
                        write.write(list.get(i += 1));
                        write.write(System.lineSeparator());
                    }
                    write.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // ---------------------deleteEmploye--------------------------//
        else if (a.getSource() == deleteEmploye) {
            ArrayList<String> list = new ArrayList<>();
            File temp = new File("Txt files\\Workers.txt");
            String name = JOptionPane.showInputDialog("Enter the name");
            try (Scanner read = new Scanner(temp)) {
                int match = 0;
                while (read.hasNext()) {

                    String line = read.nextLine();
                    String[] parts = line.split("\\|");
                    if (parts[0].equals(name)) {
                        match = 1;
                    } else {
                        list.add(parts[0]);
                        list.add("|");
                        list.add(parts[1]);
                    }

                }

                if (match == 0) {
                    JOptionPane.showMessageDialog(null, "No match found", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    FileWriter write = new FileWriter("Txt files\\Workers.txt");
                    for (int i = 0; i < list.size(); i++) {
                        write.write(list.get(i));
                        write.write(list.get(i += 1));
                        write.write(list.get(i += 1));
                        write.write(System.lineSeparator());
                    }
                    write.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
