package com.example.springj310.repository.impl;

import com.example.springj310.entity.ClientsEntity;

import com.example.springj310.repository.interfaces.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
public class ClientRepositoryImpl implements ClientRepository {
    @PersistenceContext
    private EntityManager em;

  //  @Override
   // public Optional<ClientsEntity> findClientById(Long id) {
//
   //     return Optional.of(em.find(ClientsEntity.class, id));
  //  }

   // @Override
   // public Stream<ClientsEntity> findAll() {
    //    return new HashSet(em.createNativeQuery("SELECT* FROM clients",
    //                    ClientsEntity.class).
    //            getResultList()).stream();
   // }

   // @Transactional
   // @Override
   // public void create(ClientsEntity entity) {

     //   em.persist(entity);
    //    em.flush();
  //  }

   // @Transactional
  //  @Override
  //  public void update(ClientsEntity clients) {
  //      em.merge(clients);
  ///      em.flush();
  //  }

   // @Transactional
   // @Override
   // public void remove(ClientsEntity clients) {
   //     em.remove(clients);
   //     em.flush();
   // }

    @Override
    public Stream<ClientsEntity> filerTypeName(String type, String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ClientsEntity> cq = cb.createQuery(ClientsEntity.class);
        Root<ClientsEntity> root = cq.from(ClientsEntity.class);
        if (type != null && name != null) {
            cq.select(root).where(
                    cb.and(
                            cb.like(cb.lower(root.get("typeclient")), "%" + type.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("clientName")), "%" + name.toLowerCase() + "%")
                    )
            );
            return em.createQuery(cq).getResultList().stream();
        } else if (type != null && name == null) {
            cq.select(root).where(
                    cb.like(root.get("typeclient"), "%" + type + "%")
            );
            return em.createQuery(cq).getResultList().stream();
        } else if (type == null && name != null) {
            cq.select(root).where(
                    cb.like(cb.lower(root.get("clientName")), "%" + name.toLowerCase() + "%")
            );
            return em.createQuery(cq).getResultList().stream();
        }

        return findAll(ClientsEntity.class);
    }

    @Override
    public EntityManager getEm() {
        return em;
    }
}
