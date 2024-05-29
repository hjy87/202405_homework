CREATE TABLE price (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       brand_id BIGINT NOT NULL,
                       category_id BIGINT NOT NULL,
                       price BIGINT NOT NULL
);

CREATE TABLE brand (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(20) NOT NULL
);

CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(20) NOT NULL
);