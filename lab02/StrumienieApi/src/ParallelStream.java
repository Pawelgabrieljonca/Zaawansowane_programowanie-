import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParallelStream {
    public static void main(String[] args) {
        // Wygenerowanie miliona identyfikatorów UUID i umieszczenie ich w ArrayList
        List<String> uuidList = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            uuidList.add(UUID.randomUUID().toString());
        }
        //Przetwarzanie sekwencyjne
        long startTimeSequential = System.nanoTime();
        List<String> sortedListSequential = uuidList.stream()
                .sorted()
                .collect(Collectors.toList());
        long endTimeSequential = System.nanoTime();
        long durationSequential = endTimeSequential - startTimeSequential;

        System.out.println("Czas sortowania sekwencyjnego: " + durationSequential / 1_000_000 + " ms");
        //Przetwarzanie równoległe
        long startTimeParallel = System.nanoTime();
        List<String> sortedListParallel = uuidList.parallelStream()
                .sorted()
                .collect(Collectors.toList());
        long endTimeParallel = System.nanoTime();
        long durationParallel = endTimeParallel - startTimeParallel;

        System.out.println("Czas sortowania równoległego: " + durationParallel / 1_000_000 + " ms");

        //Porównanie wyników
        System.out.println("\nPorównanie wyników:");
        System.out.println("Czas sortowania sekwencyjnego: " + durationSequential / 1_000_000 + " ms");
        System.out.println("Czas sortowania równoległego: " + durationParallel / 1_000_000 + " ms");

        System.out.println("\nWnioski:");
        if (durationParallel < durationSequential) {
            System.out.println("Sortowanie równoległe było szybsze od sortowania sekwencyjnego.");
        } else if (durationParallel > durationSequential) {
            System.out.println("Sortowanie sekwencyjne było szybsze od sortowania równoległego.");
        } else {
            System.out.println("Czasy sortowania sekwencyjnego i równoległego były zbliżone.");
        }
    }
}