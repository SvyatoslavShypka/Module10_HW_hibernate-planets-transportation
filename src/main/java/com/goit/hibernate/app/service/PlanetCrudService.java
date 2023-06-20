package com.goit.hibernate.app.service;

import com.goit.hibernate.app.configuration.Environment;
import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.entity.Planet;
import com.goit.hibernate.app.repository.PlanetEntityRepository;

import java.util.List;

public class PlanetCrudService {

    private final Environment environment;
    private final PlanetEntityRepository planetEntityRepository;

    public PlanetCrudService() {
        environment = Environment.load();
        planetEntityRepository = new PlanetEntityRepository(new Datasource(environment));
    }

    public PlanetCrudService(Environment environment, PlanetEntityRepository planetEntityRepository) {
        this.environment = environment;
        this.planetEntityRepository = planetEntityRepository;
    }

    public List<Planet> findAll() {
        return planetEntityRepository.findAll();
    }

    public Planet findById(String id) {
        return planetEntityRepository.findById(id);
    }

    public Planet save(Planet entity) {
        return planetEntityRepository.save(entity);
    }

    public Planet update(Planet entity) {
        return planetEntityRepository.update(entity);
    }

    public int delete(Planet entity) {
        return planetEntityRepository.delete(entity);
    }

    public int deleteById(String id) {
        return planetEntityRepository.deleteById(id);
    }
}
