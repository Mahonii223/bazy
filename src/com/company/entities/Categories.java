package com.company;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Categories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CategoryID", nullable = false)
    private byte categoryId;
    @Basic
    @Column(name = "CategoryName", nullable = false, length = 50)
    private String categoryName;
    @Basic
    @Column(name = "Description", nullable = false, length = 100)
    private String description;
    @Basic
    @Column(name = "Picture", nullable = true, length = 1)
    private String picture;
    @Basic
    @Column(name = "column5", nullable = true, length = 1)
    private String column5;

    public byte getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(byte categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getColumn5() {
        return column5;
    }

    public void setColumn5(String column5) {
        this.column5 = column5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return categoryId == that.categoryId && Objects.equals(categoryName, that.categoryName) && Objects.equals(description, that.description) && Objects.equals(picture, that.picture) && Objects.equals(column5, that.column5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, description, picture, column5);
    }
}
