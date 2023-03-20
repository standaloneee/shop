create table role
(
    id        UUID PRIMARY KEY ,
    role_name VARCHAR(15) NOT NULL
);

create table notification
(
    id        UUID PRIMARY KEY,
    subject varchar NOT NULL,
    start_date DATE NOT NULL,
    description varchar NOT NULL
);

CREATE TABLE parameters
(
    id          UUID PRIMARY KEY,
    description VARCHAR NOT NULL
);

CREATE TABLE product
(
    id               UUID PRIMARY KEY,
    name             VARCHAR          NOT NULL,
    description      VARCHAR          NOT NULL,
    price            DOUBLE PRECISION NOT NULL,
    quantity         INT              NOT NULL,
    sale_description VARCHAR          NOT NULL,
    tags             VARCHAR,
    grade            INT2,
    parameter_id     UUID,
    FOREIGN KEY (parameter_id) REFERENCES parameters (id)

);

CREATE TABLE customer
(
    id            UUID PRIMARY KEY,
    email         VARCHAR          NOT NULL,
    password VARCHAR          NOT NULL,
    username VARCHAR          NOT NULL,
    balance       DOUBLE PRECISION NOT NULL
);

create table users_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES customer (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE feedback
(
    id          UUID PRIMARY KEY,
    subject     VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    product_id UUID NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (product_id) references product(id),
    FOREIGN KEY (user_id) references customer (id)
);



CREATE TABLE sale
(
    id            UUID PRIMARY KEY,
    discount      INT2              NOT NULL,
    start_date DATE             NOT NULL,
    balance       DOUBLE PRECISION NOT NULL,
    expiration_time int              NOT NULL
);

CREATE TABLE organization
(
    id          UUID PRIMARY KEY,
    description VARCHAR NOT NULL,
    logo_url    VARCHAR
);



CREATE TABLE organization_products
(
    organization_id UUID NOT NULL,
    product_id UUID NOT NULL,
    FOREIGN KEY (organization_id) REFERENCES organization(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);


CREATE TABLE product_feedbacks
(
    product_id UUID NOT NULL,
    feedback_id UUID NOT NULL,
    foreign key (product_id) references product(id),
    foreign key (feedback_id) references feedback(id)
);

CREATE TABLE product_users
(
    user_id UUID NOT NULL,
    product_id UUID NOT NULL,
    foreign key (user_id) references customer (id),
    foreign key (product_id) references product(id)
);
CREATE TABLE product_sales
(
    id UUID PRIMARY KEY,
    sale_id UUID NOT NULL,
    product_id UUID NOT NULL,
    foreign key (sale_id) references sale (id),
    foreign key (product_id) references product(id)
);



