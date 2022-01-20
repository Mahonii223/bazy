package com.company.repositories;

import com.company.entities.Products;

public interface ProductsRepository {
    Products getById(byte id);
    Boolean isDiscontinued(byte id);
    Byte getAmountOfUnits(byte id);
    Products saveProduct(Products p);
    void deleteProducts(Products p);

}
