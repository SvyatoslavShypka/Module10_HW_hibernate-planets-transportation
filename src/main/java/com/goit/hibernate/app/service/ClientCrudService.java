package com.goit.hibernate.app.service;

import com.goit.hibernate.app.configuration.Environment;
import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.entity.Client;
import com.goit.hibernate.app.repository.ClientEntityRepository;

import java.util.List;

public class ClientCrudService {

    private final Environment environment;
    private final ClientEntityRepository clientEntityRepository;

    public ClientCrudService() {
        environment = Environment.load();
        clientEntityRepository = new ClientEntityRepository(new Datasource(environment));
    }

    public ClientCrudService(Environment environment, ClientEntityRepository clientEntityRepository) {
        this.environment = environment;
        this.clientEntityRepository = clientEntityRepository;
    }

    public List<Client> findAll() {
        return clientEntityRepository.findAll();
    }

    public Client findById(Long id) {
        return clientEntityRepository.findById(id);
    }

    public Client save(Client entity) {
        return clientEntityRepository.save(entity);
    }

    public Client update(Client entity) {
        return clientEntityRepository.update(entity);
    }

    public int delete(Client entity) {
        return clientEntityRepository.delete(entity);
    }

    public int deleteById(Long id) {
        return clientEntityRepository.deleteById(id);
    }
}
