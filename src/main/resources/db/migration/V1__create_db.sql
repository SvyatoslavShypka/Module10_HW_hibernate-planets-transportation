-- Create the client table
CREATE TABLE client (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(200) CHECK(length(NAME) >= 3 and length(NAME) <= 200)
);

-- Create the planet table
CREATE TABLE planet (
                             id VARCHAR(50) NOT NULL CHECK(id = UPPER(id)) PRIMARY KEY,
                             name VARCHAR(500) NOT NULL UNIQUE CHECK(length(name) >= 1 and length(name) <= 500)
);

-- Create the ticket table
CREATE TABLE ticket (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_at TIMESTAMP,
                         client_id BIGINT,
                         from_planet_id VARCHAR(50),
                         to_planet_id VARCHAR(50),
                         FOREIGN KEY (client_id) REFERENCES client(id),
                         FOREIGN KEY (from_planet_id) REFERENCES planet(id),
                         FOREIGN KEY (to_planet_id) REFERENCES planet(id)
);