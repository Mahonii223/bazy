package com.company.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Shippers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ShipperID", nullable = false)
    private byte shipperId;
    @Basic
    @Column(name = "CompanyName", nullable = false, length = 50)
    private String companyName;
    @Basic
    @Column(name = "Phone", nullable = false, length = 50)
    private String phone;

    public byte getShipperId() {
        return shipperId;
    }

    public void setShipperId(byte shipperId) {
        this.shipperId = shipperId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shippers shippers = (Shippers) o;
        return shipperId == shippers.shipperId && Objects.equals(companyName, shippers.companyName) && Objects.equals(phone, shippers.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipperId, companyName, phone);
    }
}
