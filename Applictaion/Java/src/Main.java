import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame mainFrame;
        JLabel tsas;
        JProgressBar progressBar;
        GridBagConstraints gbc;

        // ---------------------Instance--------------------------//
        mainFrame = new JFrame();
        ImageIcon icon = null;
        tsas = new JLabel("TSAS");
        progressBar = new JProgressBar();
        gbc = new GridBagConstraints();

        // ---------------------progress bar--------------------------//        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        progressBar.setIndeterminate(true);
        progressBar.setBackground(new Color(100,100,100));
        progressBar.setForeground(new Color(50,50,50));
        gbc.gridy = 1; 

        // ---------------------label--------------------------//
        tsas.setForeground(Color.WHITE);
        tsas.setFont(new Font("Arial",Font.BOLD,45));

        // ---------------------Frame--------------------------//
        try {
            icon = new ImageIcon("src\\Icon.png");
        } catch (Exception e) {
            System.err.println("Icon image not found.");
        }
        mainFrame.setTitle("VER_1.1.0");
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (icon != null) {
            mainFrame.setIconImage(icon.getImage());
        }
        mainFrame.getContentPane().setBackground(new Color(50, 50, 50));
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.add(tsas);
        mainFrame.add(progressBar, gbc);

        Thread.sleep(5000);

        mainFrame.dispose();
        new Login();

    }
}
