import userinterface.GameWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main_Log extends JFrame implements ActionListener{

    JButton login, cancel;
    JTextField tfusername, tfpassword;
    Main_Log()
    {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 20);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 20);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 20);
        add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(150, 70, 150, 20);
        add(tfpassword);

        login = new JButton("Login");
        login.setBounds(40, 140, 120, 30);
        login.setBackground(Color.BLACK);
//        login.setBorder(new RoundedBorder(25));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        login.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(login);

        cancel = new JButton("Cancel");
        cancel.setBounds(180, 140, 120, 30);
        cancel.setBackground(Color.RED);
        cancel.setForeground(Color.WHITE);
//        cancel.setBorder(new RoundedBorder(25));
        cancel.addActionListener(this);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("dino.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 1, 200, 200);
        add(image);

        setSize(600, 250);
        setLocation(500, 250);
        setVisible(true);
        setTitle("Dino Escape The Dino Game");
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfusername.getText();
            String password = tfpassword.getText();

            String query = "select * from users where username='"+username+"'";

            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    if (password.equals(dbPassword)) {
                        setVisible(false);
                        GameWindow.main(new String[0]);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password");
                    }
                } else {
                    // Username does not exist, insert new user
                    String insertQuery = "insert into users (username, password) values ('" + username + "', '" + password + "')";
                    int rowsAffected = c.s.executeUpdate(insertQuery);
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "New user registered successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to register new user");
                    }
                }
                c.s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }


    public static void main(String[] args) {

        new Main_Log();

    }
}