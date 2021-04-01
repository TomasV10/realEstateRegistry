CREATE TABLE tax_rates
(
    id            bigint         NOT NULL AUTO_INCREMENT,
    rate          decimal(19, 2) NOT NULL,
    property_type varchar(255)   NOT NULL,
    valid_from    date           NOT NULL,
    valid_to      date,
    PRIMARY KEY (id)
);

CREATE TABLE owners
(
    id        bigint       NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE properties
(
    id            bigint         NOT NULL AUTO_INCREMENT,
    area_size     decimal(19, 2) NOT NULL,
    market_value  decimal(19, 2) NOT NULL,
    address       VARCHAR(255)   NOT NULL,
    property_type VARCHAR(255)   NOT NULL,
    owner_id      bigint         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES owners (id)
);
