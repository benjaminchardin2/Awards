package com.bencha.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.sql.Column;

import javax.annotation.processing.Generated;

/**
 * Ceremony is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class Ceremony extends com.coreoz.plume.db.querydsl.crud.CrudEntityQuerydsl {

    @Column("avatar_url")
    private String avatarUrl;

    @Column("ceremony_date")
    private java.time.Instant ceremonyDate;

    @Column("description")
    private String description;

    @Column("has_nominees")
    private Boolean hasNominees;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id")
    private Long id;

    @Column("is_highlighted")
    private Boolean isHighlighted;

    @Column("name")
    private String name;

    @Column("picture_url")
    private String pictureUrl;

    @Column("short_name")
    private String shortName;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public java.time.Instant getCeremonyDate() {
        return ceremonyDate;
    }

    public void setCeremonyDate(java.time.Instant ceremonyDate) {
        this.ceremonyDate = ceremonyDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasNominees() {
        return hasNominees;
    }

    public void setHasNominees(Boolean hasNominees) {
        this.hasNominees = hasNominees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(Boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "Ceremony#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) {
            return super.equals(o);
        }
        if (!(o instanceof Ceremony)) {
            return false;
        }
        Ceremony obj = (Ceremony) o;
        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        return result;
    }

}

