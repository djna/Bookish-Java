


CREATE TABLE books (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title varchar(500) NOT NULL,
  author varchar(500) NOT NULL,
  isbn varchar(20) NOT NULL
);

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE copies (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  book_id int(11) NOT NULL,
  borrower_id int(11),
  due_date date,
  FOREIGN KEY (book_id) REFERENCES books (id),
  FOREIGN KEY (borrower_id) REFERENCES users (id)
);
