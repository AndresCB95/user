CREATE TABLE user(
    user_id VARCHAR PRIMARY KEY NOT NULL,
    user_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    user_password VARCHAR NOT NULL,
    create_to DATE NOT NULL,
    modified_to DATE NOT NULL,
    last_login DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    token VARCHAR NOT NULL
);

CREATE TABLE phone(
    id_phone VARCHAR PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR NOT NULL,
    number_phone VARCHAR NOT NULL,
    city_code VARCHAR NOT NULL,
    contry_code VARCHAR NOT NULL
)