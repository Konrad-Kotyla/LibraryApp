package pl.javastart.library.app;

import pl.javastart.library.exception.*;
import pl.javastart.library.file.FileManager;
import pl.javastart.library.file.FileManagerBuilder;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.LibraryUser;
import pl.javastart.library.model.Magazine;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

class LibraryControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;
    private Library library;

    LibraryControl() {
        this.fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            this.library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku.");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę.");
            library = new Library();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();

            switch (option) {
                case ADD_BOOK -> addBook();
                case ADD_MAGAZINE -> addMagazine();
                case PRINT_BOOKS -> printBooks();
                case PRINT_MAGAZINES -> printMagazines();
                case DELETE_BOOK -> deleteBook();
                case DELETE_MAGAZINE -> deleteMagazine();
                case ADD_USER -> addUser();
                case PRINT_USERS -> printUsers();
                case EXIT -> exit();
                default -> printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while (option != Option.EXIT);
    }

    private void deleteMagazine() {
        printer.printLine("Podaj tytuł magazynu:");
        String title = dataReader.readText();
        printer.printLine("Podaj dzień wydania magazynu:");
        int day = dataReader.readNumber();
        printer.printLine("Podaj miesiąc wydania magazynu:");
        int month = dataReader.readNumber();
        printer.printLine("Podaj rok wydania magazynu:");
        int year = dataReader.readNumber();
        try {
            Magazine magazine = library.findMagazineByTitleAndPublicationDate(title, day, month, year);
            library.removePublication(magazine);
            printer.printLine("Usunięto magazyn.");
        } catch (NoSuchElementException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void deleteBook() {
        printer.printLine("Podaj numer ISBN:");
        String isbn = dataReader.readText();

        try {
            Book book = library.findBookByIsbn(isbn);
            library.removePublication(book);
            printer.printLine("Usunięto książkę.");
        } catch (NoSuchElementException e) {
            printer.printLine(e.getMessage());
        }
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;

        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException ex) {
                printer.printLine(ex.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException ex) {
                printer.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz jedną z opcji: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException ex) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException ex) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printBooks() {
        printer.printBooks(library.getPublications().values());
    }

    private void printMagazines() {
        printer.printMagazines(library.getPublications().values());
    }

    private void printUsers() {
        printer.printUsers(library.getUsers().values());
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakończony powoedzniem.");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Do widzenia!");
        dataReader.close();
    }

    private enum Option {
        EXIT(0, "Wyjście z programu"),
        ADD_BOOK(1, "Dodaj książkę"),
        ADD_MAGAZINE(2, "Dodaj magazyn"),
        PRINT_BOOKS(3, "Wyświetl dostępne książki"),
        PRINT_MAGAZINES(4, "Wyświetl dostępne magazyny"),
        DELETE_BOOK(5, "Usuń książkę"),
        DELETE_MAGAZINE(6, "Usuń magazyn"),
        ADD_USER(7, "Dodaj czytelnika"),
        PRINT_USERS(8, "Wyświetl czytelników");

        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException exception) {
                throw new NoSuchOptionException("Wybrano niepoprawny numer! - " + option);
            }
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }
    }
}
