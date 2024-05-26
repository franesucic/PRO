package INFSUS.project.PRO.models;

import java.sql.Types;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.validation.constraints.NotBlank;

public class AdvertRequest {

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
    private String user;

    @NotBlank
    private String categoryName;

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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
