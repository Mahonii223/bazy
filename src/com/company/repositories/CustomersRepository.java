package com.company.repositories;

import com.company.entities.Customers;

public interface CustomersRepository {
    Customers getCategoriesById(String id);

    Customers getByCompanyName(String companyName);

    Customers getByContactName(String contactName);

    Customers getByContactTitle(String contactTitle);

    Customers saveCustomer(Customers customer);

    void deleteCustomery(Customers c);
}
