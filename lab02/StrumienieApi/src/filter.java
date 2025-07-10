import java.util.ArrayList;
import java.util.stream.Stream;
public class filter {
    public static void main(String[] args) {
        ArrayList<Integer> liczby = new ArrayList<>();
        liczby.add(1);
        liczby.add(2);
        liczby.add(3);
        liczby.add(4);
        liczby.add(5);
        liczby.add(6);
        liczby.add(7);
        liczby.add(8);
        liczby.add(9);
        liczby.add(10);
        // Utwórz strumień z kolekcji i odfiltruj elementy parzyste
        Stream<Integer> parzysteLiczby = liczby.stream()
                .filter(liczba -> liczba % 2 == 0);
        parzysteLiczby.forEach(System.out::println);
    }
}