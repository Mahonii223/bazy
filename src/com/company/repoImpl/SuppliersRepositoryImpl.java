package com.company.repoImpl;

import com.company.entities.Suppliers;
import com.company.repositories.SuppliersRepository;

import javax.persistence.EntityManager;

public class SuppliersRepositoryImpl implements SuppliersRepository {

    private EntityManager em;

    public SuppliersRepositoryImpl(EntityManager em){
        this.em = em;
    }
    @Override
    public Suppliers getById(byte id) {
        return em.find(Suppliers.class, id);
    }

    @Override
    public Suppliers saveSupplier(Suppliers s) {
        if (s.getSupplierId()==-1) {
            em.persist(s);
        } else {
            s = em.merge(s);
        }
        return s;
    }

    @Override
    public void deleteSupplier(Suppliers s) {
        if (em.contains(s)) {
            em.remove(s);
        } else {
            em.merge(s);
        }
    }

}
