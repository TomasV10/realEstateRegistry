INSERT INTO owners (full_name)
VALUES ('Tomas Varnauskas'),
       ('Jonas Cimbala');

INSERT INTO properties (address, owner_id, area_size, market_value, property_type)
VALUES ('Kaunas, Savanoriu pr. 288', 1, 288.45, 440000, 'APARTMENT'),
       ('Vilnius, Saltoniskiu g. 13', 2, 795.21, 2345600, 'VILLA'),
       ('Ukmerge, Akemnu g. 3', 2, 120.11, 345600, 'MOBILE_HOME');

INSERT INTO tax_rates (property_type, rate, valid_from, valid_to)
VALUES ('APARTMENT', 0.03, '2021-01-12', null),
       ('VILLA', 0.5, '2021-01-12', '2021-02-02'),
       ('MOBILE_HOME', 0.03, '2021-01-12', '2021-02-02'),
       ('VILLA', 0.67, '2021-02-02', null),
       ('MOBILE_HOME', 0.85, '2021-02-02', null),
       ('HOUSE', 0.05, '2021-01-01', null);

