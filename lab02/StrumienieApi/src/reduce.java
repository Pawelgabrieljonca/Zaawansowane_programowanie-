import java.util.Arrays;
import java.util.List;
import java.util.Optional;
public class reduce {
    public static void main(String[] args) {
        List<Integer> liczby = Arrays.asList(1, 2, 3, 4, 5);
        //Zsumuj elementy kolekcji używając Optional<Integer> reduce(BinaryOperator<Integer> accumulator)
        Optional<Integer> sumaOptional = liczby.stream()
                .reduce((a, b) -> a + b);
        sumaOptional.ifPresent(suma -> System.out.println("Suma elementów (Optional): " + suma));
        //Pomnóż wszystkie elementy kolekcji używając T reduce(T identityVal, BinaryOperator<T> accumulator)
        int iloczyn = liczby.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("Iloczyn elementów: " + iloczyn);
    }
}