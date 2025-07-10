package lab6.note_app.controller;

import lab6.note_app.model.Importance;
import lab6.note_app.model.Note;
import lab6.note_app.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Kontroler obsługujący żądania związane z notatkami.
 * Zarządza wyświetlaniem notatek, dodawaniem nowych notatek i interakcją z warstwą serwisu.
 */
@Controller
public class NoteController {

    private final NoteService noteService;

    /**
     * Konstruktor do wstrzykiwania zależności NoteService.
     * Spring automatycznie wstrzyknie instancję NoteServiceImp.
     */
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Obsługuje żądania GET dla ścieżki "/list".
     * Wyświetla formularz do dodawania notatek oraz listę istniejących notatek.
     */
    @GetMapping("/list")
    public String showNotesForm(Model model) {
        model.addAttribute("note", new Note()); // Pusty obiekt Note dla formularza
        model.addAttribute("notes", noteService.listOfNotes()); // Lista wszystkich notatek z serwisu
        model.addAttribute("importanceLevels", Importance.values()); // Poziomy ważności dla listy rozwijanej
        return "notes"; // Nazwa szablonu Thymeleaf (notes.html)
    }

    /**
     * Obsługuje żądania POST dla ścieżki "/add" w celu dodawania nowych notatek.
     */
    @PostMapping("/add")
    public String addNote(@ModelAttribute("note") Note note) {
        noteService.addNote(note); // Użycie serwisu do dodania notatki
        return "redirect:/list"; // Przekierowanie z powrotem na stronę listy
    }
}
