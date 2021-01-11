package pl.javastart.library.app;

import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;

public class LibraryControl {
    DataReader dataReader = new DataReader();
    Library library = new Library();

    public void controlLoop() {
        Option option;

        do {
            printOptions();
            option = Option.createFromInt(dataReader.getInt());

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
            }
        } while (option != Option.EXIT);
    }

    private void printOptions() {
        System.out.println("Wybierz jedną z opcji: ");
        for (Option option: Option.values()) {
            System.out.println(option);
        }
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

    private void addMagazines() {
        Magazine magazine = dataReader.readAndCreateMagazine();
        library.addMagazine(magazine);
    }

    private void printMagazines() {
        library.printMagazines();
    }
}
