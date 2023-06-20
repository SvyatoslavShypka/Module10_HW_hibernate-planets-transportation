package com.goit.hibernate.app.test.utils;

import com.goit.hibernate.app.entity.Client;
import com.goit.hibernate.app.entity.CustomerEntity;
import com.goit.hibernate.app.entity.Planet;

public final class TestUtils {

    public static CustomerEntity createTestCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setName("John Doe");
        customer.setCountry("Ukraine");
        customer.setContactName("John Doe1");

        return customer;
    }

    public static Client createTestClient() {
        Client client = new Client();
        client.setName("Test Client");
        return client;
    }

    public static Planet createTestPlanet() {
        Planet planet = new Planet();
        planet.setId("SOLAR100");
        planet.setName("Test Client");
        return planet;
    }
}
