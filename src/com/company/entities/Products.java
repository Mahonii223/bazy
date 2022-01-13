package com.company;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Products {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ProductID", nullable = false)
    private byte productId;
    @Basic
    @Column(name = "ProductName", nullable = false, length = 50)
    private String productName;
    @Basic
    @Column(name = "SupplierID", nullable = false)
    private byte supplierId;
    @Basic
    @Column(name = "CategoryID", nullable = false)
    private byte categoryId;
    @Basic
    @Column(name = "QuantityPerUnit", nullable = false, length = 50)
    private String quantityPerUnit;
    @Basic
    @Column(name = "UnitPrice", nullable = false)
    private byte unitPrice;
    @Basic
    @Column(name = "UnitsInStock", nullable = false)
    private byte unitsInStock;
    @Basic
    @Column(name = "UnitsOnOrder", nullable = false)
    private byte unitsOnOrder;
    @Basic
    @Column(name = "ReorderLevel", nullable = false)
    private byte reorderLevel;
    @Basic
    @Column(name = "Discontinued", nullable = false)
    private boolean discontinued;

    public byte getProductId() {
        return productId;
    }

    public void setProductId(byte productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(byte supplierId) {
        this.supplierId = supplierId;
    }

    public byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(byte categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public byte getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(byte unitPrice) {
        this.unitPrice = unitPrice;
    }

    public byte getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(byte unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public byte getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(byte unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public byte getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(byte reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return productId == products.productId && supplierId == products.supplierId && categoryId == products.categoryId && unitPrice == products.unitPrice && unitsInStock == products.unitsInStock && unitsOnOrder == products.unitsOnOrder && reorderLevel == products.reorderLevel && discontinued == products.discontinued && Objects.equals(productName, products.productName) && Objects.equals(quantityPerUnit, products.quantityPerUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, supplierId, categoryId, quantityPerUnit, unitPrice, unitsInStock, unitsOnOrder, reorderLevel, discontinued);
    }
}
