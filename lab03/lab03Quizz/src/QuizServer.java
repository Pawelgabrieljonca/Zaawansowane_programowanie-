import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class QuizServer {
    private ServerSocket serverSocket;
    private List<Question> questions;
    private BlockingQueue<ClientAnswer> answerQueue;
    private List<ClientHandler> clients;
    private int currentQuestionIndex;
    private boolean gameOver;

    public QuizServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            questions = new ArrayList<>();
            answerQueue = new LinkedBlockingQueue<>();
            clients = new ArrayList<>();
            currentQuestionIndex = 0;
            gameOver = false;

            System.out.println("Serwer uruchomiony na porcie: " + port);
        } catch (IOException e) {
            System.out.println("Błąd tworzenia serwera: " + e.getMessage());
        }
    }

    public void loadQuestionsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    questions.add(new Question(parts[0], parts[1]));
                }
            }
            System.out.println("Załadowano " + questions.size() + " pytań.");
        } catch (IOException e) {
            System.out.println("Błąd wczytywania pytań: " + e.getMessage());
        }
    }

    public void startGame() {
        if (questions.isEmpty()) {
            System.out.println("Brak pytań do gry!");
            return;
        }

        // Uruchom wątek konsumenta
        AnswerConsumer consumer = new AnswerConsumer(this, answerQueue);
        new Thread(consumer).start();

        // Wyświetl pierwsze pytanie
        broadcastQuestion();

        // Nasłuchuj połączeń klientów
        while (!gameOver) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, this);
                clients.add(handler);
                new Thread(handler).start();
            } catch (IOException e) {
                if (!gameOver) {
                    System.out.println("Błąd akceptacji klienta: " + e.getMessage());
                }
            }
        }
    }

    public void broadcastQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            String questionText = currentQuestion.getQuestion();

            for (ClientHandler client : clients) {
                client.sendQuestion(questionText);
            }

            System.out.println("Wysłano pytanie: " + questionText);
        } else {
            endGame();
        }
    }

    public void submitAnswer(String playerName, String answer) {
        try {
            answerQueue.put(new ClientAnswer(playerName, answer));
        } catch (InterruptedException e) {
            System.out.println("Błąd podczas umieszczania odpowiedzi w kolejce: " + e.getMessage());
        }
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            broadcastQuestion();
        } else {
            endGame();
        }
    }

    public void endGame() {
        gameOver = true;
        System.out.println("Koniec gry! Wszystkie pytania zostały rozwiązane.");

        for (ClientHandler client : clients) {
            client.sendMessage("GAME_OVER|Koniec gry! Wszystkie pytania zostały rozwiązane.");
            client.close();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Błąd podczas zamykania serwera: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        QuizServer server = new QuizServer(8080);
        server.loadQuestionsFromFile("questions.txt");
        server.startGame();
    }
}

class Question {
    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrectAnswer(String userAnswer) {
        return answer.trim().equalsIgnoreCase(userAnswer.trim());
    }
}

class ClientAnswer {
    private String playerName;
    private String answer;

    public ClientAnswer(String playerName, String answer) {
        this.playerName = playerName;
        this.answer = answer;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAnswer() {
        return answer;
    }
}
