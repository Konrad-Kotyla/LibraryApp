package pl.javastart.library.app;

import pl.javastart.library.exception.NoSuchOptionException;

public enum Option {
	EXIT(0, "wyjście z programu"),
	ADD_BOOK(1, "Dodaj książkę"),
	ADD_MAGAZINES(2, "Dodaj magazyn"),
	PRINT_BOOKS(3, "Wyświetl dostępne książki"),
	PRINT_MAGAZINES(4, "Wyświetl dostępne magazyny");

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

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return value + " - " + description;
	}
}
