package com.company;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Customers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CustomerID", nullable = false, length = 50)
    private String customerId;
    @Basic
    @Column(name = "CompanyName", nullable = false, length = 50)
    private String companyName;
    @Basic
    @Column(name = "ContactName", nullable = false, length = 50)
    private String contactName;
    @Basic
    @Column(name = "ContactTitle", nullable = false, length = 50)
    private String contactTitle;
    @Basic
    @Column(name = "Address", nullable = false, length = 50)
    private String address;
    @Basic
    @Column(name = "City", nullable = false, length = 50)
    private String city;
    @Basic
    @Column(name = "Region", nullable = true, length = 50)
    private String region;
    @Basic
    @Column(name = "PostalCode", nullable = true, length = 50)
    private String postalCode;
    @Basic
    @Column(name = "Country", nullable = false, length = 50)
    private String country;
    @Basic
    @Column(name = "Phone", nullable = false, length = 50)
    private String phone;
    @Basic
    @Column(name = "Fax", nullable = true, length = 50)
    private String fax;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customers customers = (Customers) o;
        return Objects.equals(customerId, customers.customerId) && Objects.equals(companyName, customers.companyName) && Objects.equals(contactName, customers.contactName) && Objects.equals(contactTitle, customers.contactTitle) && Objects.equals(address, customers.address) && Objects.equals(city, customers.city) && Objects.equals(region, customers.region) && Objects.equals(postalCode, customers.postalCode) && Objects.equals(country, customers.country) && Objects.equals(phone, customers.phone) && Objects.equals(fax, customers.fax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, companyName, contactName, contactTitle, address, city, region, postalCode, country, phone, fax);
    }
}
