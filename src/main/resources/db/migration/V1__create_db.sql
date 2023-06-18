-- Create the client table
CREATE TABLE client (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(200)
);

-- Create the planet table
CREATE TABLE planet (
                             id VARCHAR(50) NOT NULL PRIMARY KEY,
                             name VARCHAR(500) NOT NULL UNIQUE
);

-- Create the ticket table
CREATE TABLE ticket (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         created_at TIMESTAMP,
                         client_id BIGINT,
                         from_planet_id VARCHAR(50),
                         to_planet_id VARCHAR(50),
                         FOREIGN KEY (client_id) REFERENCES clients(id),
                         FOREIGN KEY (from_planet_id) REFERENCES planets(id),
                         FOREIGN KEY (to_planet_id) REFERENCES planets(id)
);