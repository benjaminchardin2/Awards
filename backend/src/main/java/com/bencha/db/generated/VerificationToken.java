package com.bencha.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.sql.Column;

import javax.annotation.processing.Generated;

/**
 * VerificationToken is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class VerificationToken extends com.coreoz.plume.db.querydsl.crud.CrudEntityQuerydsl {

    @Column("expiration_date")
    private java.time.Instant expirationDate;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("id")
    private Long id;

    @Column("type")
    private String type;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("user_id")
    private Long userId;

    @Column("verification_string")
    private String verificationString;

    public java.time.Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(java.time.Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVerificationString() {
        return verificationString;
    }

    public void setVerificationString(String verificationString) {
        this.verificationString = verificationString;
    }

    @Override
    public String toString() {
        return "VerificationToken#" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (id == null) {
            return super.equals(o);
        }
        if (!(o instanceof VerificationToken)) {
            return false;
        }
        VerificationToken obj = (VerificationToken) o;
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

