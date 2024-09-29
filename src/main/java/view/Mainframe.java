package view;

import controller.RegistrationController;

import javax.swing.*;
import java.awt.*;

public class Mainframe extends JFrame {
    private Font latoFont;
    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private RegistrationController registrationController;

    public Mainframe() {
        super("Welcome to Snake Game");
        registrationController = new RegistrationController(); // Inicijalizacija kontrolera za registraciju
        initialize();
    }

    private void initialize() {
        try {
            latoFont = new Font("SansSerif", Font.PLAIN, 20); // Koristi jednostavniji font za primjer
        } catch (Exception e) {
            latoFont = new Font("SansSerif", Font.PLAIN, 20);
        }

        setSize(800, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel marginBox = new JPanel();
        marginBox.setLayout(new BorderLayout());
        marginBox.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 70));
        setContentPane(marginBox);

        marginBox.add(initTopPanel(), BorderLayout.NORTH);
        marginBox.add(initLeftPanel(), BorderLayout.WEST);
        marginBox.add(initBotPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Component initTopPanel() {
        JPanel northPanel = new JPanel();
        JLabel label = new JLabel("Welcome to Snake Game");
        label.setFont(latoFont);
        northPanel.add(label, BorderLayout.CENTER);
        return northPanel;
    }

    private Component initBotPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register and Play");
        JButton exitButton = new JButton("Exit");

        registerButton.setFont(latoFont.deriveFont(Font.PLAIN, 15));
        exitButton.setFont(latoFont.deriveFont(Font.PLAIN, 15));

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username and password!");
            } else {
                boolean registrationSuccess = registrationController.registerUser(username, password);
                if (registrationSuccess) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + username + "! Starting game...");
                    new GameFrame(username);  // Pokreće igru nakon uspješne registracije
                    dispose(); // Zatvori Mainframe prozor
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed. Username may already be taken.");
                }
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        panel.add(registerButton);
        panel.add(exitButton);
        return panel;
    }

    private Component initLeftPanel() {
        JLabel usernameLabel = new JLabel("Enter your username:");
        JLabel passwordLabel = new JLabel("Enter your password:");

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.add(Box.createVerticalStrut(100));
        usernameLabel.setFont(latoFont.deriveFont(Font.PLAIN, 15));
        passwordLabel.setFont(latoFont.deriveFont(Font.PLAIN, 15));
        usernameField.setFont(latoFont.deriveFont(Font.PLAIN, 15));
        passwordField.setFont(latoFont.deriveFont(Font.PLAIN, 15));
        usernameField.setMaximumSize(new Dimension(400, 25));
        passwordField.setMaximumSize(new Dimension(400, 25));

        westPanel.add(usernameLabel);
        westPanel.add(Box.createVerticalStrut(10));
        westPanel.add(usernameField);
        westPanel.add(Box.createVerticalStrut(20));
        westPanel.add(passwordLabel);
        westPanel.add(Box.createVerticalStrut(10));
        westPanel.add(passwordField);
        return westPanel;
    }
}
