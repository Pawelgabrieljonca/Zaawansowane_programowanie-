import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class QuizClient extends JFrame {
    private JTextField nicknameField;
    private JTextField responseField;
    private JButton submitButton;
    private JTextArea messageArea;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;

    public QuizClient() {
        super("Quiz Client");
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField(15);

        JLabel responseLabel = new JLabel("Answer:");
        responseField = new JTextField(15);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> sendResponse());

        messageArea = new JTextArea();
        messageArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nicknameLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(nicknameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(responseLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(responseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
    }

    private void sendResponse() {
        String nickname = nicknameField.getText().trim();
        String response = responseField.getText().trim();

        if (nickname.isEmpty() || response.isEmpty()) {
            displayMessage("Both nickname and answer are required!");
            return;
        }

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(nickname + "|" + response);
            displayMessage("Sent: " + response);
            responseField.setText("");
        } catch (IOException e) {
            displayMessage("Connection error: " + e.getMessage());
        }
    }

    private void displayMessage(String message) {
        messageArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizClient::new);
    }
}
