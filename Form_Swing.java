import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Pattern;

public class Form_Swing {
    JFrame jframe;
    JButton loginButton;
    JTextField email;
    JPasswordField password;
    JLabel usernameError;
    JLabel passwordError;
    String defaultEmailText = "";
    String defaultPasswordText = "";

    public Form_Swing() throws IOException {
        jframe = new JFrame("Login Form");
        email = new JTextField() {
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }

            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder());
                setFont(getFont().deriveFont(16f)); // Adjust the font size here
            }
        };
        password = new JPasswordField() {
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }

            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder());
                setFont(getFont().deriveFont(16f)); // Adjust the font size here
            }
        };

        loginButton = new JButton("LOGIN") {
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                            0, 0, getWidth() - 1, getHeight() - 1));
                    g2.dispose();
                }
                super.paintComponent(g);
            }

            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(new RoundedCornerBorder());
            }
        };
        usernameError = new JLabel();
        passwordError = new JLabel();

        jframe.setContentPane(new JPanel() {
            BufferedImage bufferedImage = ImageIO.read(this.getClass().getResource("/background.jpg"));

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, this);
            }
        });
        init();
    }

    public void addEventListeners() {
        //submit button action listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Username: " + email.getText();
                data += "\n" + "Password: " + password.getText();
                JOptionPane.showMessageDialog(null, data);
            }
        });

        //email validation listener
        email.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                validateEmail();
            }

            public void insertUpdate(DocumentEvent e) {
                validateEmail();
            }

            public void changedUpdate(DocumentEvent e) {
                validateEmail();
            }
        });

        //password validation listener
        password.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                validatePassword();
            }

            public void insertUpdate(DocumentEvent e) {
                validatePassword();
            }

            public void changedUpdate(DocumentEvent e) {
                validatePassword();
            }
        });

        email.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                if (email.getText().equals("")) {
                    email.setText(defaultEmailText);
                    email.setForeground(Color.gray);
                }
            }

            public void focusGained(FocusEvent e) {
                if (email.getText().equals(defaultEmailText)) {
                    email.setText("");
                    email.setForeground(Color.black);
                }
            }
        });

        password.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                if (String.valueOf(password.getPassword()).equals("")) {
                    password.setText(defaultPasswordText);
                    password.setForeground(Color.gray);
                    password.setEchoChar((char) 0);
                }
            }

            public void focusGained(FocusEvent e) {
                if (String.valueOf(password.getPassword()).equals(defaultPasswordText)) {
                    password.setText("");
                    password.setEchoChar('*');
                    password.setForeground(Color.black);
                }
            }
        });
    }

    private void validateEmail() {
        if (email.getText().length() > 0 && !email.getText().equals(defaultEmailText)) {
            if (validateMail(email.getText())) {
                usernameError.setForeground(new Color(50, 168, 58));
                usernameError.setText("Email is valid");
            } else {
                usernameError.setForeground(Color.RED);
                usernameError.setText("Email is not valid");
            }
        } else {
            usernameError.setText("");
        }
    }

    private void validatePassword() {
        if (String.valueOf(password.getPassword()).length() > 0 && !String.valueOf(password.getPassword()).equals(defaultPasswordText)) {
            if (validatePassword(String.valueOf(password.getPassword()))) {
                passwordError.setForeground(new Color(50, 168, 58));
                passwordError.setText("Password is valid");
            } else {
                passwordError.setForeground(Color.RED);
                passwordError.setText("Password is not valid");
            }
        } else {
            passwordError.setText("");
        }
    }

    private boolean validateMail(String mail) {
        String regExp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regExp);
        return pattern.matcher(mail).matches();
    }

    private boolean validatePassword(String text) {
        return text.length() >= 8 && text.matches(".*[a-zA-Z]+.*") && text.matches(".*\\d.*");
    }

    public void init() {
        email.setPreferredSize(new Dimension(250, 35));
        password.setPreferredSize(new Dimension(250, 35));
        loginButton.setPreferredSize(new Dimension(250, 35));
        loginButton.setBackground(new Color(66, 245, 114));
        loginButton.setFocusPainted(false);

        email.setText(defaultEmailText);
        email.setForeground(Color.gray);
        password.setText(defaultPasswordText);
        password.setForeground(Color.gray);
        password.setEchoChar((char) 0);

        usernameError.setFont(new Font("SansSerif", Font.BOLD, 11));
        usernameError.setForeground(Color.RED);

        passwordError.setFont(new Font("SansSerif", Font.BOLD, 11));
        passwordError.setForeground(Color.RED);

        jframe.setLayout(new GridBagLayout());

        Insets textInsets = new Insets(10, 10, 5, 10);
        Insets buttonInsets = new Insets(20, 10, 10, 10);
        Insets errorInsets = new Insets(0, 20, 0, 0);

        GridBagConstraints input = new GridBagConstraints();
        input.anchor = GridBagConstraints.CENTER;
        input.insets = textInsets;
        input.gridy = 1;
        jframe.add(email, input);

        input.gridy = 2;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        jframe.add(usernameError, input);

        input.gridy = 3;
        input.insets = textInsets;
        input.anchor = GridBagConstraints.CENTER;
        jframe.add(password, input);

        input.gridy = 4;
        input.insets = errorInsets;
        input.anchor = GridBagConstraints.WEST;
        jframe.add(passwordError, input);

        input.insets = buttonInsets;
        input.anchor = GridBagConstraints.WEST;
        input.gridx = 0;
        input.gridy = 5;
        jframe.add(loginButton, input);

        jframe.setSize(950, 650);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);

        jframe.requestFocus();
        addEventListeners();
    }

    public static void main(String args[]) {
        try {
            new Form_Swing();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Custom border for rounded corners
    class RoundedCornerBorder implements Border {
        private int radius;

        RoundedCornerBorder() {
            this.radius = 15; // You can adjust the radius as per your requirement
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        public Shape getBorderShape(int x, int y, int w, int h) {
            return new RoundRectangle2D.Double(x, y, w - 1, h - 1, radius, radius);
        }
    }
}
