create table role
(
    id        UUID PRIMARY KEY,
    role_name VARCHAR(15) NOT NULL
);

create table tag
(
    id   UUID PRIMARY KEY,
    name VARCHAR NOT NULL
);

create table notification
(
    id          UUID PRIMARY KEY,
    subject     VARCHAR NOT NULL,
    start_date  DATE    NOT NULL,
    description VARCHAR NOT NULL
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
    grade            DOUBLE PRECISION,
    parameter_id     UUID,
    all_grades_value DOUBLE PRECISION,
    number_of_grades INT,
    FOREIGN KEY (parameter_id) REFERENCES parameters (id)

);

CREATE TABLE customer
(
    id       UUID PRIMARY KEY,
    email    VARCHAR          NOT NULL,
    password VARCHAR          NOT NULL,
    username VARCHAR          NOT NULL,
    balance  DOUBLE PRECISION NOT NULL
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
    user_id     UUID    NOT NULL,
    grade            DOUBLE PRECISION,
    FOREIGN KEY (user_id) REFERENCES customer (id)
);


CREATE TABLE sale
(
    id              UUID PRIMARY KEY,
    discount        INT2             NOT NULL,
    start_date      DATE             NOT NULL,
    expiration_time INT              NOT NULL
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
    product_id      UUID NOT NULL,
    FOREIGN KEY (organization_id) REFERENCES organization (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);


CREATE TABLE product_feedbacks
(
    product_id  UUID NOT NULL,
    feedback_id UUID NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (feedback_id) REFERENCES feedback (id) ON DELETE CASCADE
);

CREATE TABLE product_users
(
    user_id    UUID NOT NULL,
    product_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES customer (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE product_sales
(
    sale_id    UUID NOT NULL,
    product_id UUID NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sale (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE product_tags
(
    tag_id     UUID NOT NULL,
    product_id UUID NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tag (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
CREATE TABLE sell_history
(
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    customer_id     UUID NOT NULL,
    purchase_date DATE  NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);




