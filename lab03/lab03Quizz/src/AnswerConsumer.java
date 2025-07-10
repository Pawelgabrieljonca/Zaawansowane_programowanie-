import java.util.concurrent.*;

public class AnswerConsumer implements Runnable {
    private QuizServer server;
    private BlockingQueue<ClientAnswer> answerQueue;

    public AnswerConsumer(QuizServer server, BlockingQueue<ClientAnswer> answerQueue) {
        this.server = server;
        this.answerQueue = answerQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Pobierz odpowiedź z kolejki (metoda take blokuje, jeśli kolejka jest pusta)
                ClientAnswer answer = answerQueue.take();

                // Sprawdź odpowiedź
                Question currentQuestion = server.getCurrentQuestion();
                if (currentQuestion != null) {
                    if (currentQuestion.isCorrectAnswer(answer.getAnswer())) {
                        System.out.println("Poprawna odpowiedź od: " + answer.getPlayerName() +
                                " - " + answer.getAnswer());

                        // Opróżnij kolejkę
                        answerQueue.clear();

                        // Przejdź do następnego pytania
                        server.moveToNextQuestion();
                    } else {
                        System.out.println("Błędna odpowiedź od: " + answer.getPlayerName() +
                                " - " + answer.getAnswer());
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Wątek konsumenta przerwany: " + e.getMessage());
                break;
            }
        }
    }
}
