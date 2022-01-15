package com.company.repositories;

import com.company.entities.Empl;

public interface EmplRepository {

    Empl getByEmplId(byte id);
    Empl getByFirstAndLastName(String firstName, String lastName);
}
