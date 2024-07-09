package com.example.springj310.repository.interfaces;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

public interface ALlForRepository {

        default  <T> Optional<T> findById(Class<T> clazz, Long id){
            return Optional.of(getEm().find(clazz, id));
        }

        default <T> Stream<T> findAll(Class<T> clazz){
            CriteriaBuilder cb = getEm().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            cq.select(cq.from(clazz));
            return getEm().createQuery(cq).getResultList().stream();
        }

        @Transactional
        default <T> void remove(T entity){
            getEm().remove(entity);
        }

        @Transactional
        default <T> Optional<T> create(T person){
            getEm().persist(person);
            getEm().flush();
            return Optional.of(person);
        }

        @Transactional
        default <T> Optional<T> update(T person){
            getEm().merge(person);
            getEm().flush();
            return Optional.of(person);
        }

        EntityManager getEm();

    }
