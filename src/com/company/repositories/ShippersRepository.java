package com.company.repositories;

import com.company.entities.Shippers;

public interface ShippersRepository {

    Shippers getById(byte id);
    Shippers saveShipper(Shippers s);
    void deleteShippers(Shippers s);
}
