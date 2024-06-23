import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    private BookDAO bookDAO = new BookDAO();
    private PatronDAO patronDAO = new PatronDAO();
    private BorrowingRecordDAO borrowingRecordDAO = new BorrowingRecordDAO();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Patron");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Calculate Fine");
            System.out.println("6. Search Books");
            System.out.println("7. Search Patrons");
            System.out.println("8. Generate Reports");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addPatron();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    calculateFine();
                    break;
                case 6:
                    searchBooks();
                    break;
                case 7:
                    searchPatrons();
                    break;
                case 8:
                    generateReports();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        Book book = new Book(title, author, genre, true);
        bookDAO.addBook(book);
        System.out.println("Book added successfully.");
    }

    private void addPatron() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();
        Patron patron = new Patron(name, contactInfo);
        patronDAO.addPatron(patron);
        System.out.println("Patron added successfully.");
    }

    private void borrowBook() {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter patron ID: ");
        int patronId = scanner.nextInt();
        BorrowingRecord record = new BorrowingRecord(bookId, patronId, new Date(), null, 0);
        borrowingRecordDAO.addBorrowingRecord(record);
        System.out.println("Book borrowed successfully.");
    }

    private void returnBook() {
        // Implement return book logic
    }

    private void calculateFine() {
        // Implement fine calculation logic
    }

    private void searchBooks() {
        // Implement search books logic
    }

    private void searchPatrons() {
        // Implement search patrons logic
    }

    private void generateReports() {
        // Implement report generation logic
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.run();
    }
}

