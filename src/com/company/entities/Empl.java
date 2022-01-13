package com.company;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Empl {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EmployeeID", nullable = false)
    private byte employeeId;
    @Basic
    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;
    @Basic
    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;
    @Basic
    @Column(name = "Title", nullable = false, length = 50)
    private String title;
    @Basic
    @Column(name = "TitleOfCourtesy", nullable = false, length = 50)
    private String titleOfCourtesy;
    @Basic
    @Column(name = "BirthDate", nullable = false)
    private Date birthDate;
    @Basic
    @Column(name = "HireDate", nullable = false)
    private Date hireDate;
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
    @Column(name = "HomePhone", nullable = false, length = 50)
    private String homePhone;
    @Basic
    @Column(name = "Extension", nullable = false)
    private short extension;
    @Basic
    @Column(name = "Photo", nullable = true, length = 1)
    private String photo;
    @Basic
    @Column(name = "Notes", nullable = false, length = 450)
    private String notes;
    @Basic
    @Column(name = "ReportsTo", nullable = false)
    private byte reportsTo;

    public byte getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(byte employeeId) {
        this.employeeId = employeeId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOfCourtesy() {
        return titleOfCourtesy;
    }

    public void setTitleOfCourtesy(String titleOfCourtesy) {
        this.titleOfCourtesy = titleOfCourtesy;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
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

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public short getExtension() {
        return extension;
    }

    public void setExtension(short extension) {
        this.extension = extension;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(byte reportsTo) {
        this.reportsTo = reportsTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empl empl = (Empl) o;
        return employeeId == empl.employeeId && extension == empl.extension && reportsTo == empl.reportsTo && Objects.equals(lastName, empl.lastName) && Objects.equals(firstName, empl.firstName) && Objects.equals(title, empl.title) && Objects.equals(titleOfCourtesy, empl.titleOfCourtesy) && Objects.equals(birthDate, empl.birthDate) && Objects.equals(hireDate, empl.hireDate) && Objects.equals(address, empl.address) && Objects.equals(city, empl.city) && Objects.equals(region, empl.region) && Objects.equals(postalCode, empl.postalCode) && Objects.equals(country, empl.country) && Objects.equals(homePhone, empl.homePhone) && Objects.equals(photo, empl.photo) && Objects.equals(notes, empl.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, lastName, firstName, title, titleOfCourtesy, birthDate, hireDate, address, city, region, postalCode, country, homePhone, extension, photo, notes, reportsTo);
    }
}
