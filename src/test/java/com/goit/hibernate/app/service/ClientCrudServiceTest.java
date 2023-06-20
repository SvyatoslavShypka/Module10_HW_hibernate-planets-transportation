package com.goit.hibernate.app.service;

import com.goit.hibernate.app.HibernateApplicationTest;
import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.entity.Client;
import com.goit.hibernate.app.entity.CustomerEntity;
import com.goit.hibernate.app.repository.ClientEntityRepository;
import com.goit.hibernate.app.repository.CustomerEntityRepository;
import com.goit.hibernate.app.test.utils.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientCrudServiceTest extends HibernateApplicationTest {

    private ClientCrudService clientCrudService;

    @BeforeEach
    void beforeEach() {
        clientCrudService = new ClientCrudService(environment,
                new ClientEntityRepository(datasource));
    }

    @Test
    @DisplayName("Check if clients exist when findAll() invoked")
    void findAll() {
        //Given
        final int expectedSize = 3;

        //When
        List<Client> all = clientCrudService.findAll();

        //Then
        assertFalse(all.isEmpty());
        assertEquals(expectedSize, all.size());

    }

    @Test
    void findById() {
        //Given
        final long testId;

        List<Client> all = clientCrudService.findAll();
        testId = all.get(0).getId();

        //When
        Client client = clientCrudService.findById(testId);

        //Then
        assertNotNull(client);
        assertEquals(testId, client.getId());
    }

    @Test
    void save() {
        //Given
        Client testClient = TestUtils.createTestClient();

        //When
        Client savedClient = clientCrudService.save(testClient);
        Client clientById = clientCrudService.findById(savedClient.getId());

        //Then
        assertNotNull(savedClient);
        assertEquals(testClient.getId(), clientById.getId());
    }

    @Test
    void updateNewClient() {
        //Given
        Client testClient = TestUtils.createTestClient();

        //When
        Client savedClient = clientCrudService.update(testClient);
        Client clientById = clientCrudService.findById(savedClient.getId());

        //Then
        assertNotNull(savedClient);
        assertEquals(testClient.getId(), clientById.getId());
    }

    @Test
    void updateExistedClient() {
        //Given
        List<Client> allClients = clientCrudService.findAll();
        Client testClient = allClients.get(1);
        testClient.setName("New Name");

        //When
        Client savedClient = clientCrudService.update(testClient);
        Long expectedId = savedClient.getId();
        Client clientById = clientCrudService.findById(savedClient.getId());

        //Then
        assertNotNull(savedClient);
        assertEquals(expectedId, clientById.getId());

    }

    @Test
    void delete() {
        //Given
        int expectedDeletedCount = 1;

        //When
        List<Client> all = clientCrudService.findAll();
        Client clientToDelete = all.get(0);
        int deleteCount = clientCrudService.delete(clientToDelete);

        //Then
        assertEquals(expectedDeletedCount, deleteCount);
    }

    @Test
    void deleteById() {
        //Given
        int expectedDeletedCount = 1;

        //When
        List<Client> all = clientCrudService.findAll();
        Long idToDelete = all.get(0).getId();
        int deleteCount = clientCrudService.deleteById(idToDelete);

        //Then
        assertEquals(expectedDeletedCount, deleteCount);
        assertNull(clientCrudService.findById(idToDelete));
    }
}