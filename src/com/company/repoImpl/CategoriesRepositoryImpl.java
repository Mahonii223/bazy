package com.company.repoImpl;

import com.company.entities.Categories;
import com.company.repositories.CategoriesRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CategoriesRepositoryImpl implements CategoriesRepository {

    private EntityManager em;

    public CategoriesRepositoryImpl(EntityManager em){
        this.em=em;
    }


    @Override
    public Categories getCategoriesById(Long id) {
        return em.find(Categories.class, id);
    }

    @Override
    public Categories getByCategoryName(String categoryName) {
        TypedQuery<Categories> q = em.createQuery("SELECT b FROM Categories b WHERE b.categoryName = :categoryName", Categories.class);
        q.setParameter("categoryName", categoryName);
        return q.getSingleResult();
    }

    @Override
    public Categories saveCategories(Categories categories) {
        if (categories.getCategoryId()==-1) {
            em.persist(categories);
        } else {
            categories = em.merge(categories);
        }
        return categories;
    }

    @Override
    public void deleteCategory(Categories c) {
        if (em.contains(c)) {
            em.remove(c);
        } else {
            em.merge(c);
        }
    }
}
