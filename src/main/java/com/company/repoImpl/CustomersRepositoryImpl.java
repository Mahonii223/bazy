package com.company.repoImpl;

import com.company.entities.Categories;
import com.company.entities.Customers;
import com.company.repositories.CustomersRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CustomersRepositoryImpl implements CustomersRepository {

    private EntityManager em;

    public CustomersRepositoryImpl(EntityManager em){
        this.em = em;
    }
    @Override
    public Customers getCategoriesById(String id) {
        return em.find(Customers.class, id);
    }

    @Override
    public Customers getByCompanyName(String companyName) {
        TypedQuery<Customers> q = em.createQuery("SELECT b FROM Customers b WHERE b.companyName = :companyName", Customers.class);
        q.setParameter("companyName", companyName);
        return q.getSingleResult();
    }

    @Override
    public Customers getByContactName(String contactName) {
        TypedQuery<Customers> q = em.createQuery("SELECT b FROM Customers b WHERE b.contactName = :contactName", Customers.class);
        q.setParameter("contactName", contactName);
        return q.getSingleResult();
    }

    @Override
    public Customers getByContactTitle(String contactTitle) {
        TypedQuery<Customers> q = em.createQuery("SELECT b FROM Customers b WHERE b.contactTitle = :contactTitle", Customers.class);
        q.setParameter("contactTitle", contactTitle);
        return q.getSingleResult();
    }

    @Override
    public Customers saveCustomer(Customers customer) {
        if (customer.getCustomerId()==null) {
            em.persist(customer);
        } else {
            customer = em.merge(customer);
        }
        return customer;
    }

    @Override
    public void deleteCustomery(Customers c) {
        if (em.contains(c)) {
            em.remove(c);
        } else {
            em.merge(c);
        }
    }
}
