CREATE TABLE product(
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INT NOT NULL,
    sale_description VARCHAR NOT NULL,
    tags VARCHAR,
    grade INT
);

CREATE TABLE feedback(
    id UUID PRIMARY KEY,
    subject VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE userentity(
    id UUID PRIMARY KEY,
    email VARCHAR NOT NULL,
    password_hash VARCHAR NOT NULL,
    balance DOUBLE PRECISION NOT NULL
);

CREATE TABLE sale(
    id UUID PRIMARY KEY,
    discount INT NOT NULL,
    startSaleDate DATE NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    hoursToExpire int NOT NULL
);

