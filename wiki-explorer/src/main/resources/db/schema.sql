create database if not exists wiki_explorer;
use wiki_explorer;


create table if not exists analysis(
	id INT PRIMARY KEY AUTO_INCREMENT,
	query_titles VARCHAR(100) NOT NULL,
	query_length LONG,
	UNIQUE (query_titles)
);

create table if not exists word(
	id INT PRIMARY KEY AUTO_INCREMENT,
	value VARCHAR(100) NOT NULL,
	UNIQUE (value)
);

create table if not exists occurrence(
	id INT PRIMARY KEY AUTO_INCREMENT,
	query_id INT NOT NULL,
	FOREIGN KEY (query_id) REFERENCES analysis(id),
	word_id INT NOT NULL,
	FOREIGN KEY (word_id) REFERENCES word(id),
	frequency LONG NOT NULL,
	UNIQUE (query_id, word_id)
);