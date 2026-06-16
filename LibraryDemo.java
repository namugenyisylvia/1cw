import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;

    // Overloaded Constructor 1
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    // Overloaded Constructor 2 
    public Book(String isbn, String title, String author, boolean isAvailable) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    // Encapsulation: Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, Title='%s', Author='%s', Available=%b]", 
                isbn, title, author, isAvailable);
    }
}

class Member {
    private String memberId;
    private String name;
    private List<Loan> currentLoans;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.currentLoans = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Loan> getCurrentLoans() { return currentLoans; }

    public void addLoan(Loan loan) {
        currentLoans.add(loan);
    }

    public void removeLoan(Loan loan) {
        currentLoans.remove(loan);
    }

    @Override
    public String toString() {
        return String.format("Member[ID=%s, Name='%s', Active Loans=%d]", 
                memberId, name, currentLoans.size());
    }
}

class Loan {
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public Loan(Member member, Book book, LocalDate borrowDate, LocalDate dueDate) {
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Member getMember() { return member; }
    public Book getBook() { return book; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return String.format("Loan[Book='%s', Member='%s', Borrowed=%s, Due=%s]", 
                book.getTitle(), member.getName(), borrowDate, dueDate);
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;
    private List<Loan> activeLoans;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.activeLoans = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    // 1. Lend book with "one active loan per book" rule validation
    public void lendBook(Member member, Book book, LocalDate dueDate) {
        if (!book.isAvailable()) {
            System.out.println("REJECTED: '" + book.getTitle() + "' is currently on loan and unavailable.");
            return;
        }
        
        Loan newLoan = new Loan(member, book, LocalDate.now(), dueDate);
        activeLoans.add(newLoan);
        member.addLoan(newLoan);
        book.setAvailable(false);
        System.out.println("SUCCESS: '" + book.getTitle() + "' successfully lent to " + member.getName() + ".");
    }

    // 2. Return book
    public void returnBook(Book book) {
        Loan loanToRemove = null;
        for (Loan loan : activeLoans) {
            if (loan.getBook().equals(book)) {
                loanToRemove = loan;
                break;
            }
        }

        if (loanToRemove != null) {
            activeLoans.remove(loanToRemove);
            loanToRemove.getMember().removeLoan(loanToRemove);
            book.setAvailable(true);
            System.out.println("SUCCESS: '" + book.getTitle() + "' has been returned to the library.");
        } else {
            System.out.println("ERROR: '" + book.getTitle() + "' is not recorded as an active loan.");
        }
    }

    public List<Book> searchBookByTitle(String keyword) {
        List<Book> matches = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(book);
            }
        }
        return matches;
    }

    public void printLibraryState() {
        System.out.println("\n--- Current Library State ---");
        System.out.println("Books in Catalog:");
        for (Book b : books) { System.out.println("  " + b); }
        System.out.println("Registered Members:");
        for (Member m : members) { System.out.println("  " + m); }
        System.out.println("Active Loans:");
        for (Loan l : activeLoans) { System.out.println("  " + l); }
        System.out.println("-----------------------------\n");
    }
}

public class LibraryDemo {
    public static void main(String[] args) {
        // Create library system
        Library myLibrary = new Library();

        // (d) Create Members
        Member member1 = new Member("M001", "Alice Smith");
        Member member2 = new Member("M002", "Bob Jones");
        myLibrary.registerMember(member1);
        myLibrary.registerMember(member2);

        // Create Books
        Book book1 = new Book("978-0134685991", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("978-0136042594", "Clean Code", "Robert C. Martin");
        Book book3 = new Book("978-0201633610", "Design Patterns", "Erich Gamma");
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);
        myLibrary.addBook(book3);

        // Print initial state
        System.out.println("--- Initial State ---");
        myLibrary.printLibraryState();

        // Perform operations (Including a correct rejection)
        LocalDate dueDate = LocalDate.now().plusWeeks(2);
        
        System.out.println("--- Operations ---");
        myLibrary.lendBook(member1, book1, dueDate); // Success
        myLibrary.lendBook(member2, book2, dueDate); // Success
        
        // This lend operation will be gracefully rejected because book1 is already on loan
        myLibrary.lendBook(member2, book1, dueDate); // Should be Rejected
        
        // Return a book
        myLibrary.returnBook(book1); // Success

        // Try lending it again after return
        myLibrary.lendBook(member2, book1, dueDate); // Should now succeed

        // Print final state
        System.out.println("\n--- Final State ---");
        myLibrary.printLibraryState();
        
        // Search functionality
        System.out.println("--- Search Results for 'Clean' ---");
        for (Book b : myLibrary.searchBookByTitle("Clean")) {
            System.out.println(b);
        }
    }
}
