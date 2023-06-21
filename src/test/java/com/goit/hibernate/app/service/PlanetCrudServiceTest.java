package com.goit.hibernate.app.service;

import com.goit.hibernate.app.HibernateApplicationTest;
import com.goit.hibernate.app.entity.Planet;
import com.goit.hibernate.app.repository.PlanetEntityRepository;
import com.goit.hibernate.app.test.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlanetCrudServiceTest extends HibernateApplicationTest {

    private PlanetCrudService planetCrudService;

    @BeforeEach
    void beforeEach() {
        planetCrudService = new PlanetCrudService(environment,
                new PlanetEntityRepository(datasource));
    }

    @Test
    @DisplayName("Check if planets exist when findAll() invoked")
    void findAll() {

        //Given
        final int expectedSize = 3;

        //When
        List<Planet> all = planetCrudService.findAll();

        //Then
        assertFalse(all.isEmpty());
        assertEquals(expectedSize, all.size());
    }

    @Test
    void findById() {
        //Given
        final String testId;

        List<Planet> all = planetCrudService.findAll();
        testId = all.get(0).getId();

        //When
        Planet planet = planetCrudService.findById(testId);

        //Then
        assertNotNull(planet);
        assertEquals(testId, planet.getId());
    }

    @Test
    void save() {
        //Given
        Planet testPlanet = TestUtils.createTestPlanet();

        //When
        Planet savedPlanet = planetCrudService.save(testPlanet);
        Planet planetById = planetCrudService.findById(savedPlanet.getId());

        //Then
        assertNotNull(savedPlanet);
        assertEquals(testPlanet.getId(), planetById.getId());
    }

    @Test
    void updateNewPlanet() {
        //Given
        Planet testPlanet = TestUtils.createTestPlanet();

        //When
        Planet savedPlanet = planetCrudService.update(testPlanet);
        Planet planetById = planetCrudService.findById(savedPlanet.getId());

        //Then
        assertNotNull(savedPlanet);
        assertEquals(testPlanet.getId(), planetById.getId());
    }

    @Test
    void updateExistedPlanet() {
        //Given
        List<Planet> allPlanets = planetCrudService.findAll();
        Planet testPlanet = allPlanets.get(1);
        testPlanet.setName("New Name");

        //When
        Planet savedPlanet = planetCrudService.update(testPlanet);
        String expectedId = savedPlanet.getId();
        Planet clientById = planetCrudService.findById(savedPlanet.getId());

        //Then
        assertNotNull(savedPlanet);
        assertEquals(expectedId, clientById.getId());
    }

    @Test
    void delete() {
        //Given
        int expectedDeletedCount = 1;

        //When
        List<Planet> all = planetCrudService.findAll();
        Planet planetToDelete = all.get(0);
        int deleteCount = planetCrudService.delete(planetToDelete);

        //Then
        assertEquals(expectedDeletedCount, deleteCount);

    }

    @Test
    void deleteById() {
        //Given
        int expectedDeletedCount = 1;

        //When
        List<Planet> all = planetCrudService.findAll();
        String idToDelete = all.get(0).getId();
        int deleteCount = planetCrudService.deleteById(idToDelete);

        //Then
        assertEquals(expectedDeletedCount, deleteCount);
        assertNull(planetCrudService.findById(idToDelete));

    }
}
