package com.company.repoImpl;

import com.company.entities.Shippers;
import com.company.repositories.ShippersRepository;

import javax.persistence.EntityManager;
import javax.xml.stream.events.EntityDeclaration;

public class ShippersRepositoryImpl implements ShippersRepository {

    private EntityManager em;

    public ShippersRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Shippers getById(byte id) {
        return em.find(Shippers.class, id);
    }

    @Override
    public Shippers saveShipper(Shippers s) {
        if (s.getShipperId()==-1) {
            em.persist(s);
        } else {
            s = em.merge(s);
        }
        return s;
    }

    @Override
    public void deleteShippers(Shippers s) {
        if (em.contains(s)) {
            em.remove(s);
        } else {
            em.merge(s);
        }
    }

}
