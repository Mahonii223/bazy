package com.company.repoImpl;

import com.company.entities.Empl;
import com.company.repositories.EmplRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class EmplRepositoryImpl implements EmplRepository {

    private EntityManager em;
    public EmplRepositoryImpl(EntityManager em){
        this.em = em;
    }
    @Override
    public Empl getByEmplId(byte id) {
        return em.find(Empl.class, id);
    }

    @Override
    public Empl getByFirstAndLastName(String firstName, String lastName) {
        TypedQuery<Empl> q = em.createQuery("SELECT b FROM Empl b WHERE b.firstName = :firstName AND b.lastName = :lastName", Empl.class);
        q.setParameter("firstName", firstName);
        q.setParameter("lastName", lastName);
        return q.getSingleResult();    }
}
