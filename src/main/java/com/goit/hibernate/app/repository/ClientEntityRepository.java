package com.goit.hibernate.app.repository;

import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class ClientEntityRepository {

    private final Datasource datasource;

    public ClientEntityRepository(Datasource datasource) {
        this.datasource = datasource;
    }

    public List<Client> findAll() {
        return dbCall(session -> session
                .createQuery("select c from Client c", Client.class)
                .getResultList());
    }

    public Client findById(Long id) {
        return dbCall(session -> {
            String queryString = "select c from Client c where c.id=:id";
            Query<Client> query = session.createQuery(queryString, Client.class);
            query.setParameter("id", id);
            Client result;
            try {
                result = query.getSingleResult();
            } catch (Exception e) {
                log.warn("no results found", e);
                result = null;
            }
            return result;
        });
    }

    public Client save(Client entity) {
        dbVoidCall(session -> persist(entity, session));
        return entity;
    }

    public Client update(Client entity) {
        dbVoidCall(session -> merge(entity, session));
        return entity;
    }

    public int delete(Client entity) {
        Long id = entity.getId();
        return deleteById(id);
    }

    public int deleteById(Long id) {
        return dbCall(session -> {
            String queryString = "delete from Client c where c.id=:id";
            MutationQuery mutationQuery = session.createMutationQuery(queryString);
            mutationQuery.setParameter("id", id);
            return mutationQuery.executeUpdate();
        });
    }

    private Client persist(Client entity, Session session) {
//        Long id = entity.getId();
        Client saved = session.merge(entity);
        entity.setId(saved.getId());
        return entity;
    }

    private Client merge(Client entity, Session session) {
        Client saved = session.merge(entity);
        entity.setId(saved.getId());
        return entity;
    }

    private <R> R dbCall(Function<Session, R> function) {
        try (Session session = datasource.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            log.error("db execution failed", e);
            throw new RuntimeException(e);
        }
    }

    private void dbVoidCall(Consumer<Session> function) {
        try (Session session = datasource.openSession()) {
            Transaction transaction = session.beginTransaction();
            function.accept(session);
            transaction.commit();
        } catch (Exception e) {
            log.error("db execution failed", e);
            throw new RuntimeException(e);
        }
    }
}
