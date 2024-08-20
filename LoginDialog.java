import gameobject.MainCharacter;
import userinterface.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private Connection connection;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(usernameLabel, cs);

        usernameField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(usernameField, cs);

        JLabel passwordLabel = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(passwordLabel, cs);

        passwordField = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(passwordField, cs);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (getUsername().isEmpty() || getPassword().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Please enter details!",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                } else if (authenticate(getUsername(), getPassword())) {
                    // Open another Java class upon successful login
                    dispose();
                    GameWindow.main(new String[0]);
                    // Load another Java class here
                    // GameWindow.main(new String[0]);
                    // You can call a method to open another frame or perform any other action here
                    JOptionPane.showMessageDialog(parent, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Invalid username or password",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(loginButton);
        bp.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        try {
            String url = "jdbc:mysql://localhost:3306/dinoData";
            String user = "root";
            String password = "Panchal@2109";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    private boolean authenticate(String username, String password) {
        // Check if the username exists in the database
        String checkUserQuery = "SELECT * FROM users WHERE username=?";
        try (PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery)) {
            checkUserStatement.setString(1, username);
            ResultSet userResultSet = checkUserStatement.executeQuery();
            if (!userResultSet.next()) {
                // If username doesn't exist, create a new user
                String createUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement createUserStatement = connection.prepareStatement(createUserQuery)) {
                    createUserStatement.setString(1, username);
                    createUserStatement.setString(2, password);
                    createUserStatement.executeUpdate();
                    return true; // User created successfully
                }
            } else {
                // Username exists, validate the password
                String validatePasswordQuery = "SELECT * FROM users WHERE username=? AND password=?";
                try (PreparedStatement validatePasswordStatement = connection.prepareStatement(validatePasswordQuery)) {
                    validatePasswordStatement.setString(1, username);
                    validatePasswordStatement.setString(2, password);
                    ResultSet passwordResultSet = validatePasswordStatement.executeQuery();
                    return passwordResultSet.next(); // If there's at least one result, authentication is successful
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
//private boolean authenticate(String username, String password) {
//    // Check if the username exists in the database
//    String checkUserQuery = "SELECT * FROM users WHERE username=?";
//    try (PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery)) {
//        checkUserStatement.setString(1, username);
//        ResultSet userResultSet = checkUserStatement.executeQuery();
//        if (!userResultSet.next()) {
//            // If username doesn't exist, create a new user
//            String createUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
//            try (PreparedStatement createUserStatement = connection.prepareStatement(createUserQuery)) {
//                createUserStatement.setString(1, username);
//                createUserStatement.setString(2, password);
//                createUserStatement.setInt(3, 0); // Initialize score to 0
//                createUserStatement.executeUpdate();
//                return true; // User created successfully
//            }
//        } else {
//            // Username exists, validate the password
//            String validatePasswordQuery = "SELECT * FROM users WHERE username=? AND password=?";
//            try (PreparedStatement validatePasswordStatement = connection.prepareStatement(validatePasswordQuery)) {
//                validatePasswordStatement.setString(1, username);
//                validatePasswordStatement.setString(2, password);
//                ResultSet passwordResultSet = validatePasswordStatement.executeQuery();
//                if (passwordResultSet.next()) {
//                    // If password is validated, update the score in the database
////                    int currentScore = passwordResultSet.getInt("score");
////                    int newScore = currentScore + mainCharacter.getScore(); // Assuming mainCharacter.getScore() returns the score
//                    String updateScoreQuery = "UPDATE users SET score=? WHERE username=?";
//                    try (PreparedStatement updateScoreStatement = connection.prepareStatement(updateScoreQuery)) {
////                        updateScoreStatement.setInt(1, newScore);
//                        updateScoreStatement.setString(2, username);
//                        updateScoreStatement.executeUpdate();
//                    }
//                    return true; // Authentication successful
//                } else {
//                    return false; // Invalid password
//                }
//            }
//        }
//    } catch (SQLException ex) {
//        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
//                "Error", JOptionPane.ERROR_MESSAGE);
//        return false;
//    }
//}


    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 200);
                frame.setVisible(true);

                JButton loginButton = new JButton("Login");
                loginButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new LoginDialog(frame).setVisible(true);
                    }
                });
                frame.add(loginButton);
            }
        });
    }
}

