package pl.javastart.library.app;

import pl.javastart.library.exception.NoSuchOptionException;
import pl.javastart.library.io.ConsolePrinter;
import pl.javastart.library.io.DataReader;
import pl.javastart.library.model.Book;
import pl.javastart.library.model.Library;
import pl.javastart.library.model.Magazine;
import pl.javastart.library.model.Publication;

import java.util.InputMismatchException;

public class LibraryControl {
	ConsolePrinter printer = new ConsolePrinter();
	DataReader dataReader = new DataReader(printer);
	Library library = new Library();

	public void controlLoop() {
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
			}
		} while (option != Option.EXIT);
	}

	private void printOptions() {
		printer.printLine("Wybierz jedną z opcji: ");
		for (Option option : Option.values()) {
			printer.printLine(option.toString());
		}
	}

	private void exit() {
		printer.printLine("Do widzenia!");
		dataReader.close();
	}

	private void addBook() {
		try {
			Book book = dataReader.readAndCreateBook();
			library.addBook(book);
		} catch (InputMismatchException ex) {
			printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
		} catch (ArrayIndexOutOfBoundsException e) {
			printer.printLine(e.getMessage());
		}
	}

	private void printBooks() {
		Publication[] publications = library.getPublications();
		printer.printBooks(publications);
	}

	private void addMagazines() {
		try {
			Magazine magazine = dataReader.readAndCreateMagazine();
			library.addMagazine(magazine);
		} catch (InputMismatchException ex) {
			printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
		} catch (ArrayIndexOutOfBoundsException e) {
			printer.printLine(e.getMessage());
		}
	}

	private void printMagazines() {
		Publication[] publications = library.getPublications();
		printer.printMagazines(publications);
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
}
