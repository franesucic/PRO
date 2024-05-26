package INFSUS.project.PRO.models;

import java.sql.Types;
import java.util.Arrays;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Table(name = "advert")
@Entity

public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @JdbcTypeCode(Types.BINARY)
    private byte[] picture;

    @NotBlank
    private double price;

    @NotBlank
    @Column(name = "user_id")
    private int userId;

    @NotBlank
    @Column(name = "category_id")
    private int categoryId;

    public Advert() {

    }

    public Advert(String title, String description, byte[] picture, double price, int userId, int categoryId) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.price = price;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Advert advert = (Advert) o;

        if (id != null ? !id.equals(advert.id) : advert.id != null) {
            return false;
        }

        if (title != null ? !title.equals(advert.title) : advert.title != null) {
            return false;
        }

        if (description != null ? !description.equals(advert.description) : advert.description != null) {
            return false;
        }

        if (!Arrays.equals(picture, advert.picture)) {
            return false;
        }

        if (price != advert.price) {
            return false;
        }

        if (userId != advert.userId) {
            return false;
        }

        return categoryId == advert.categoryId;
    }

}
