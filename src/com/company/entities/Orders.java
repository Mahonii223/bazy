package com.company.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Orders {
    @Basic
    @Column(name = "OrderID", nullable = true)
    private Short orderId;
    @Basic
    @Column(name = "CustomerID", nullable = true, length = 50)
    private String customerId;
    @Basic
    @Column(name = "EmployeeID", nullable = true)
    private Byte employeeId;
    @Basic
    @Column(name = "OrderDate", nullable = true)
    private Date orderDate;
    @Basic
    @Column(name = "RequiredDate", nullable = true)
    private Date requiredDate;
    @Basic
    @Column(name = "ShippedDate", nullable = true)
    private Date shippedDate;
    @Basic
    @Column(name = "ShipVia", nullable = true)
    private Byte shipVia;
    @Basic
    @Column(name = "Freight", nullable = true)
    private Byte freight;
    @Basic
    @Column(name = "ShipName", nullable = true, length = 50)
    private String shipName;
    @Basic
    @Column(name = "ShipAddress", nullable = true, length = 50)
    private String shipAddress;
    @Basic
    @Column(name = "ShipCity", nullable = true, length = 50)
    private String shipCity;
    @Basic
    @Column(name = "ShipRegion", nullable = true, length = 50)
    private String shipRegion;
    @Basic
    @Column(name = "ShipPostalCode", nullable = true, length = 50)
    private String shipPostalCode;
    @Basic
    @Column(name = "ShipCountry", nullable = true, length = 50)
    private String shipCountry;

    public Short getOrderId() {
        return orderId;
    }

    public void setOrderId(Short orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Byte getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Byte employeeId) {
        this.employeeId = employeeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Byte getShipVia() {
        return shipVia;
    }

    public void setShipVia(Byte shipVia) {
        this.shipVia = shipVia;
    }

    public Byte getFreight() {
        return freight;
    }

    public void setFreight(Byte freight) {
        this.freight = freight;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipRegion() {
        return shipRegion;
    }

    public void setShipRegion(String shipRegion) {
        this.shipRegion = shipRegion;
    }

    public String getShipPostalCode() {
        return shipPostalCode;
    }

    public void setShipPostalCode(String shipPostalCode) {
        this.shipPostalCode = shipPostalCode;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(orderId, orders.orderId) && Objects.equals(customerId, orders.customerId) && Objects.equals(employeeId, orders.employeeId) && Objects.equals(orderDate, orders.orderDate) && Objects.equals(requiredDate, orders.requiredDate) && Objects.equals(shippedDate, orders.shippedDate) && Objects.equals(shipVia, orders.shipVia) && Objects.equals(freight, orders.freight) && Objects.equals(shipName, orders.shipName) && Objects.equals(shipAddress, orders.shipAddress) && Objects.equals(shipCity, orders.shipCity) && Objects.equals(shipRegion, orders.shipRegion) && Objects.equals(shipPostalCode, orders.shipPostalCode) && Objects.equals(shipCountry, orders.shipCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, employeeId, orderDate, requiredDate, shippedDate, shipVia, freight, shipName, shipAddress, shipCity, shipRegion, shipPostalCode, shipCountry);
    }
}
