package com.company.repositories;

import com.company.entities.Orders;
import org.hibernate.criterion.Order;

import java.util.List;

public interface OrdersRepository {

    Orders getById(Short id);
    List<Orders> getAllOrdersByCustomerId(String customerId);
    Orders saveOrder(Orders o);
    void deleteOrder(Orders o);
}
