-- insert statements for Table 1: clients
INSERT INTO client (name) VALUES
('Client1'),
('Client2'),
('Client3'),
('Client4'),
('Client5'),
('Client6'),
('Client7'),
('Client8'),
('Client9'),
('Client10')
;

-- insert statements for Table 2: planets
INSERT INTO planet (id, name) VALUES
('SOLAR001', 'Mars'),
('SOLAR002', 'Earth'),
('SOLAR003', 'Jupiter'),
('SOLAR004', 'Mercury'),
('SOLAR005', 'Neptune')
;

-- insert statements for Table 3: tickets
INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
('2029-09-25 16:28:45', 4, 'SOLAR001', 'SOLAR004'),
('2029-09-26 16:28:45', 2, 'SOLAR004', 'SOLAR001'),
('2029-09-27 16:28:45', 3, 'SOLAR002', 'SOLAR005'),
('2029-09-28 16:28:45', 7, 'SOLAR005', 'SOLAR002'),
('2029-09-29 16:28:45', 9, 'SOLAR001', 'SOLAR003'),
('2029-09-30 16:28:45', 10, 'SOLAR001', 'SOLAR004'),
('2029-10-02 16:28:45', 1, 'SOLAR003', 'SOLAR004'),
('2029-11-05 16:28:45', 5, 'SOLAR002', 'SOLAR005'),
('2029-12-26 16:28:45', 6, 'SOLAR005', 'SOLAR003'),
('2029-12-26 10:28:45', 8, 'SOLAR005', 'SOLAR004')
;