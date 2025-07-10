import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    private QuizClient client;
    private JTextArea questionArea;
    private JTextField answerField;
    private JButton sendButton;

    public ClientGUI(QuizClient client) {
        this.client = client;

        setTitle("Quiz Client");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel pytań
        questionArea = new JTextArea(10, 40);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        questionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(questionArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel odpowiedzi
        JPanel answerPanel = new JPanel(new BorderLayout(5, 0));
        answerField = new JTextField();
        sendButton = new JButton("Wyślij odpowiedź");

        answerPanel.add(answerField, BorderLayout.CENTER);
        answerPanel.add(sendButton, BorderLayout.EAST);
        mainPanel.add(answerPanel, BorderLayout.SOUTH);

        // Dodaj nasłuchiwacz do przycisku
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAnswer();
            }
        });

        // Obsługa Enter w polu tekstowym
        answerField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendAnswer();
                }
            }
        });

        add(mainPanel);

        // Rozpocznij z wyłączonym przyciskiem, dopóki nie otrzymamy pytania
        sendButton.setEnabled(false);
        answerField.setEnabled(false);
        questionArea.setText("Oczekiwanie na pytanie...");
    }

    private void sendAnswer() {
        String answer = answerField.getText().trim();
        if (!answer.isEmpty()) {
            client.sendAnswer(answer);
            answerField.setText("");
            answerField.requestFocus();
        }
    }

    public void displayQuestion(String question) {
        SwingUtilities.invokeLater(() -> {
            questionArea.setText(question);
            sendButton.setEnabled(true);
            answerField.setEnabled(true);
            answerField.requestFocus();
        });
    }

    public void displayGameOver(String message) {
        SwingUtilities.invokeLater(() -> {
            questionArea.setText(message);
            sendButton.setEnabled(false);
            answerField.setEnabled(false);
        });
    }

    public void displayError(String errorMessage) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, errorMessage, "Błąd", JOptionPane.ERROR_MESSAGE);
        });
    }
}
