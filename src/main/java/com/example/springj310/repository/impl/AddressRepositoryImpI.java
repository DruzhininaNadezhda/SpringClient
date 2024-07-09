package com.example.springj310.repository.impl;

import com.example.springj310.entity.AddressesEntity;
import com.example.springj310.entity.ClientsEntity;
import com.example.springj310.repository.interfaces.AddressesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class AddressRepositoryImpI implements AddressesRepository {
    @PersistenceContext
    private EntityManager em;

   // @Override
   // public Optional<AddressesEntity> findAddressById(Long id) {
      //  if (id == null) Objects.requireNonNull(id, "Идентификатор не может быть null");
    //    return Optional.of(em.find(AddressesEntity.class, id));
  //  }

    //@Override
   // public Stream<AddressesEntity> findAll() {
   //     return new HashSet<>(em.createNativeQuery("select * from addresses", AddressesEntity.class).getResultList()).stream();
   // }

   // @Override
   // public void create(AddressesEntity entity) {
   //     em.persist(entity);
   //     em.flush();
   // }

   // @Override
   // public void update(AddressesEntity addresses) {
   //     em.merge(addresses);
   //     em.flush();
   // }

    //@Override
    //public void remove(AddressesEntity addresses) {
    //    em.remove(addresses);
    //    em.flush();
   // }

    @Override
    public Stream<AddressesEntity> filerAddress(String address) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AddressesEntity> cq = cb.createQuery(AddressesEntity.class);
        Root<AddressesEntity> root = cq.from(AddressesEntity.class);
        Join <AddressesEntity, ClientsEntity> clientJoin = root.join("clientid", JoinType.RIGHT);
        if (address != null) {
            cq.select(root).where(
                    cb.and(
                            cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%")
                    )
            );
            return em.createQuery(cq).getResultList().stream();
        }
        return findAll(AddressesEntity.class);
    }

    @Override
    public EntityManager getEm() {
        return em;
    }
}