package com.company;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "odID", nullable = false)
    private short odId;
    @Basic
    @Column(name = "OrderID", nullable = false)
    private short orderId;
    @Basic
    @Column(name = "ProductID", nullable = false)
    private byte productId;
    @Basic
    @Column(name = "UnitPrice", nullable = false)
    private byte unitPrice;
    @Basic
    @Column(name = "Quantity", nullable = false)
    private byte quantity;
    @Basic
    @Column(name = "Discount", nullable = false)
    private byte discount;

    public short getOdId() {
        return odId;
    }

    public void setOdId(short odId) {
        this.odId = odId;
    }

    public short getOrderId() {
        return orderId;
    }

    public void setOrderId(short orderId) {
        this.orderId = orderId;
    }

    public byte getProductId() {
        return productId;
    }

    public void setProductId(byte productId) {
        this.productId = productId;
    }

    public byte getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(byte unitPrice) {
        this.unitPrice = unitPrice;
    }

    public byte getQuantity() {
        return quantity;
    }

    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }

    public byte getDiscount() {
        return discount;
    }

    public void setDiscount(byte discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return odId == that.odId && orderId == that.orderId && productId == that.productId && unitPrice == that.unitPrice && quantity == that.quantity && discount == that.discount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(odId, orderId, productId, unitPrice, quantity, discount);
    }
}
