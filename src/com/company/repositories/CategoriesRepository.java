package com.company.repositories;

import com.company.entities.Categories;

public interface CategoriesRepository {

    Categories getCategoriesById(Long id);
    Categories getByCategoryName(String categoryName);
    Categories saveCategories(Categories categories);
    void deleteCategory(Categories c);

}
