DROP TABLE Books;

CREATE TABLE Books (
	Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Author VARCHAR(50) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Date DATE NOT NULL,
	Price DECIMAL(6, 2)
);

INSERT INTO Books(Author, Name, Date, Price) values ('Alicja Abacka', 'JAVA for beginners', '2015-01-23', 59.99);
INSERT INTO Books(Author, Name, Date, Price) values ('Alicja Abacka', 'JAVA for professionals', '2016-11-01', 169.99);
INSERT INTO Books(Author, Name, Date, Price) values ('Benedykt Bananowy', 'WEB applications in JAVA', '2015-01-23', 59.99);
INSERT INTO Books(Author, Name, Date, Price) values ('Celina Czytata', 'Design patterns for software developers', '2015-01-23', 41.00);
INSERT INTO Books(Author, Name, Date, Price) values ('Seweryn Hostowy', 'Tomcat application server', '2015-01-23', 20.00);

select * from Books;