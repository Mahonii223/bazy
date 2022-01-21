package com.company.repoImpl;

import com.company.entities.Categories;
import com.company.entities.Products;
import com.company.repositories.ProductsRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

public class ProductsRepositoryImpl implements ProductsRepository {

    private EntityManager em;

    public ProductsRepositoryImpl(EntityManager em){
        this.em=em;
    }
    @Override
    public Products getById(byte id) {
        return em.find(Products.class, id);
    }

    @Override
    public Boolean isDiscontinued(byte id) {
        TypedQuery<Products> q = em.createQuery("SELECT b FROM Products b WHERE b.productId= :id", Products.class);
        q.setParameter("id", id);
        return q.getSingleResult().isDiscontinued();
    }

    @Override
    public Byte getAmountOfUnits(byte id) {
        TypedQuery<Products> q = em.createQuery("SELECT b FROM Products b WHERE b.productId= :id", Products.class);
        q.setParameter("id", id);
        return q.getSingleResult().getUnitsInStock();
    }

    @Override
    public Products saveProduct(Products p) {
        if (p.getProductId()==-1) {
            em.persist(p);
        } else {
            p = em.merge(p);
        }
        return p;
    }

    @Override
    public void deleteProducts(Products p) {
        if (em.contains(p)) {
            em.remove(p);
        } else {
            em.merge(p);
        }
    }

    @Override
    public byte getUnitsOnStock(byte productId) {
        TypedQuery<Products> q = em.createQuery("SELECT b FROM Products b WHERE b.productId= :productId", Products.class);
        q.setParameter("productId", productId);
        return q.getSingleResult().getUnitsInStock();
    }

    @Override
    @Transactional
    public void setUnitsOnStock(byte productId, byte decrementBy) {
        byte amount = (byte) (getUnitsOnStock(productId) - decrementBy);
        Query q = em.createQuery("UPDATE Products SET UnitsInStock = :amount WHERE ProductId = :productId");
        q.setParameter("productId", productId);
        q.setParameter("amount", amount);
        em.getTransaction().begin();
        em.joinTransaction();
        q.executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public byte getUnitsOnOrder(byte productId) {
        TypedQuery<Products> q = em.createQuery("SELECT b FROM Products b WHERE b.productId= :productId", Products.class);
        q.setParameter("productId", productId);
        return q.getSingleResult().getUnitsOnOrder();
    }

    @Override
    public void setUnitsOnOrder(byte productId, byte decrementBy) {
        byte amount = (byte) (getUnitsOnStock(productId) - decrementBy);
        Query q = em.createQuery("UPDATE Products SET UnitsOnOrder = :amount WHERE ProductId = :productId");
        q.setParameter("productId", productId);
        q.setParameter("amount", amount);
        q.executeUpdate();
    }
}