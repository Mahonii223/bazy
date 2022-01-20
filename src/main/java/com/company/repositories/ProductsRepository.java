package com.company.repositories;

import com.company.entities.Products;

public interface ProductsRepository {
    Products getById(byte id);
    Boolean isDiscontinued(byte id);
    Byte getAmountOfUnits(byte id);
    Products saveProduct(Products p);
    void deleteProducts(Products p);
    byte getUnitsOnStock(byte productId);
    void setUnitsOnStock(byte productId, byte decrementBy);
    byte getUnitsOnOrder(byte productId);
    void setUnitsOnOrder(byte productId, byte decrementBy);

}
