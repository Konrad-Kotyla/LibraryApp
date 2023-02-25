package pl.javastart.library.model;

import pl.javastart.library.exception.PublicationAlreadyExistsException;
import pl.javastart.library.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Library implements Serializable {
    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public void addUser(LibraryUser user) {
        if (users.containsKey(user.getPesel()))
            throw new UserAlreadyExistsException(
                    "Użytkownik ze wskazanym peselem już istnieje " + user.getPesel()
            );
        users.put(user.getPesel(), user);
    }

    public void addPublication(Publication publication) {
        if (publications.containsKey(publication.getTitle()))
            throw new PublicationAlreadyExistsException(
                    "Publikacja o takim tytule już istnieje: " + publication.getTitle()
            );
        publications.put(publication.getTitle(), publication);
    }

    public boolean removePublication(Publication publication) {
        return publications.remove(publication.getTitle(), publication);
    }

    public Book findBookByIsbn(String isbn) {
        for (Publication publication : publications.values()) {
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
        for (Publication publication : publications.values()) {
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

