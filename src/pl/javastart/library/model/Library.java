package pl.javastart.library.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Library implements Serializable {
    private static final int INITIAL_CAPACITY = 1;
    private int publicationsNumber;
    Publication[] publications = new Publication[INITIAL_CAPACITY];

    public Publication[] getPublications() {
        Publication[] result = new Publication[publicationsNumber];
        for (int i = 0; i < publicationsNumber; i++) {
            result[i] = publications[i];
        }
        return result;
    }

    public void addPublication(Publication publication) {
        if (publicationsNumber == publications.length) {
            publications = Arrays.copyOf(publications, publications.length * 2);
        }
        publications[publicationsNumber] = publication;
        publicationsNumber++;
    }

    public boolean removePublication(Publication publication) {
        final int NOT_FOUND = -1;
        int found = NOT_FOUND;
        int counter = 0;

        while (counter < publicationsNumber && found == NOT_FOUND) {
            if (publication.equals(publications[counter])) {
                found = counter;
            } else {
                counter++;
            }
        }
        if (found != NOT_FOUND) {
            System.arraycopy(publications, found + 1, publications, found, publications.length - found - 1);
            publicationsNumber--;
        }
        return found != NOT_FOUND;
    }

    public Book findBookByIsbn(String isbn) {
        for (Publication publication : publications) {
            if (publication.getClass() == Book.class) {
                Book book = (Book) publication;
                if (book.getIsbn().equals(isbn)) {
                    return book;
                }
            }
        }
        throw new NoSuchElementException("Nie znaleziono takiej książki!");
    }

    public Magazine findMagazineByTitleAndPublicationDate(String title, int day, int month, int year) {
        for (Publication publication : publications) {
            if (publication.getClass() == Magazine.class) {
                Magazine magazine = (Magazine) publication;
                if (magazine.getTitle().equals(title) && magazine.getDay() == day &&
                        magazine.getMonth() == month && magazine.getYear() == year) {
                    return magazine;
                }
            }
        }
        throw new NoSuchElementException("Nie znaleziono takiego magazynu!");
    }
}

