package com.company.repositories;

import com.company.entities.OrderDetails;

public interface OrderDetailsRepository {

    OrderDetails getById(short odId);
    OrderDetails getByOrderId(short orderId);

}
