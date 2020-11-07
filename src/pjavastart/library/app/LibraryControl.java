package pjavastart.library.app;

import pjavastart.library.io.DataReader;
import pjavastart.library.model.Book;
import pjavastart.library.model.Library;

public class LibraryControl {
    private static final int EXIT = 0;
    private static final int ADD_BOOK = 1;
    private static final int PRINT_BOOKS = 2;

    DataReader dataReader = new DataReader();
    Library library = new Library();

    public void controlLoop() {
        int option;

        do {
            printOptions();
            option = dataReader.getInt();

            switch (option) {
                case EXIT:
                    exit();
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                default:
                    System.out.println("Nie znaleziono takiej opcji.");
            }
        } while (option != EXIT);
    }

    private void printOptions() {
        System.out.println("Wybierz jedną z opcji: ");
        System.out.println(EXIT + " - wyjście z programu");
        System.out.println(ADD_BOOK + " - dodaj książkę");
        System.out.println(PRINT_BOOKS + " - wyświetl dostępne książki");
    }

    private void exit() {
        System.out.println("Do widzenia!");
        dataReader.close();
    }

    private void addBook() {
        Book book = dataReader.readAndCreateBook();
        library.addBook(book);
    }

    private void printBooks() {
        library.printBooks();
    }
}
