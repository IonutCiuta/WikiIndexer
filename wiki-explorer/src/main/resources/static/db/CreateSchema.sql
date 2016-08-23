create database if not exists WikiExplorer;
use WikiExplorer;


create table if not exists Query(
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(100),
	time LONG
);

create table if not exists Word(
	id INT PRIMARY KEY AUTO_INCREMENT,
	value VARCHAR(100)
);

create table if not exists Occurences(
	id INT PRIMARY KEY AUTO_INCREMENT,
	query_id INT NOT NULL,
	FOREIGN KEY (query_id) REFERENCES Query(id),
	word_id INT NOT NULL,
	FOREIGN KEY (word_id) REFERENCES Word(id),
	frequency LONG NOT NULL
);