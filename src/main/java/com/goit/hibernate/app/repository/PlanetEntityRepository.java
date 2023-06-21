package com.goit.hibernate.app.repository;

import com.goit.hibernate.app.configuration.hibernate.Datasource;
import com.goit.hibernate.app.entity.Planet;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class PlanetEntityRepository {

    private final Datasource datasource;

    public PlanetEntityRepository(Datasource datasource) {
        this.datasource = datasource;
    }

    public List<Planet> findAll() {
        return dbCall(session -> session
                .createQuery("select c from Planet c", Planet.class)
                .getResultList());
    }

    public Planet findById(String id) {
        return dbCall(session -> {
            String queryString = "select c from Planet c where c.id=:id";
            Query<Planet> query = session.createQuery(queryString, Planet.class);
            query.setParameter("id", id);
            Planet result;
            try {
                result = query.getSingleResult();
            } catch (Exception e) {
                log.warn("no results found", e);
                result = null;
            }
            return result;
        });
    }

    public Planet save(Planet entity) {
        dbVoidCall(session -> persist(entity, session));
        return entity;
    }

    public int delete(Planet entity) {
        String id = entity.getId();
        return deleteById(id);
    }

    public int deleteById(String id) {
        return dbCall(session -> {
            String queryString = "delete from Planet c where c.id=:id";
            MutationQuery mutationQuery = session.createMutationQuery(queryString);
            mutationQuery.setParameter("id", id);
            return mutationQuery.executeUpdate();
        });
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

    public Planet update(Planet entity) {
        dbVoidCall(session -> merge(entity, session));
        return entity;
    }

    private Planet persist(Planet entity, Session session) {
        Planet saved = session.merge(entity);
        entity.setId(saved.getId());
        return entity;
    }

    private Planet merge(Planet entity, Session session) {
        Planet saved = session.merge(entity);
        entity.setId(saved.getId());
        return entity;
    }
}
