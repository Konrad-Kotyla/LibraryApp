package pl.javastart.library.model;

public class Library {
	private static final int MAX_PUBLICATIONS = 2000;
	Publication[] publications = new Book[MAX_PUBLICATIONS];
	private int publicationsNumber;

	public void addBook(Book book) {
		addPublication(book);
	}

	public void addMagazine(Magazine magazine) {
		addPublication(magazine);
	}

	private void addPublication(Publication publication) {
		if (publicationsNumber < MAX_PUBLICATIONS) {
			publications[publicationsNumber++] = publication;
		} else {
			throw new ArrayIndexOutOfBoundsException("Maksymalna liczba publikacji(" + MAX_PUBLICATIONS + ") została osiągnięta");
		}
	}

	public Publication[] getPublications() {
		Publication[] result = new Publication[publicationsNumber];
		for (int i = 0; i < publicationsNumber; i++) {
			result[i] = publications[i];
		}
		return result;
	}
}

