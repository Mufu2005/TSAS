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
import javax.swing.JOptionPane;

public class Login implements ActionListener {
    private String user = "Admin";
    private String password = "Admin.123";
    JFrame loginFrame;
    JButton loginButton;
    JButton signinButton;
    FileHandling file = new FileHandling();

    // ---------------------getter setter--------------------------//
    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // ---------------------readFileLogIn--------------------------//
    public String checkCredentials(String userPass) {
        if (getUser() + getPassword() == userPass) {
            return "Welcome";
        } else {
            return "Wrong Credentials";
        }
    }

    public Login() {
        loginFrame = new JFrame();
        ImageIcon icon = null;
        loginButton = new JButton("Log In");
        signinButton = new JButton("Sign In");
        GridBagConstraints signinGrid = new GridBagConstraints();
        GridBagConstraints loginGrid = new GridBagConstraints();

        // ---------------------Frame--------------------------//
        try {
            icon = new ImageIcon("src\\Icon.png");
        } catch (Exception e) {
            System.err.println("Icon image not found.");
        }
        loginFrame.setTitle("TSAS");
        loginFrame.setSize(500, 500);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (icon != null) {
            loginFrame.setIconImage(icon.getImage());
        }
        loginFrame.getContentPane().setBackground(new Color(50, 50, 50));
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
        loginFrame.setLayout(new GridBagLayout());
        loginFrame.setResizable(false);

        // ---------------------signin Button--------------------------//
        signinGrid.gridx = 0;
        signinGrid.gridy = 0;
        signinGrid.anchor = GridBagConstraints.CENTER;
        signinGrid.insets = new Insets(20, 10, 10, 0); // Top, Left, Bottom, Right

        signinButton.setSize(new Dimension(100, 50));
        signinButton.setForeground(Color.white);
        signinButton.setBorderPainted(false);
        signinButton.setBackground(new Color(100, 100, 100));
        signinButton.setFocusable(false);
        loginFrame.add(signinButton, signinGrid);
        signinButton.addActionListener(this);

        // ---------------------login Button--------------------------//
        loginGrid.gridx = 0;
        loginGrid.gridy = 1;
        loginGrid.anchor = GridBagConstraints.CENTER;
        loginGrid.insets = new Insets(10, 10, 10, 0);

        loginButton.setSize(new Dimension(100, 50));
        loginButton.setForeground(Color.white);
        loginButton.setBorderPainted(false);
        loginButton.setBackground(new Color(100, 100, 100));
        loginButton.setFocusable(false);
        loginFrame.add(loginButton, loginGrid);
        loginButton.addActionListener(this);
    }

    // ---------------------Actions--------------------------//
    @Override
    public void actionPerformed(ActionEvent e) {

        // ---------------------login--------------------------//
        if (e.getSource() == loginButton) {
            String usename = JOptionPane.showInputDialog("Username");
            String passwrd = JOptionPane.showInputDialog("Password");

            new Thread(() -> {
                if (usename.equals(getUser()) && passwrd.equals(getPassword())) {
                    loginFrame.dispose();
                    new WelcomeWindow();
                }

                else if (file.readFileLogIn(usename,passwrd)) {
                    loginFrame.dispose();
                    new WelcomeWindow();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password", "Incorrect credentials",
                            JOptionPane.WARNING_MESSAGE);
                }

            }).start();
        }

        // ---------------------signIn--------------------------//
        else if (e.getSource() == signinButton) {
            String ADusename = JOptionPane.showInputDialog("Admin Username");
            String ADpasswrd = JOptionPane.showInputDialog("Admin Password");
           
            if (ADusename.equals(getUser()) && ADpasswrd.equals(getPassword())) {
                String usename = JOptionPane.showInputDialog("Username");
                String passwrd = JOptionPane.showInputDialog("Password");    
                file.writeFileSignIn(usename, passwrd);
                JOptionPane.showMessageDialog(null, "credentials saved", "message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Wrong username or password", "Incorrect credentials",
                JOptionPane.WARNING_MESSAGE);
            }

        }
    }

}
