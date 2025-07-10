import java.util.ArrayList;
import java.util.Optional;
public class Main {
    public static void main(String[] args) {
        // Tworzenie kolekcji klasy ArrayList<Integer>
        ArrayList<Integer> numbers = new ArrayList<>();
        // Dodanie kilku elementów do kolekcji
        numbers.add(5);
        numbers.add(12);
        numbers.add(3);
        numbers.add(7);
        numbers.add(9);
        // Utworzenie strumienia z przygotowanej kolekcji i wykonanie metody min()
        Optional<Integer> minValue = numbers.stream()
                .min(Integer::compare); // Użycie referencji do metody compare w klasie Integer
        minValue.ifPresent(value -> System.out.println("Minimalna wartość: " + value));
    }
}
