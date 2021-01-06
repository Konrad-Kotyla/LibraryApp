package pl.javastart.library.model;

public class Library {
    Publication[] publications = new Book[MAX_PUBLICATIONS];
    private static final int MAX_PUBLICATIONS = 2000;
    private int publicationsNumber;

    public void addBook(Book book) {
        if (publicationsNumber < MAX_PUBLICATIONS) {
            publications[publicationsNumber] = book;
            publicationsNumber++;
        } else {
            System.out.println("Maksymalna liczba książek została osiągnięta");
        }
    }

    public void printBooks() {
        int counter = 0;
        for (int i = 0; i < publicationsNumber; i++) {
            if (publications[i] instanceof Book) {
                System.out.println(publications[i]);
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("Brak książek w bibliotece");
        }
    }

    public void addMagazine(Magazine magazine) {
        if (publicationsNumber < MAX_PUBLICATIONS) {
            publications[publicationsNumber] = magazine;
            publicationsNumber++;
        } else {
            System.out.println("Maksymalna liczba magazynów została osiągnięta");
        }
    }

    public void printMagazines() {
        int counter = 0;
        for (int i = 0; i < publicationsNumber; i++) {
            if (publications[i] instanceof Magazine) {
                System.out.println(publications[i]);
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("Brak magazynów w bibliotece");
        }
    }
}

