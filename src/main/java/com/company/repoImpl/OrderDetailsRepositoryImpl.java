package com.company.repoImpl;

import com.company.entities.OrderDetails;
import com.company.repositories.OrderDetailsRepository;

import javax.persistence.EntityManager;

public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private EntityManager em;
    public OrderDetailsRepositoryImpl(EntityManager em){
        this.em = em;
    }
    @Override
    public OrderDetails getById(short odId) {
        return em.find(OrderDetails.class, odId);
    }

    @Override
    public OrderDetails getByOrderId(short orderId) {
        return em.find(OrderDetails.class, orderId);
    }

    @Override
    public OrderDetails save(OrderDetails od) {
        if (od.getOrderId()==-1) {
            em.persist(od);
        } else {
            od = em.merge(od);
        }
        return od;
    }
}