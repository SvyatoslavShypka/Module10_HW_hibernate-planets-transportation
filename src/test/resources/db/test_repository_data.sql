-- Create the client table
CREATE TABLE IF NOT EXISTS client (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200) CHECK(length(NAME) >= 3 and length(NAME) <= 200)
);

DELETE FROM client;

-- insert statements for Table 1: client
INSERT INTO client (name) VALUES
  ('Client1'),
  ('Client2'),
  ('Client3')
;

-- Create the planet table
CREATE TABLE IF NOT EXISTS planet (
  id VARCHAR(50) NOT NULL CHECK(id = UPPER(id)) PRIMARY KEY,
  name VARCHAR(500) NOT NULL UNIQUE CHECK(length(name) >= 1 and length(name) <= 500)
);

DELETE FROM planet;

-- insert statements for Table 2: planet
INSERT INTO planet (id, name) VALUES
  ('SOLAR001', 'Mars'),
  ('SOLAR002', 'Earth'),
  ('SOLAR003', 'Jupiter')
;

