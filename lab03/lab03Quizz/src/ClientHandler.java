import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private QuizServer server;
    private BufferedReader in;
    private PrintWriter out;
    private String playerName;

    public ClientHandler(Socket socket, QuizServer server) {
        this.clientSocket = socket;
        this.server = server;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Błąd inicjalizacji handlera klienta: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            // Odbierz imię gracza
            playerName = in.readLine();
            System.out.println("Nowy gracz dołączył: " + playerName);

            // Wyślij aktualne pytanie
            Question currentQuestion = server.getCurrentQuestion();
            if (currentQuestion != null) {
                sendQuestion(currentQuestion.getQuestion());
            }

            // Odbieraj odpowiedzi
            String answer;
            while ((answer = in.readLine()) != null) {
                server.submitAnswer(playerName, answer);
            }
        } catch (IOException e) {
            System.out.println("Klient rozłączony: " + playerName);
        } finally {
            close();
        }
    }

    public void sendQuestion(String question) {
        out.println("QUESTION|" + question);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.out.println("Błąd zamykania połączenia: " + e.getMessage());
        }
    }
}
