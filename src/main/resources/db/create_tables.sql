CREATE DATABASE IF NOT EXISTS projectsdb;
CREATE TABLE IF NOT EXISTS projectsdb.projects(
     id int NOT NULL AUTO_INCREMENT primary key
);
ALTER TABLE projectsdb.projects ADD description VARCHAR(256);