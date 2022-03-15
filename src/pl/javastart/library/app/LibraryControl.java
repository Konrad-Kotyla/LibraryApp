package pl.javastart.library.app;

import pl.javastart.library.exception.DataExportException;
import pl.javastart.library.exception.DataImportException;
import pl.javastart.library.exception.InvalidDataException;
import pl.javastart.library.exception.NoSuchOptionException;
import pl.javastart.library.file.FileManager;
import pl.javastart.library.file.FileManagerBuilder;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;
import pl.javastart.library.model.comparator.AlphabeticalTitleComparator;

import java.util.Arrays;
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
                case EXIT:
                    exit();
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINES:
                    addMagazines();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
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

    private void printOptions() {
        printer.printLine("Wybierz jedną z opcji: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
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

    private void printBooks() {
        Publication[] publications = getSortedPublications();
        printer.printBooks(publications);
    }

    private void addMagazines() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException ex) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printMagazines() {
        Publication[] publications = getSortedPublications();
        printer.printMagazines(publications);
    }

    private Publication[] getSortedPublications() {
        Publication[] publications = library.getPublications();
        Arrays.sort(publications, new AlphabeticalTitleComparator());
        return publications;
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

    private enum Option {
        EXIT(0, "wyjście z programu"),
        ADD_BOOK(1, "Dodaj książkę"),
        ADD_MAGAZINES(2, "Dodaj magazyn"),
        PRINT_BOOKS(3, "Wyświetl dostępne książki"),
        PRINT_MAGAZINES(4, "Wyświetl dostępne magazyny"),
        DELETE_BOOK(5, "Usuń książkę"),
        DELETE_MAGAZINE(6, "Usuń magazyn");

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
