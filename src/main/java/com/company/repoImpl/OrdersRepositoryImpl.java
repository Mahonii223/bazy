package com.company.repoImpl;

import com.company.entities.Categories;
import com.company.entities.Orders;
import com.company.repositories.OrdersRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrdersRepositoryImpl implements OrdersRepository {

    private EntityManager em;

    public OrdersRepositoryImpl(EntityManager em){
        this.em=em;
    }

    @Override
    public Orders getById(Short id) {
        return em.find(Orders.class, id);
    }

    @Override
    public List<Orders> getAllOrdersByCustomerId(String customerId) {
        TypedQuery<Orders> q = em.createQuery("SELECT b FROM Orders b WHERE b.customerId = :customerId", Orders.class);
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

    @Override
    public Orders saveOrder(Orders o) {
        if (o.getOrderId()==-1) {
            em.persist(o);
        } else {
            o = em.merge(o);
        }
        return o;
    }

    @Override
    public void deleteOrder(Orders o) {
        if (em.contains(o)) {
            em.remove(o);
        } else {
            em.merge(o);
        }
    }

}
