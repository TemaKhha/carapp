CREATE TABLE IF NOT EXISTS users
(
    id    INT PRIMARY KEY ,
    name  VARCHAR(200) NOT NULL ,
    username VARCHAR(200) NOT NULL ,
    wallet INT  NOT NULL,
    access VARCHAR(20) NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 2 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS cars
(
    id    INT PRIMARY KEY ,
    model  VARCHAR(200) NOT NULL ,
    brand VARCHAR(200) NOT NULL ,
    price INT  NOT NULL,
    color VARCHAR(200) NOT NULL,
    image VARCHAR(300) NOT NULL,
    year INT NOT NULL,
    max_speed INT NOT NULL,
    user_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE SEQUENCE IF NOT EXISTS cars_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS buying
(
    id    INT PRIMARY KEY ,
    user_id INT NOT NULL,
    car_id INT NOT NULL,
    price INT NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20),

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
);
CREATE SEQUENCE IF NOT EXISTS buying_id_seq START WITH 2 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS service
(
    id    INT PRIMARY KEY ,
    user_id INT NOT NULL,
    car_id INT NOT NULL,
    price INT NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20),
    options VARCHAR(255) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (car_id) REFERENCES cars(id)
);
CREATE SEQUENCE IF NOT EXISTS service_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS memberships
(
    id INT PRIMARY KEY ,
    car_id INT NOT NULL,
    price INT NOT NULL,
    options VARCHAR(255) NOT NULL,
     FOREIGN KEY (car_id) REFERENCES cars(id)
);
CREATE SEQUENCE IF NOT EXISTS mem_id_seq START WITH 1 INCREMENT BY 1;
--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS buying;
--DROP sequence IF EXISTS buying_id_seq;