package com.company.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Suppliers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SupplierID", nullable = false)
    private byte supplierId;
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
    @Column(name = "PostalCode", nullable = false, length = 50)
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
    @Basic
    @Column(name = "HomePage", nullable = true, length = 100)
    private String homePage;

    public byte getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(byte supplierId) {
        this.supplierId = supplierId;
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

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suppliers suppliers = (Suppliers) o;
        return supplierId == suppliers.supplierId && Objects.equals(companyName, suppliers.companyName) && Objects.equals(contactName, suppliers.contactName) && Objects.equals(contactTitle, suppliers.contactTitle) && Objects.equals(address, suppliers.address) && Objects.equals(city, suppliers.city) && Objects.equals(region, suppliers.region) && Objects.equals(postalCode, suppliers.postalCode) && Objects.equals(country, suppliers.country) && Objects.equals(phone, suppliers.phone) && Objects.equals(fax, suppliers.fax) && Objects.equals(homePage, suppliers.homePage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplierId, companyName, contactName, contactTitle, address, city, region, postalCode, country, phone, fax, homePage);
    }
}
