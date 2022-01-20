package com.company.repositories;

import com.company.entities.Suppliers;

public interface SuppliersRepository {

    Suppliers getById(byte id);
    Suppliers saveSupplier(Suppliers s);
    void deleteSupplier(Suppliers s);
}
