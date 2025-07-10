import javax.swing.*;
import java.io.*;
import java.net.*;

public class QuizClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientGUI gui;
    private String playerName;

    public QuizClient(String host, int port) {
        try {
            // Inicjalizacja GUI
            gui = new ClientGUI(this);
            gui.setVisible(true);

            // Prośba o podanie imienia
            playerName = JOptionPane.showInputDialog("Podaj swoje imię:");
            if (playerName == null || playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Imię jest wymagane!");
                System.exit(0);
            }

            // Połączenie z serwerem
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Wysłanie imienia
            out.println(playerName);

            // Nasłuchiwanie wiadomości od serwera
            listenForServerMessages();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd połączenia: " + e.getMessage());
            System.exit(1);
        }
    }

    private void listenForServerMessages() {
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("QUESTION|")) {
                        String question = message.substring("QUESTION|".length());
                        gui.displayQuestion(question);
                    } else if (message.startsWith("GAME_OVER|")) {
                        String gameOverMessage = message.substring("GAME_OVER|".length());
                        gui.displayGameOver(gameOverMessage);
                        close();
                        break;
                    }
                }
            } catch (IOException e) {
                if (socket.isClosed()) {
                    System.out.println("Połączenie zamknięte.");
                } else {
                    System.out.println("Błąd odczytu od serwera: " + e.getMessage());
                    gui.displayError("Utracono połączenie z serwerem.");
                }
            }
        }).start();
    }

    public void sendAnswer(String answer) {
        if (out != null) {
            out.println(answer);
        }
    }

    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Błąd zamykania połączenia: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String host = "localhost";
            int port = 8080;
            new QuizClient(host, port);
        });
    }
}
