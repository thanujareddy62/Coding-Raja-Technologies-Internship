CREATE DATABASE LibraryManagement;

USE LibraryManagement;

CREATE TABLE Books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    genre VARCHAR(50),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE Patrons (
    patron_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    contact_info VARCHAR(100)
);

CREATE TABLE BorrowingRecords (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    patron_id INT,
    borrow_date DATE,
    return_date DATE,
    fine DOUBLE DEFAULT 0,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (patron_id) REFERENCES Patrons(patron_id)
);

