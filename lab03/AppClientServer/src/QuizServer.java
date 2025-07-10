import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class QuizServer extends JFrame {
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private Map<String, String> questions = new LinkedHashMap<>();
    private int currentQuestionIndex = 0;
    private boolean isGameOver = false;

    private final BlockingQueue<String[]> answerQueue = new LinkedBlockingQueue<>();

    public QuizServer() {
        super("Quiz Server");
        setupUI();
        loadQuestionsFromFile();
        startServerThreads();
        setVisible(true);
    }

    private void setupUI() {
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Courier New", Font.PLAIN, 14));

        add(new JScrollPane(logArea), BorderLayout.CENTER);
    }

    private void loadQuestionsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    questions.put(parts[0].trim(), parts[1].trim());
                }
            }
            logArea.append("Loaded " + questions.size() + " questions.\n");
        } catch (IOException e) {
            logArea.append("Error loading questions: " + e.getMessage() + "\n");
        }
    }

    private void startServerThreads() {
        new Thread(this::handleClientConnections).start();
        new Thread(this::processAnswers).start();
    }

    private void handleClientConnections() {
        try {
            serverSocket = new ServerSocket(5000);
            logArea.append("Server listening on port 5000...\n");

            while (!isGameOver) {
                Socket clientSocket = serverSocket.accept();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String message = reader.readLine(); // Format: nickname|answer
                    String clientIP = clientSocket.getInetAddress().getHostAddress();

                    if (message != null && message.contains("|")) {
                        String[] data = message.split("\\|");
                        if (data.length == 2) {
                            answerQueue.put(new String[]{data[0], data[1], clientIP});
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clientSocket.close();
            }
        } catch (IOException e) {
            logArea.append("Server error: " + e.getMessage() + "\n");
        }
    }

    private void processAnswers() {
        List<String> questionList = new ArrayList<>(questions.keySet());

        if (!questionList.isEmpty()) {
            logArea.append("\nQUESTION 1: " + questionList.get(currentQuestionIndex) + "\n");
        }

        while (!isGameOver) {
            try {
                String[] answerData = answerQueue.take(); // Wait for an answer
                String nickname = answerData[0];
                String answer = answerData[1];
                String clientIP = answerData[2];

                String currentQuestion = questionList.get(currentQuestionIndex);
                String correctAnswer = questions.get(currentQuestion);

                if (answer.equalsIgnoreCase(correctAnswer)) {
                    logArea.append(nickname + " (" + clientIP + ") answered correctly!\n");
                    answerQueue.clear(); // Clear remaining answers
                    currentQuestionIndex++;

                    if (currentQuestionIndex < questionList.size()) {
                        logArea.append("\nQUESTION " + (currentQuestionIndex + 1) + ": " + questionList.get(currentQuestionIndex) + "\n");
                    } else {
                        logArea.append("\nGame over! All questions answered.\n");
                        isGameOver = true;
                        serverSocket.close();
                    }
                } else {
                    logArea.append(nickname + " (" + clientIP + ") answered incorrectly.\n");
                }
            } catch (Exception e) {
                logArea.append("Error processing answers: " + e.getMessage() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizServer::new);
    }
}
