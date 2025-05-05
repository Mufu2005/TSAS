import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class WelcomeWindow implements ActionListener{
    
    JFrame welcomeFrame;
    JButton welcomeButton;
    
    public WelcomeWindow(){
        
        //---------------------Instance--------------------------//
        welcomeFrame = new JFrame();
        ImageIcon icon = null;
        welcomeButton = new JButton("WELCOME");

        //---------------------Frame--------------------------//
        try {
            icon = new ImageIcon("src\\Icon.png");
        } catch (Exception e) {
            System.err.println("Icon image not found.");
        }
        welcomeFrame.setTitle("TSAS");
        welcomeFrame.setSize(500, 500);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (icon != null) {
            welcomeFrame.setIconImage(icon.getImage());
        }
        welcomeFrame.getContentPane().setBackground(new Color(50, 50, 50));
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);  
        welcomeFrame.setLayout(new GridBagLayout());

        //---------------------Button--------------------------//
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        welcomeButton.setSize(new Dimension(100, 50));
        welcomeButton.setForeground(Color.white);
        welcomeButton.setBorderPainted(false);
        welcomeButton.setBackground(new Color(100,100,100));
        welcomeButton.setFocusable(false);
        welcomeButton.addActionListener(this);
        welcomeFrame.add(welcomeButton,gbc);
    }

    //---------------------Action--------------------------//
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == welcomeButton){
            welcomeFrame.dispose();
            new HomeWindow();
        }
    }
}
